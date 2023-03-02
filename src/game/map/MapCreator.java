package game.map;

//Created by Jahrr on 3/1/23

//Perhaps we should create a new debug package and store this class there


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utility.Vector2;



//Created by Jahrr on 3/2/23

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

    //"Selected" texture coordinates
    //This should be the same as the last pressed button's texture coordinates
    Vector2<Integer> currentTexCoords = new Vector2<Integer>(0,0);
    //How we align the buttons
    GridPane selectorGrid = new GridPane();
    public void createEditor(Stage MenuStage) {

        System.out.println("Starting Editor...");
        Stage editorStage = new Stage();
        VBox tileSelectorAlign = new VBox();
        Scene editorScene = new Scene(tileSelectorAlign, 800, 600);
        editorStage.setTitle("Tilemap Editor");

        //Test for image loading
        //Image tileset = new Image(path);
        //ImageView imageView = new ImageView(tileset);


        editorStage.setScene(editorScene);

        //set up selectorGrid
        selectorGrid.setHgap(3);
        selectorGrid.setVgap(2);
        selectorGrid.setPadding(new Insets(5,5,5,5));
        populateTileSelection();
        
        //set up tile selector
        tileSelectorAlign.setAlignment(Pos.BOTTOM_CENTER);
        tileSelectorAlign.setPadding(new Insets(10,10,10,10));
        ScrollPane tileSelector = new ScrollPane();
        tileSelector.setPrefSize(500,100);
        tileSelector.setContent(selectorGrid);
        tileSelectorAlign.getChildren().add(tileSelector);


        editorStage.show();

    }

    private void populateTileSelection(){
        //Placeholder population method; Load from tileset in the future
        //use Button.setGraphic(ImageView)
        int gridSize = 23;
        ToggleGroup buttonGroup = new ToggleGroup();
        for(Integer i = 0; i < 200; i++){

            TileToggleButton button = new TileToggleButton(i.toString(), new Vector2<>(i%gridSize, (i/gridSize)));

            button.setMinWidth(30);
            button.setMaxWidth(30);
            button.setToggleGroup(buttonGroup);
            selectorGrid.add(button, i%gridSize, (i/gridSize));

            button.setOnAction(actionEvent -> {

                System.out.println(button.textureCoordinates.returnVectorString());
            });

        }

    }



}
