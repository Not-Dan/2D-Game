package game.map;

//Created by Jahrr on 3/1/23

//Perhaps we should create a new debug package and store this class there


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utility.Vector2;
import java.util.*;


//Created by Jahrr on 3/2/23

//This is our tilemap editor contained in a class. It should contain an accessible scene
//that can be run in debug to edit our maps. We should be able to load multiple tilemaps,
//but to start focus on loading one tilemap for all of our maps.


//Before we start programming adding tiles, we need to first create the tile picker.
//This is done by iterating over the tileset in the increment of its tile resolution (32x32)
//and creating buttons with that png on it that assigns the texture coordinate of the tile to
//currentTexCoords. We can then use those coordinates to layout tiles.

//loading in the tilemap can be optimized... currently it scans every fourth point, we should implement a better algorithm to do this

public class MapCreator
{

    //someone get this relative path right please
    //To NatureTileset.png
    String tilesetPath = "";

    Vector2<Integer> tilesetSize = new Vector2<>(641, 288);
    int tileSize = 32;
    Vector2<Integer> currentTexCoords = new Vector2<>(0,0);
    Vector2<Integer> lastTileDrawnPosition = new Vector2<>(0,0);
    Image tileset;
    Image currentTileImage;
    Rectangle currentTileRect = new Rectangle(0,0, tileSize,tileSize);
    Group tiles;
    Map<Vector2<Integer>, Tile> tileMap = new HashMap<>();

    //How we align the buttons
    BorderPane borderPane = new BorderPane();
    GridPane selectorGrid = new GridPane();
    public void createEditor(Stage MenuStage) {

        System.out.println("Starting Editor...");
        Stage editorStage = new Stage();

        VBox tileSelectorAlign = new VBox();

        Scene editorScene = new Scene(borderPane, 800, 600);
        editorStage.setTitle("Tilemap Editor");
        tiles = new Group();
        currentTileRect.setFill(Color.TRANSPARENT);

        //Test for image loading
        try {
            tileset = new Image(tilesetPath);
        }catch(Exception e){
            System.out.println("Error: Could not load tileset: The tileset path is probably wrong... Either figure out how the relative path works for the good of Jahrr's sanity or use " +
                    "an absolute path to the tileset.");
            System.exit(-1);
        }


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
        borderPane.setBottom(tileSelectorAlign);
        borderPane.getChildren().add(currentTileRect);
        borderPane.getChildren().add(tiles);

        //borderPane.setCenter(currentTileRect);


        setupTilePainting();


        editorStage.show();

    }

    private void populateTileSelection(){

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
                    TileToggleButton button = new TileToggleButton(newImage, new Vector2<>(x,y));
                    button.setMinWidth(60);
                    button.setMaxWidth(60);
                    button.setToggleGroup(buttonGroup);
                    selectorGrid.add(button, it%12, it/12);
                    button.setOnAction(actionEvent -> {
                        currentTexCoords = button.textureCoordinates;
                        currentTileImage = button.tileImage;
                        currentTileRect.setFill(new ImagePattern(currentTileImage));
                    });
                    it++;
                }

            }
        }

    }

    private void setupTilePainting(){

        borderPane.setOnMouseMoved(mouseEvent -> {
            if(mouseEvent.getSceneY() < 470) {
                currentTileRect.setX(Math.floor(mouseEvent.getSceneX() / tileSize) * tileSize);
                currentTileRect.setY(Math.floor(mouseEvent.getSceneY() / tileSize) * tileSize);
            }
        });
        borderPane.setOnMouseDragged(mouseEvent -> {
            if(mouseEvent.getSceneY() < 470) {
                currentTileRect.setX(Math.floor(mouseEvent.getSceneX() / tileSize) * tileSize);
                currentTileRect.setY(Math.floor(mouseEvent.getSceneY() / tileSize) * tileSize);
                Vector2<Double> roundedMousePosition = new Vector2<>(Math.floor(mouseEvent.getSceneX() / tileSize) * tileSize, Math.floor(mouseEvent.getSceneY() / tileSize) * tileSize);

                if(mouseEvent.getButton() == MouseButton.SECONDARY) {
                    removeTile(roundedMousePosition);
                }else if((roundedMousePosition.x.intValue() != lastTileDrawnPosition.x ||
                        roundedMousePosition.y.intValue() != lastTileDrawnPosition.y)){
                    putTile(roundedMousePosition);
                }
            }
        });
        borderPane.setOnMouseClicked(mouseEvent -> {

            Vector2<Double> roundedMousePosition = new Vector2<>(Math.floor(mouseEvent.getSceneX() / tileSize) * tileSize, Math.floor(mouseEvent.getSceneY() / tileSize) * tileSize);
            if(mouseEvent.getButton() == MouseButton.SECONDARY){
                removeTile(roundedMousePosition);
            }
            else if(mouseEvent.getSceneY() < 470 && (roundedMousePosition.x.intValue() != lastTileDrawnPosition.x ||
                    roundedMousePosition.y.intValue() != lastTileDrawnPosition.y)){
                putTile(roundedMousePosition);
            }

        });

        borderPane.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.SLASH){
                System.out.println("Number of Values: " + tileMap.size());
            }
        });

    }


    private void putTile(Vector2<Double> position){
        Rectangle drawnRect = new Rectangle(position.x, position.y,
                tileSize, tileSize);
        drawnRect.setFill(new ImagePattern(currentTileImage));

        drawnRect.setUserData(position);
        tiles.getChildren().add(drawnRect);
        lastTileDrawnPosition = new Vector2<>((int) (drawnRect.getX()), (int) drawnRect.getY());

        Tile drawnTile = new Tile();
        drawnTile.globalPosition = lastTileDrawnPosition;
        drawnTile.tilemapPosition = new Vector2<>(lastTileDrawnPosition.x/tileSize, lastTileDrawnPosition.y/tileSize);
        drawnTile.textureCoordinates = currentTexCoords;
        //Layers start at 0, and FindLayerByPosition will always return n >= 1 in this context (Right after we push a tile) so we take one away
        drawnTile.layer = FindLayerByPosition(position) - 1;

        tileMap.put(new Vector2<>(lastTileDrawnPosition.x/tileSize, lastTileDrawnPosition.y/tileSize), drawnTile);

        System.out.println("Pushed new tile to tilemap:");
        System.out.println("globalPosition: " + drawnTile.globalPosition.returnVectorString());
        System.out.println("tilemapPosition: " + drawnTile.tilemapPosition.returnVectorString());
        System.out.println("textureCoordinates: " + drawnTile.textureCoordinates.returnVectorString());
        System.out.println("layer: " + drawnTile.layer + '\n');
    }

    private void removeTile(Vector2<Double> position){

        while(tiles.getChildren().remove(FindRectangleByPosition(position)));
        tileMap.remove(new Vector2<>(position.x.intValue() / tileSize, position.y.intValue() / tileSize));
    }

    //If someone wants to implement a non-linear search algorithm that has better than O(n) complexity feel free to do so
    Node FindRectangleByPosition(Vector2<Double> position){
        for(Node i : tiles.getChildren()){

            //JUST LET ME COMPARE I.GETUSERDATA() WITH POSITION PLEASE FOR THE LOVE OF GOD
            if (((Vector2<?>) i.getUserData()).x.equals(position.x) && ((Vector2<?>) i.getUserData()).y.equals(position.y)){
                return i;
            }

        }
        return null;
    }

    int FindLayerByPosition(Vector2<Double> position){
        int layer = 0;
        for(Node i : tiles.getChildren()){
            if (((Vector2<?>) i.getUserData()).x.equals(position.x) && ((Vector2<?>) i.getUserData()).y.equals(position.y)){
                layer++;
            }
        }
        return layer;
    }



}
