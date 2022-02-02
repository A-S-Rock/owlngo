package owlngo.gui.gamefield.view;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/** Class adds a lightblue-themed rectangle as a air/sky tile. */
public class AirView extends StackPane {

  /** Constructor makes a rectangle filled with lightblue paint. */
  public AirView() {
    Rectangle airElement = new Rectangle(GameView.TILE_SIZE, GameView.TILE_SIZE, Color.LIGHTBLUE);
    getChildren().add(airElement);
  }
}
