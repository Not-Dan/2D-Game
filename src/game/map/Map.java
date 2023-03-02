package game.map;

//Created by Jahrr on 3/1/23


import utility.Vector2;

//This will hold our map objects. We can create many of these classes to account for different levels
//or worlds. Note that this does not contain maps, we need to create a maploader or mapmanager class,
//as well as a world class to contain and load these objects. These should not be accessed directly,
//rather loaded in with our world class and switched to through the world class interface.
public class Map {




    String mapTilesetPath;

    Map(String tilesetPath) {
        mapTilesetPath = tilesetPath;
    }



}
