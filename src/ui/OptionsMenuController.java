package ui;


import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;

import settings.UserSettings;


/*
Created by Jahrr on 2/25/23
This class controls the events that occur in the options menu. Please note that
the JavaFX object names must match the fx:id specified in OptionsMenu.fxml
*/

public class OptionsMenuController {

    public Slider VolumeSlider;
    public MenuButton WindowButton;
    public Button VSyncButton;
    public Button DeleteProgressButton;
    public Button BackButton;


    @FXML
    public void initialize() throws Exception {
        VolumeSlider.setValue(UserSettings.getGameVolume());
        //Set button labels based on user settings
        WindowButton.setText(UserSettings.getIsFullScreenEnabled() ? "Fullscreen" : "Windowed");
        VSyncButton.setText(UserSettings.getIsVSyncEnabled() ? "On" : "Off");

        //When the slider is moved, capture the value and assign it to the game volume
        VolumeSlider.valueProperty().addListener((
                (observableValue, number, newValue) -> {
                    UserSettings.setGameVolume(newValue.intValue());
                }
        ));
    }

    @FXML
    public void setWindowOption(ActionEvent e){
        //Bug: When Esc is pressed (The default fullscreen exit button) the window returns to windowed, but the
        //text remains fullscreen. Functionality of WindowButton is unaffected, but it should be handled regardless.
        //We can fix it pretty easily by setting UserSettings.isFullScreenEnabled to false when the fullscreen exit button
        // is pressed (handle key event) but I'm unsure where we can handle that so it persists across scenes.
        Stage stage = (Stage)(WindowButton.getScene().getWindow());

        WindowButton.setText(((MenuItem)e.getSource()).getText());
        UserSettings.setIsFullScreenEnabled(Objects.equals(WindowButton.getText(), "Fullscreen"));
        stage.setFullScreen(UserSettings.getIsFullScreenEnabled());
    }
    @FXML
    public void toggleVSync(ActionEvent e){
        UserSettings.setIsVSyncEnabled(!UserSettings.getIsVSyncEnabled());
        VSyncButton.setText((UserSettings.getIsVSyncEnabled() ? "On" : "Off"));
        //JavaFX doesn't let us set VSync, maybe canvas does?
        //TODO: Toggle vsync using isVsyncEnabled
    }

    public void deleteProgress(ActionEvent e){
        //Nuke everything
        //TODO: Add confirmation dialog
        System.out.println("This would have deleted your progress if you were playing a game...");
    }

    public void returnToMainMenu(ActionEvent e) throws IOException {
        WriteSettingsToFile();
        Stage stage = (Stage) BackButton.getScene().getWindow();
        stage.setScene(MainMenu.createMainMenu());
        stage.setFullScreen(UserSettings.getIsFullScreenEnabled());
        stage.show();
    }

    private void WriteSettingsToFile() throws IOException {
        File configFile = new File("settings.cfg");
        BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
        writer.write("{" + "\"volume\":" + UserSettings.getGameVolume() +
                ", \"isFullScreen\":" + UserSettings.getIsFullScreenEnabled() +
                ", \"isVSyncEnabled\":"+UserSettings.getIsVSyncEnabled() + "}");
        writer.close();
    }



}
