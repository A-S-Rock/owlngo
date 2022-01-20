package owlngo.gui.controller;

import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/** Contoller class for GameViewScreen.fxml. */
public class GameViewScreenController {
  @FXML Button backToMainMenuButton;
  @FXML Button giveUpButton;
  @FXML Pane gamePane;

  @FXML
  void initialize() {

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
}
