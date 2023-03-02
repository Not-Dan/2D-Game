package game.map;

//Created by Jahrr on 3/1/23

//Perhaps we should create a new debug package and store this class there


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import utility.Vector2;

import java.net.MalformedURLException;
import java.net.URL;

//This is our tilemap editor contained in a class. It should contain a accessible scene
//that can be run in debug to edit our maps. We should be able to load multiple tilemaps,
//but to start focus on loading one tilemap for all of our maps.


//Before we start programming adding tiles, we need to first create the tile picker.
//This can be done by iterating over the tileset in the increment of its tile resolution (32x32)
//and creating buttons with that png on it that assigns the texture coordinate of the tile to
//currentTexCoords. We can then use those coordinates to layout tiles.
public class MapCreator
{

    //someone get this relative path right please
    //To NatureTileset.png
    String path = "";

    Vector2<Integer> currentTexCoords = new Vector2<Integer>(0,0);

    public void createEditor(Stage MenuStage) {

        System.out.println("Starting Editor...");
        Stage editorStage = new Stage();
        Group root = new Group();
        Scene editorScene = new Scene(root, 1200, 900);
        editorStage.setTitle("Tilemap Editor");


        Image tileset = new Image(path);
        ImageView imageView = new ImageView(tileset);
        root.getChildren().add(imageView);

        editorStage.setScene(editorScene);
        editorStage.show();

    }



}
