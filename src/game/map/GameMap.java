package game.map;

//Created by Jahrr on 3/1/23


import utility.Vector2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//This will hold our map objects. We can create many of these classes to account for different levels
//or worlds. Note that this does not contain maps, we need to create a maploader or mapmanager class,
//as well as a world class to contain and load these objects. These should not be accessed directly,
//rather loaded in with our world class and switched to through the world class interface.
public class GameMap {




    //Can change into enum
    String mapTilesetType;


    private Map<Vector2<Integer>, Tile> tileMap;


    GameMap(){
        tileMap = new HashMap<>();
        mapTilesetType = "";
    }

    GameMap(Map<Vector2<Integer>, Tile> map) {
        tileMap = map;
    }


    GameMap(Map<Vector2<Integer>, Tile> map, String tilesetType) {
        tileMap = map;
        mapTilesetType = tilesetType;
    }

    public Tile getTile(Vector2<Integer> tilemapPosition){
        return tileMap.get(tilemapPosition);
    }

    public Map<Vector2<Integer>, Tile> getTileMap() {
        return tileMap;
    }

    public String getMapTilesetType() {
        return mapTilesetType;
    }

    public void setMapTilesetType(String mapTilesetType) {
        this.mapTilesetType = mapTilesetType;
    }


    public static GameMap loadMap(String tilemapPath) throws IOException {


        File configFile;
        try{
            configFile = new File(tilemapPath);
            Map<Vector2<Integer>, Tile> readMap = new HashMap<>();
            BufferedReader reader = new BufferedReader(new FileReader(configFile));
            String tileLine;
            int numOfTilemapAttributes = 4; //tilemapPosition, globalPosition, textureCoordinates, layer
            while ((tileLine = reader.readLine()) != null) {
                Tile loadTile = new Tile();
                for(int i = 0; i < numOfTilemapAttributes; i++) {
                    String newAttribute = tileLine.substring(0, tileLine.indexOf(i < (numOfTilemapAttributes - 1) ? ";" : "}"));
                    if(newAttribute.contains("tilemapPosition")){
                        loadTile.tilemapPosition = Vector2.getVectorFromString(newAttribute.substring(newAttribute.indexOf('[')));
                    }else if(newAttribute.contains("globalPosition")){
                        loadTile.globalPosition = Vector2.getVectorFromString(newAttribute.substring(newAttribute.indexOf('[')));
                    }else if(newAttribute.contains("textureCoordinates")){
                        loadTile.textureCoordinates = Vector2.getVectorFromString(newAttribute.substring(newAttribute.indexOf('[')));
                    }else if(newAttribute.contains("layer")){
                        loadTile.layer = Integer.parseInt(newAttribute.substring(newAttribute.indexOf(':') + 1));
                    }else{
                        System.out.println("No valid key detected");
                    }
                    tileLine = tileLine.replace(newAttribute + ";", "");
                }
                readMap.put(loadTile.tilemapPosition, loadTile);
                System.out.println("Pushed new tile to tilemap:");
                System.out.println("tilemapPosition: " + loadTile.tilemapPosition.returnVectorString());
                System.out.println("globalPosition: " + loadTile.globalPosition.returnVectorString());
                System.out.println("textureCoordinates: " + loadTile.textureCoordinates.returnVectorString());
                System.out.println("layer: " + loadTile.layer + '\n');
            }
            return new GameMap(readMap);
        } catch (Exception e){
            System.out.println("The load path does not exist or is in an incorrect format");
            return null;
        }
    }


}
