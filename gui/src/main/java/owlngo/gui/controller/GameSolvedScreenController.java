package owlngo.gui.controller;

import java.util.List;
import java.util.stream.Collectors;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/** Contoller class for GameSolvedScreen.fxml. */
public class GameSolvedScreenController {

  @FXML Button retryButton;
  @FXML Button backToWelcomeScreenButton;
  @FXML Button exitGameButton;
  @FXML Pane imagePane;

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

    animatePane(imagePane);

  }

  private void animatePane(Pane pane) {
    KeyFrame f1 =
        new KeyFrame(
            Duration.seconds(1),
            new KeyValue(pane.translateXProperty(), 350),
            new KeyValue(pane.translateYProperty(), -150));
    KeyFrame f2 =
        new KeyFrame(
            Duration.seconds(1),
            new KeyValue(pane.rotateProperty(), -180.0));
            //new KeyValue(pane.translateYProperty(), 20));
    KeyFrame f3 =
        new KeyFrame(
            Duration.seconds(1),
            new KeyValue(pane.translateXProperty(), 550),
            new KeyValue(pane.translateYProperty(), 200));
    KeyFrame f4 =
        new KeyFrame(
            Duration.seconds(1),
            new KeyValue(pane.translateXProperty(), -350),
            new KeyValue(pane.translateYProperty(), 330));
    Timeline tl = new Timeline();
    tl.getKeyFrames().addAll(f1, f2, f3, f4);
    tl.setAutoReverse(true);
    tl.setCycleCount(25);
    tl.play();
    }
}
