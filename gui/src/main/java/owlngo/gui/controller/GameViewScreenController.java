package owlngo.gui.controller;

import java.util.Objects;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import owlngo.game.OwlnGo;
import owlngo.gui.data.DataManager;
import owlngo.gui.playfield.view.GameView;
import owlngo.gui.playfield.view.ViewUtils;

/** Contoller class for GameViewScreen.fxml. */
public class GameViewScreenController {
  static final int NUM_LEVEL_COLUMNS = ViewUtils.NUM_LEVEL_COLUMNS;
  static final int NUM_LEVEL_ROWS = ViewUtils.NUM_LEVEL_ROWS;

  @FXML Button backToMainMenuButton;
  @FXML Button giveUpButton;
  @FXML AnchorPane gamePane;
  @FXML ProgressBar enduranceBar;

  private final OwlnGo game;

  /** Allows the controller to load a different level if possible. */
  public GameViewScreenController() {
    DataManager manager = DataManager.getInstance();
    if (manager.getLevelContent() != null) {
      game = new OwlnGo(manager.getLevelContent());
    } else {
      game = new OwlnGo(NUM_LEVEL_ROWS, NUM_LEVEL_COLUMNS);
    }
  }

  @FXML
  void initialize() {

    gamePane.getChildren().addAll(createGameNode(game));
    Task task =
        new Task<Void>() {
          @Override
          protected Void call() {
            while (game.getGameState().isGameRunning()) {
              int i = game.getSideConditions().getEndurance();
              updateProgress(i, 10);
            }
            return null;
          }
        };
    enduranceBar.progressProperty().bind(task.progressProperty());
    new Thread(task).start();

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

  private Node createGameNode(OwlnGo game) {

    final GameView gameView = new GameView(game);
    Platform.runLater(
        () -> {
          final Stage stage = (Stage) gameView.getScene().getWindow();
          ViewUtils.setGameStateListener(stage, game);
        });

    return gameView;
  }
}
