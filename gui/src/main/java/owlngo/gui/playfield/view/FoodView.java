package owlngo.gui.playfield.view;

import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/** Class adds a mouse-themed rectangle as a food tile. */
public class FoodView extends StackPane {

  private Image foodImage;

  /** Constructor loads a png-image and makes a rectangle filled with this image. */
  public FoodView() {
    try {
      foodImage =
          new Image(
              Objects.requireNonNull(getClass().getResource("/images/food_animated.gif"))
                  .toString());
    } catch (IllegalArgumentException e) {
      System.err.println("Image not found.");
    }
    Rectangle groundElement =
        new Rectangle(GameView.TILE_SIZE, GameView.TILE_SIZE, Color.TRANSPARENT);
    groundElement.setFill(new ImagePattern(foodImage));
    getChildren().add(groundElement);
  }
}
