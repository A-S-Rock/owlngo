package owlngo.gui.gamefield.view;

import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import owlngo.game.GameState;
import owlngo.game.level.Coordinate;
import owlngo.game.level.objects.ObjectInGame;
import owlngo.game.level.objects.ObjectInGame.ObjectType;

/** Class adds a soil-themed rectangle as a ground/soil tile. */
public class GroundView extends StackPane {

  private Image soilImage;

  /** Constructor loads a png-image and makes a rectangle filled with this image. */
  public GroundView(GameState gameState, int tileRow, int tileColumn) {
    String name = fileNameForGround(gameState, tileRow, tileColumn);
    try {
      soilImage =
          new Image(Objects.requireNonNull(getClass().getResource("/images/" + name)).toString());
    } catch (IllegalArgumentException e) {
      System.err.println("Image not found.");
    }
    Rectangle groundElement =
        new Rectangle(GameView.TILE_SIZE, GameView.TILE_SIZE, Color.TRANSPARENT);
    groundElement.setFill(new ImagePattern(soilImage));
    getChildren().add(groundElement);
  }

  private String fileNameForGround(GameState gameState, int row, int column) {
    String name = "soil.png";
    if (topGrasBlocker(gameState, row, column)) {
      name = "soil.png";
    } else {
      // if left and richt is blocked but not on the top it is free
      // gas can only grow on the top only
      if (leftGrasBlocker(gameState, row, column) && (rightGrasBlocker(gameState, row, column))) {
        name = "soil_lawn_normal.png";
      } else {
        // either left or right are blocked or both are blocked, but the top is free
        // Therefore if left is blocked gras can only grow on the right side.
        if (leftGrasBlocker(gameState, row, column)
            && (!rightGrasBlocker(gameState, row, column))) {
          name = "soil_lawn_right.png";
        }
        if (!leftGrasBlocker(gameState, row, column)
            && (rightGrasBlocker(gameState, row, column))) {
          name = "soil_lawn_left.png";
        }
        if (!leftGrasBlocker(gameState, row, column)
            && (!rightGrasBlocker(gameState, row, column))) {
          name = "soil_lawn_leftright.png";
        }
      }
    }
    return name;
  }

  private boolean leftGrasBlocker(GameState gameState, int row, int column) {
    return isElementGrasBlockingElement(gameState, row, column - 1);
  }

  private boolean rightGrasBlocker(GameState gameState, int row, int column) {
    return isElementGrasBlockingElement(gameState, row, column + 1);
  }

  private boolean topGrasBlocker(GameState gameState, int row, int column) {
    return isElementGrasBlockingElement(gameState, row - 1, column);
  }

  // This method handles also coordinates this wrong range
  private boolean isElementGrasBlockingElement(GameState gameState, int row, int column) {
    if ((possibleRow(gameState, row)) && possibleColunmn(gameState, column)) {
      ObjectInGame object = gameState.getLevel().getObjectInGameAt(Coordinate.of(row, column));
      // grass growing
      return (object.getType() == ObjectType.START)
          || (object.getType() == ObjectType.GROUND)
          || (object.getType() == ObjectType.FIRE);
    } else {
      System.out.println("Not allowed values" + row + " " + column);
      return true;
    }
  }

  private boolean possibleColunmn(GameState gameState, int column) {
    return (column >= 0) && (column < gameState.getLevel().getNumColumns());
  }

  private boolean possibleRow(GameState gameState, int row) {
    return (row >= 0) && (row < gameState.getLevel().getNumRows());
  }
}
