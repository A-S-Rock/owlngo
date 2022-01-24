package owlngo.gui.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import owlngo.game.GameState;
import owlngo.game.OwlnGo;
import owlngo.game.level.Coordinate;
import owlngo.game.level.Level;
import owlngo.game.level.objects.ObjectInGame;
import owlngo.game.level.objects.ObjectInGame.ObjectType;
import owlngo.gui.playfield.action.PlayerAction;
import owlngo.gui.playfield.view.LevelTileView;
import owlngo.gui.playfield.view.PlayerView;
import owlngo.gui.playfield.view.ViewUtils;

/** Contoller class for GameViewScreen.fxml. */
public class GameViewScreenController {
  @FXML Button backToMainMenuButton;
  @FXML Button giveUpButton;
  @FXML Pane gamePane;
  @FXML AnchorPane root;

  @FXML
  void initialize() {

    gamePane.getChildren().addAll(createGameNode());

    backToMainMenuButton.setOnAction(
        new EventHandler<>() {
          @Override
          public void handle(ActionEvent event) {
            FXMLLoader fxmlLoader =
                new FXMLLoader(
                    Objects.requireNonNull(getClass().getResource("/WelcomeScreen.fxml")));
            ControllerUtils.createScene(event, fxmlLoader);
          }
        });

    giveUpButton.setOnAction(
        new EventHandler<>() {
          @Override
          public void handle(ActionEvent event) {
            FXMLLoader fxmlLoader =
                new FXMLLoader(
                    Objects.requireNonNull(getClass().getResource("/GameGivenUpScreen.fxml")));
            ControllerUtils.createScene(event, fxmlLoader);
          }
        });
  }

  static final int NUM_LEVEL_COLUMNS = ViewUtils.NUM_LEVEL_COLUMNS;
  static final int NUM_LEVEL_ROWS = ViewUtils.NUM_LEVEL_ROWS;
  private final Map<ObjectInGame, Node> movableObjectViews = new HashMap<>();

  private Node createGameNode() {

    //ToDo: Hier der Bind an den GameState.Running einfügen.

    OwlnGo game = new OwlnGo(NUM_LEVEL_COLUMNS, NUM_LEVEL_ROWS);
    Stage stage = (Stage) gamePane.getScene().getWindow();


    ViewUtils.checkIfGameHasStopped(game, stage);


    GameState gameState = game.getGameState();
    TilePane levelView = new TilePane();
    fillLevelView(levelView, game);
    StackPane fullLevel = new StackPane();
    fillLevel(fullLevel, levelView, game);

    gameState
        .propertyPlayer()
        .addListener(((observable, oldValue, newValue) -> fillLevel(fullLevel, levelView, game)));

    ObjectInGame player = gameState.getPlayer();
    Node playerView = movableObjectViews.get(player);
    root.onKeyPressedProperty().bind(playerView.onKeyPressedProperty());
    return fullLevel;
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

}
