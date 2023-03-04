package game.map;

import utility.Vector2;

class Tile{
    Vector2<Integer> globalPosition;
    Vector2<Integer> tilemapPosition;


    Vector2<Integer> textureCoordinates;

    //Maybe we'll need id, maybe we won't
    int id;
    public int layer;

}