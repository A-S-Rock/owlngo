package owlngo.gui.playfield;

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class GroundView extends StackPane {
  Image soil;

  public GroundView() {
    try {
      soil = new Image(getClass().getResource("/images/soil.png").toString());
    } catch (IllegalArgumentException e) {
      System.err.println("Image not found.");
    }
    Rectangle groundElement =
        new Rectangle(GameView.TILE_SIZE, GameView.TILE_SIZE, Color.TRANSPARENT);
    groundElement.setFill(new ImagePattern(soil));
    getChildren().add(groundElement);
  }
}
