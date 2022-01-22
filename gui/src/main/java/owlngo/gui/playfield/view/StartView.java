package owlngo.gui.playfield.view;

import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/** Class adds a house-symbol in a rectangle as a start tile. */
public class StartView extends StackPane {

  private Image startImage;

  /** Constructor loads a png-image and makes a rectangle filled with this image. */
  public StartView() {
    try {
      startImage =
          new Image(Objects.requireNonNull(getClass().getResource("/images/start.png")).toString());
    } catch (IllegalArgumentException e) {
      System.err.println("Image not found.");
    }
    Rectangle playerElement =
        new Rectangle(GameView.TILE_SIZE, GameView.TILE_SIZE, Color.TRANSPARENT);
    playerElement.setFill(new ImagePattern(startImage));
    getChildren().add(playerElement);
  }
}
