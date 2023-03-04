package game.map;

import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import utility.Vector2;

import java.awt.*;


//Created by Jahrr on 3/2/22
//This class does the exact same thing as ToggleButton, but can store some other values
//we need this class so we know what tile to jump to when its respective button is pressed.

//Feel free to add member variables as needed
public class TileToggleButton extends ToggleButton {




    public Vector2<Integer> textureCoordinates;
    public WritableImage tileImage;

    public TileToggleButton(WritableImage buttonImage, Vector2<Integer> texCoords) {
        textureCoordinates = texCoords;
        tileImage = buttonImage;
        setGraphic(new ImageView(tileImage));
    }
}
