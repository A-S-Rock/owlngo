package owlngo.gui.playfield;

import java.util.Objects;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import owlngo.game.GameState;
import owlngo.game.OwlnGo;

/** Class adds a owl-themed rectangle as a player tile. */
public class PlayerView extends StackPane {
  Image playerImage;

  /** Constructor loads a png-image and creates a rectangle filled with this image. */
  public PlayerView() {
    try {
      playerImage =
          new Image(
              Objects.requireNonNull(getClass().getResource("/images/ingo40x40.png")).toString());
    } catch (IllegalArgumentException e) {
      System.err.println("Image not found.");
    }
    Rectangle playerElement =
        new Rectangle(GameView.TILE_SIZE, GameView.TILE_SIZE, Color.TRANSPARENT);
    playerElement.setFill(new ImagePattern(playerImage));

    getChildren().add(playerElement);
  }
}
