package owlngo.gui.controller;

import java.util.Objects;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import owlngo.game.OwlnGo;
import owlngo.gui.playfield.view.GameView;
import owlngo.gui.playfield.view.ViewUtils;

/** Contoller class for GameViewScreen.fxml. */
public class GameViewScreenController {
  @FXML Button backToMainMenuButton;
  @FXML Button giveUpButton;
  @FXML AnchorPane gamePane;
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

  private Node createGameNode() {
    final OwlnGo game = new OwlnGo(NUM_LEVEL_COLUMNS, NUM_LEVEL_ROWS);
    final GameView gameView = new GameView(game);

    Platform.runLater(
        () -> {
          final Stage stage = (Stage) gameView.getScene().getWindow();
          ViewUtils.checkIfGameHasStopped(game, stage);
        });

    return gameView;
  }
}
