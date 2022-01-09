package owlngo.gui.playfield;

import java.util.Map;
import java.util.Objects;
import javafx.beans.property.MapProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import owlngo.game.OwlnGo;
import owlngo.game.level.Level;
import owlngo.game.level.objects.ObjectInGame;
import owlngo.game.level.objects.ObjectInGame.ObjectType;

/**
 * Class shows our playingfield on the left side and an information field with buttons, etc. on the
 * right side.
 */
public class GameView extends HBox {

  OwlnGo game;
  static final int TILE_SIZE = 40;

  /** Constructor without a parameter. */
  public GameView() {
    game = new OwlnGo(21, 21);
    getChildren().addAll(createLevelView(game), createSidePanel(game));
  }

  /**
   * Constructor that uses a given game.
   *
   * @param game is a given owlngo game
   */
  public GameView(OwlnGo game) {
    getChildren().addAll(createLevelView(game), createSidePanel(game));
  }

  private Node createLevelView(OwlnGo game) {
    TilePane levelView = new TilePane();

    Level level = game.getGameState().getLevel();
    final int rows = level.getNumRows();
    final int cols = level.getNumColumns();

    levelView.setPrefRows(rows);
    levelView.setPrefColumns(cols);

    Map<Integer, MapProperty<Integer, ObjectInGame>> levelProperties = level.propertyLevelLayout();

    for (int currentRow = 0; currentRow < rows; currentRow++) {
      MapProperty<Integer, ObjectInGame> rowProperty =
          Objects.requireNonNull(levelProperties.get(currentRow));

      for (int currentColumn = 0; currentColumn < cols; currentColumn++) {
        StackPane tileContent = new StackPane();

        final ObjectInGame objectInGame = rowProperty.get(currentColumn);
        if (objectInGame.getType().equals(ObjectType.GROUND)) {
          tileContent.getChildren().add(new GroundView());
        } else if (objectInGame.getType().equals(ObjectType.PLAYER)) {
          tileContent.getChildren().add(new AirView());
          tileContent.getChildren().add(new PlayerView());
        } else if (objectInGame.getType().equals(ObjectType.AIR)) {
          tileContent.getChildren().add(new AirView());
        } else if (objectInGame.getType().equals(ObjectType.START)) {
          tileContent.getChildren().add(new AirView());
          tileContent.getChildren().add(new StartView());
        } else if (objectInGame.getType().equals(ObjectType.FINISH)) {
          tileContent.getChildren().add(new AirView());
          tileContent.getChildren().add(new FinishView());
        } else {
          throw new AssertionError("Object type does not exist.");
        }

        int finalCurrentColumn = currentColumn;
        rowProperty.addListener(
            (observable, oldValue, newValue) -> {
              System.out.println("Something changed");
              System.out.println("Observable: " + observable);
              System.out.println("Old Value: " + oldValue);
              System.out.println("New Value: " + newValue);
              tileContent.getChildren().clear();
              final ObjectInGame playerTile = newValue.get(finalCurrentColumn);
              System.out.println("Coords of PlayerTile: " + playerTile.getCoordinate().toString());
              if (objectInGame.getType().equals(ObjectType.PLAYER)) {
                tileContent.getChildren().add(new AirView());
                tileContent.getChildren().add(new Rectangle(20, 20, Color.RED));
              }
            });
        levelView.getChildren().add(tileContent);
      }
    }
    return levelView;
  }

  private Node createSidePanel(OwlnGo game) {

    return new VBox(new Button("Test Button"));
  }

  public void interpreteKeyEntries(KeyCode keyCode, OwlnGo game) {
    if (keyCode == KeyCode.W) {
      game.moveJump(false); // jumping without falling for testing
      System.out.println("W -> Jump");
    } else if (keyCode == KeyCode.S) {
      System.out.println("S -> Down");
    } else if (keyCode == KeyCode.D) {
      System.out.println("D -> Right");
      game.moveRight();
    } else if (keyCode == KeyCode.A) {
      System.out.println("A -> Left");
      game.moveLeft();
    }
  }
}
