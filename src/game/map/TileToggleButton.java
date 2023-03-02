package game.map;

import javafx.scene.control.ToggleButton;
import utility.Vector2;


//Created by Jahrr on 3/2/22
//This class does the exact same thing as ToggleButton, but can store some other values
//we need this class so we know what tile to jump to when its respective button is pressed.

//Feel free to add member variables as needed
public class TileToggleButton extends ToggleButton {




    public Vector2<Integer> textureCoordinates;

    public TileToggleButton(String name, Vector2<Integer> texCoords) {
        super(name);
        textureCoordinates = texCoords;
    }
}
