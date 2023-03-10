package ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainMenu extends Application {

	@Override
	public void start(Stage stage) throws Exception {
        stage.setTitle("2D Game");
		stage.setResizable(false);
        stage.setScene(createMainMenu());
        stage.show();
	}
	
	private Scene createMainMenu() {
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
                // @TODO Open Options Menu
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

	    Scene scene = new Scene(vb);
		return scene;
	}
}
