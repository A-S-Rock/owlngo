package owlngo.gui.playfield.view;

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

  /** Constructor loads a png-image and makes a rectangle filled with this image. */
  public GroundView(GameState gameState, int tileRow, int tileColumn) {
    System.out.println("GroundView");
    String name = fileNameForGround(gameState, tileRow, tileColumn);
    System.out.println("filename" + name);
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

  String fileNameForGround(GameState gameState, int row, int column) {
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

  boolean leftGrasBlocker(GameState gameState, int row, int column) {
    return isElementGrasBlockingElement(gameState, row, column - 1);
  }

  boolean rightGrasBlocker(GameState gameState, int row, int column) {
    return isElementGrasBlockingElement(gameState, row, column + 1);
  }

  boolean topGrasBlocker(GameState gameState, int row, int column) {
    return isElementGrasBlockingElement(gameState, row - 1, column);
  }

  // This method handles also coordinates this wrong range
  boolean isElementGrasBlockingElement(GameState gameState, int row, int column) {
    System.out.println("isElementGrasBlockingElement");
    if ((possibleRow(row)) && possibleColunmn(column)) {
      ObjectInGame object = gameState.getLevel().getObjectInGameAt(Coordinate.of(row, column));
      // gas growing
      return (object.getType() == ObjectType.START)
          || (object.getType() == ObjectType.GROUND)
          || (object.getType() == ObjectType.FIRE);
    } else {
      System.out.println("Not allowed values" + row + " " + column);
      return true;
    }
  }

  boolean possibleColunmn(int column) {
    return (column >= 0) && (column < ViewUtils.NUM_LEVEL_COLUMNS);
  }

  boolean possibleRow(int row) {
    return (row >= 0) && (row < ViewUtils.NUM_LEVEL_COLUMNS);
  }
}
