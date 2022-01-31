package owlngo.gui.playfield.view;

import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/** Class adds a fire-themed rectangle as a dangerous block tile. */
public class FireView extends StackPane {

  private Image soilImage;

  /** Constructor loads a png-image and makes a rectangle filled with this image. */
  public FireView() {
    try {
      soilImage =
          new Image(
              Objects.requireNonNull(getClass().getResource("/images/fire_animated.gif"))
                  .toString());
    } catch (IllegalArgumentException e) {
      System.err.println("Image not found.");
    }
    Rectangle groundElement =
        new Rectangle(GameView.TILE_SIZE, GameView.TILE_SIZE, Color.TRANSPARENT);
    groundElement.setFill(new ImagePattern(soilImage));
    getChildren().add(groundElement);
  }
}
