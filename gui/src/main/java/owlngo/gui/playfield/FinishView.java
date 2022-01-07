package owlngo.gui.playfield;

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/** Class adds a plane-symbol in a rectangle as a finish tile. */
public class FinishView extends StackPane {
  Image finishImage;

  /** Constructor loads a png-image and makes a rectangle filled with this image. */
  public FinishView() {
    try {
      finishImage = new Image(getClass().getResource("/images/finish.png").toString());
    } catch (IllegalArgumentException e) {
      System.err.println("Image not found.");
    }
    Rectangle playerElement =
        new Rectangle(GameView.TILE_SIZE, GameView.TILE_SIZE, Color.TRANSPARENT);
    playerElement.setFill(new ImagePattern(finishImage));
    getChildren().add(playerElement);
  }
}