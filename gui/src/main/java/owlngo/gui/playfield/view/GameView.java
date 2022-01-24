package owlngo.gui.playfield.view;

import java.util.HashMap;
import java.util.Map;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import owlngo.game.GameState;
import owlngo.game.OwlnGo;
import owlngo.game.level.Coordinate;
import owlngo.game.level.Level;
import owlngo.game.level.objects.ObjectInGame;
import owlngo.game.level.objects.ObjectInGame.ObjectType;
import owlngo.gui.playfield.action.PlayerAction;

/**
 * Class shows our playingfield on the left side and an information field with buttons, etc. on the
 * right side.
 */
public class GameView extends HBox {

  public static final int TILE_SIZE = 40;

  private final Map<ObjectInGame, Node> movableObjectViews = new HashMap<>();

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
    GameState gameState = game.getGameState();
    fillLevelView(levelView, game);
    StackPane fullLevel = new StackPane();
    fillLevel(fullLevel, levelView, game);
    gameState
        .propertyPlayer()
        .addListener(((observable, oldValue, newValue) -> fillLevel(fullLevel, levelView, game)));

    ObjectInGame player = gameState.getPlayer();
    Node playerView = movableObjectViews.get(player);
    onKeyPressedProperty().bind(playerView.onKeyPressedProperty());

    AnchorPane root = new AnchorPane();
    root.getChildren().add(fullLevel);
    return root;
  }

  private void fillLevel(StackPane level, Region levelView, OwlnGo game) {
    level.getChildren().clear();
    level.getChildren().addAll(levelView, createMovableObjectsView(game, levelView));
  }

  private Node createMovableObjectsView(OwlnGo game, Region levelView) {
    movableObjectViews.clear();
    Pane pane = new Pane();
    pane.setMouseTransparent(true);

    GameState gameState = game.getGameState();
    Level level = gameState.getLevel();

    final int rows = level.getNumRows();
    final int columns = level.getNumColumns();

    for (int currentRow = 0; currentRow < rows; currentRow++) {
      for (int currentColumn = 0; currentColumn < columns; currentColumn++) {
        ObjectInGame object = level.getObjectInGameAt(Coordinate.of(currentRow, currentColumn));

        // Currently, only players can move.
        if (object.getType() != ObjectType.PLAYER) {
          continue;
        }
        PlayerView playerView = new PlayerView();
        movableObjectViews.put(object, playerView);

        int finalCurrentCol = currentColumn;
        int finalCurrentRow = currentRow;
        DoubleBinding pieceLocationX =
            Bindings.createDoubleBinding(
                () -> ViewUtils.getTileX(levelView.widthProperty().get(), finalCurrentCol),
                levelView.widthProperty());
        DoubleBinding pieceLocationY =
            Bindings.createDoubleBinding(
                () -> ViewUtils.getTileY(levelView.heightProperty().get(), finalCurrentRow),
                levelView.heightProperty());

        playerView.layoutXProperty().bind(pieceLocationX);
        playerView.layoutYProperty().bind(pieceLocationY);

        PlayerAction action =
            new PlayerAction(currentRow, currentColumn, game, levelView, movableObjectViews);
        playerView.setOnKeyPressed(action);
      }
    }
    pane.getChildren().addAll(movableObjectViews.values());
    return pane;
  }

  private void fillLevelView(TilePane levelView, OwlnGo game) {
    GameState gameState = game.getGameState();
    Level level = gameState.getLevel();

    final int rows = level.getNumRows();
    final int columns = level.getNumColumns();

    levelView.setPrefRows(rows);
    levelView.setPrefColumns(columns);

    for (int currentRow = 0; currentRow < rows; currentRow++) {
      for (int currentColumn = 0; currentColumn < columns; currentColumn++) {
        LevelTileView tileContent = new LevelTileView(currentRow, currentColumn, game);
        levelView.getChildren().add(tileContent);
      }
    }
  }

  private Node createSidePanel(OwlnGo game) {
    return new VBox(new Button("Test Button"));
  }
}
