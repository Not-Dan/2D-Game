package ui;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.util.Objects;

import settings.UserSettings;


/*
Created by Jahrr on 2/25/23
This class controls the events that occur in the options menu. Please note that
the JavaFX object names must match the fx:id specified in OptionsMenu.fxml
*/

public class OptionsMenuController {
    public MenuButton WindowButton;
    public Button VSyncButton;
    public Button BackButton;


    @FXML
    public void initialize(){
        //Set button labels based on user settings
        WindowButton.setText(UserSettings.getIsFullScreenEnabled() ? "Fullscreen" : "Windowed");
        VSyncButton.setText(UserSettings.getIsVSyncEnabled() ? "On" : "Off");
    }
    @FXML
    public void setWindowOption(ActionEvent e){
        //Bug: When Esc is pressed (The default fullscreen exit button) the window returns to windowed, but the
        //text remains fullscreen. Functionality of WindowButton is unaffected, but it should be handled regardless.
        //We can fix it pretty easily by setting UserSettings.isFullScreenEnabled to false when the fullscreen exit button
        // is pressed (handle key event) but I'm unsure where we can handle that so it persists across scenes.
        Stage stage = (Stage)(WindowButton.getScene().getWindow());
        stage.setFullScreenExitHint("");
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

    public void returnToMainMenu(ActionEvent e){
        //TODO: Save options to a file
        Stage stage = (Stage) BackButton.getScene().getWindow();
        stage.setScene(MainMenu.createMainMenu());
        stage.setFullScreen(UserSettings.getIsFullScreenEnabled());
        stage.show();
    }
}
