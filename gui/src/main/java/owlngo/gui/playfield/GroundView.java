package owlngo.gui.playfield;

import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/** Class adds a soil-themed rectangle as a ground/soil tile. */
public class GroundView extends StackPane {
  Image soilImage;

  /** Constructor loads a png-image and makes a rectangle filled with this image. */
  public GroundView() {
    try {
      soilImage =
          new Image(Objects.requireNonNull(getClass().getResource("/images/soil.png")).toString());
    } catch (IllegalArgumentException e) {
      System.err.println("Image not found.");
    }
    Rectangle groundElement =
        new Rectangle(GameView.TILE_SIZE, GameView.TILE_SIZE, Color.TRANSPARENT);
    groundElement.setFill(new ImagePattern(soilImage));
    getChildren().add(groundElement);
  }
}
