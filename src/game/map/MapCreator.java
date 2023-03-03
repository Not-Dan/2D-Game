package game.map;

//Created by Jahrr on 3/1/23

//Perhaps we should create a new debug package and store this class there


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    String path = "C:\\Users\\minri\\IdeaProjects\\Game_Dev\\2D-Game\\res\\tilesets\\NatureTileset.png";
    //String relativePath = "..\\..\\..\\res\\tilesets\\NatureTileset.png";
    //"Selected" texture coordinates
    //This should be the same as the last pressed button's texture coordinates
    Vector2<Integer> currentTexCoords = new Vector2<>(0,0);
    //How we align the buttons
    GridPane selectorGrid = new GridPane();
    public void createEditor(Stage MenuStage) {

        System.out.println("Starting Editor...");
        Stage editorStage = new Stage();
        VBox tileSelectorAlign = new VBox();
        Scene editorScene = new Scene(tileSelectorAlign, 800, 600);
        editorStage.setTitle("Tilemap Editor");

        //Test for image loading

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
        Vector2<Integer> tilesetSize = new Vector2<>(641, 288);
        int tileSize = 32;
        Image tileset = new Image(path);



        ToggleGroup buttonGroup = new ToggleGroup();



        int it = 0;

        for(int y=0; y<tilesetSize.y - tileSize; y+=tileSize)
        {
            for(int x=0; x<tilesetSize.x - tileSize; x+=tileSize)
            {

                PixelReader reader = tileset.getPixelReader();
                WritableImage newImage = new WritableImage(reader, x, y, tileSize, tileSize);
                PixelReader tileValidationReader = newImage.getPixelReader();

                boolean validFlag = false;
                System.out.println("Tile number: " + it);
                for(int ty = 0; ty < tileSize; ty+=4){
                    for(int tx = 0; tx < tileSize; tx+=4){
                        if(!tileValidationReader.getColor(tx,ty).equals(Color.TRANSPARENT)){
                            validFlag = true;
                            break;
                        }
                    }
                    if(validFlag)
                        break;
                }
                if(validFlag)
                {
                    TileToggleButton button = new TileToggleButton("", new Vector2<>(x,y));
                    button.setMinWidth(60);
                    button.setMaxWidth(60);
                    button.setToggleGroup(buttonGroup);
                    selectorGrid.add(button, it%11, it/11);
                    button.setGraphic(new ImageView(newImage));
                    button.setOnAction(actionEvent -> {
                        currentTexCoords = button.textureCoordinates;
                        System.out.println(currentTexCoords.returnVectorString());
                    });
                    it++;
                }

            }
        }

    }



}
