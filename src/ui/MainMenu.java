package ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import settings.UserSettings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;


public class MainMenu extends Application {

	@Override
	public void start(Stage stage) throws Exception {
        stage.setTitle("2D Game");
		stage.setResizable(false);
		stage.setFullScreenExitHint("");
		ReadSettingsFromFile();
		stage.setFullScreen(UserSettings.getIsFullScreenEnabled());
        stage.setScene(createMainMenu());
        stage.show();
	}
	
	public static Scene createMainMenu() {

		GridPane inner = new GridPane();
		GridPane outer = new GridPane();



		inner.setHgap(10);
		inner.setVgap(3);
		
		// @TODO replace placeholder with image of game play
		Label placeholder = new Label("Placeholder");
		placeholder.setMinWidth(400);
		placeholder.setMinHeight(250);
		placeholder.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
		
		outer.add(placeholder, 0, 0);
		
		//create labels
		Label title = new Label("2D Game");
		Label credentials = new Label("Made by: Not-Dan, dl90, and Jahrr");
		Label githubLink = new Label("https://github.com/Not-Dan/2D-Game");
		inner.add(title, 0, 0);
		inner.add(credentials, 0, 1);
		inner.add(githubLink, 0, 2);
		
		//create buttons, handlers, style
		Button startBtn = new Button("Start");
		Button optionsBtn = new Button("Options");
		Button exitBtn = new Button("Exit");
		startBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                // @TODO Open Game
                
            }
        });
		optionsBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                // Create a scene using the fxml file and switch to it
				/*
				I don't know the best practices of JavaFX, but I think
				we should create a SceneManager class and use it to
				switch our scenes in the future because this looks a little messy
				*/
				Parent root;
				try {
					root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ui/OptionsMenu.fxml")));
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
				Stage stage = (Stage) optionsBtn.getScene().getWindow();
				Scene scene = new Scene(root);
				stage.setScene(scene);
				//We have to set stage.setFullScreen every time we switch our scene to maintain the player setting
				//this is stupid. It feels like a hack and causes this wierd stuttering when switching scenes if in
				//fullscreen because the stage briefly reverts back to its original resolution before returning to
				//fullscreen. Hopefully there's a way to simply preserve stage.setFullScreen so we don't have to set
				//it every time.
				stage.setFullScreen(UserSettings.getIsFullScreenEnabled());
				stage.show();
            }
        });
		exitBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
            	Stage stage = (Stage) exitBtn.getScene().getWindow();
                stage.close();
            }
        });
		startBtn.setMinWidth(120);
		optionsBtn.setMinWidth(120);
		exitBtn.setMinWidth(120);
		
		inner.add(startBtn, 1, 0);
		inner.add(optionsBtn, 1, 1);
		inner.add(exitBtn, 1, 2);
		inner.setAlignment(Pos.CENTER);
		
		outer.add(inner, 0, 1);
		
		//add vbox for padding
		VBox vb = new VBox();
	    vb.setPadding(new Insets(10, 25, 25, 25));
	    vb.getChildren().add(outer);

	    return new Scene(vb);

	}

	//This feels very very bad but its a starting point and can be revised. I don't have much experience
	//with how games store data so please feel free to revise or rewrite
	//This function reads lines and separates them based on the commas and closing bracket
	//It then tests if those substrings contain the setting parameter and if they do, they set the
	//parameter and remove that part of the string from the line.
	private void ReadSettingsFromFile() throws Exception{
		File configFile = new File("settings.cfg");
		BufferedReader reader = new BufferedReader(new FileReader(configFile));
		String settingsLine;
		String requiredReadAttributes[] = {
				"volume",
				"isFullScreen",
				"isVSyncEnabled"
		};
		while((settingsLine = reader.readLine()) != null){
			int it = 0;
			for(int i = 0; i < requiredReadAttributes.length; i++){
				String newAttribute = settingsLine.substring(it, settingsLine.indexOf(i < requiredReadAttributes.length - 1 ? "," : "}"));

				if(newAttribute.contains("volume")){
					UserSettings.setGameVolume(Integer.parseInt(newAttribute.substring(newAttribute.indexOf(":") + 1)));
				}else if(newAttribute.contains("isFullScreen")){
					UserSettings.setIsFullScreenEnabled(Boolean.parseBoolean((newAttribute.substring(newAttribute.indexOf(":") + 1))));
				}else if(newAttribute.contains("isVSyncEnabled")){
					UserSettings.setIsVSyncEnabled(Boolean.parseBoolean((newAttribute.substring(newAttribute.indexOf(":") + 1))));
				}
				settingsLine = settingsLine.replace(newAttribute + ",", "");
			}
		}
		System.out.println("Settings read from memory:\nVolume: " + UserSettings.getGameVolume()+
				"\nisFullScreen: " + UserSettings.getIsFullScreenEnabled() +
				"\nisVSyncEnabled: " + UserSettings.getIsVSyncEnabled());
	}


}
