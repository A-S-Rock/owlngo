package owlngo.gui.playfield;

import java.sql.Array;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.MapProperty;
import javafx.collections.MapChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javax.naming.SizeLimitExceededException;
import owlngo.game.GameState;
import owlngo.game.OwlnGo;
import owlngo.game.level.Level;
import owlngo.game.level.objects.ObjectInGame;
import owlngo.game.level.objects.ObjectInGame.ObjectType;

import java.util.Map;
import java.util.Objects;
import owlngo.game.level.objects.Player;

/**
 * Class shows our playingfield on the left side and a information field with buttons, etc. on the
 * right side.
 */
public class GameView extends HBox {

  OwlnGo game;
  static final int TILE_SIZE = 40;

  /** Constructor without a parameter. */
  public GameView() {
    this.game = new OwlnGo(21, 21);
    getChildren().addAll(createLevelView(game), createSidePanel(game));
  }

  /**
   * Constructor that uses a given game.
   *
   * @param game is a given owlngo game
   */
  public GameView(OwlnGo game) {
    this.game = game;
    getChildren().addAll(createLevelView(game), createSidePanel(game));
  }

  private Node createLevelView(OwlnGo game) {
    TilePane levelView = new TilePane();
    /*levelView.setOnKeyPressed(
        event -> {
          KeyCode keyCode = event.getCode();
          interpreteKeyEntries(keyCode);
          });*/

    GameState gameState = game.getGameState();
    Level level = game.getGameState().getLevel();
    final int rows = level.getNumRows();
    final int cols = level.getNumColumns();

    levelView.setPrefRows(rows);
    levelView.setPrefColumns(cols);

    //  Zeile                Spalte    Inhalt
    Map<Integer, MapProperty<Integer, ObjectInGame>> levelProperties = level.propertyLevelLayout();
    MapProperty<Integer, ObjectInGame> rowProperty;

    for (int currentRow = 0; currentRow < rows; currentRow++) {
      rowProperty = Objects.requireNonNull(levelProperties.get(currentRow));
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

        // Binden der GUI (Bewegung in Pixel) an das Model (Bewegung in der MapProperty)
        // spalte / reihe der playerProperty aus gameState binden
        /*DoubleBinding playerLocationX =
            Bindings.createDoubleBinding(
                () ->
                    (double) gameState.propertyPlayer().get().getCoordinate().getColumn()*40,
                gameState.propertyPlayer());

        */

        PlayerView actualPlayer = new PlayerView();
        DoubleBinding playerLocationX =
            Bindings.createDoubleBinding(
                () -> (double) gameState.propertyPlayer().get().getCoordinate().getColumn()*40,
                gameState.propertyPlayer());
        DoubleBinding playerLocationY =
            Bindings.createDoubleBinding(
                () -> (double) gameState.propertyPlayer().get().getCoordinate().getRow()*40,
                gameState.propertyPlayer());
        tileContent.translateXProperty().bind(playerLocationX);
        tileContent.translateYProperty().bind(playerLocationY);

        /*gameState
        .propertyPlayer()
        .addListener(
            (observable, oldValue, newValue) -> {
              System.out.println("Something changed");
              tileContent.getChildren().clear();

              if (objectInGame.getType().equals(ObjectType.PLAYER)) {
                tileContent.getChildren().add(new AirView());
                tileContent.getChildren().add(new PlayerView());
              }
            });*/
        levelView.getChildren().add(tileContent);
      }
    }
    return levelView;
  }

  private Node createSidePanel(OwlnGo game) {

    return new VBox(new Button("Test Button"));
  }


  public void interpreteKeyEntries(KeyCode keyCode) {
    if (keyCode == KeyCode.W) {
      game.moveJump(false);
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

  public void interpreteKeyEntries(KeyCode keyCode, OwlnGo game) {
    if (keyCode == KeyCode.W) {
      game.moveJump(false);
      System.out.println("W -> Jump with disabled fall");
    } else if (keyCode == KeyCode.S) {
      game.moveJump(true);
      System.out.println("S -> Jump with activated fall");
    } else if (keyCode == KeyCode.D) {
      System.out.println("D -> Right");
      game.moveRight();
    } else if (keyCode == KeyCode.A) {
      System.out.println("A -> Left");
      game.moveLeft();
    }
  }
}
