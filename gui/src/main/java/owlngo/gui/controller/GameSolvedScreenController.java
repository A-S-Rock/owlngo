package owlngo.gui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import owlngo.gui.data.DataManager;

/** Contoller class for GameSolvedScreen.fxml. */
public class GameSolvedScreenController {

  @FXML Button retryButton;
  @FXML Button backToWelcomeScreenButton;
  @FXML Button exitGameButton;
  @FXML Pane imagePane;
  @FXML Label completedTimeLabel;

  @FXML
  void initialize() {
    retryButton.setOnAction(
        new EventHandler<>() {
          @Override
          public void handle(ActionEvent event) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GameViewScreen.fxml"));
            ControllerUtils.createScene(event, fxmlLoader);
          }
        });

    backToWelcomeScreenButton.setOnAction(
        new EventHandler<>() {
          @Override
          public void handle(ActionEvent event) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/WelcomeScreen.fxml"));
            ControllerUtils.createScene(event, fxmlLoader);
          }
        });

    exitGameButton.setOnAction(event -> System.exit(0));

    imagePane.getChildren().add(ControllerUtils.createLogoPane("win"));

    completedTimeLabel.textProperty().bind(DataManager.getInstance().getTimeStringProperty());
  }
}
