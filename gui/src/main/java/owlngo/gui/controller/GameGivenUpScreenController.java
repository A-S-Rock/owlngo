package owlngo.gui.controller;

import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/** Contoller class for GameGivenUpScreen.fxml. */
public class GameGivenUpScreenController {
  private final MediaPlayer mediaPlayer;
  @FXML Button retryButton;
  @FXML Button backToWelcomeScreenButton;
  @FXML Button exitGameButton;
  @FXML Pane imagePane;

  /** Adds music to the controller. */
  public GameGivenUpScreenController() {
    final Media media =
        new Media(Objects.requireNonNull(getClass().getResource("/music/RICKROLL.m4a")).toString());
    mediaPlayer = new MediaPlayer(media);
    mediaPlayer.play();
  }

  @FXML
  void initialize() {
    retryButton.setOnAction(
        new EventHandler<>() {
          @Override
          public void handle(ActionEvent event) {
            mediaPlayer.stop();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GameViewScreen.fxml"));
            ControllerUtils.createScene(event, fxmlLoader);
          }
        });

    backToWelcomeScreenButton.setOnAction(
        new EventHandler<>() {
          @Override
          public void handle(ActionEvent event) {
            mediaPlayer.stop();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/WelcomeScreen.fxml"));
            ControllerUtils.createScene(event, fxmlLoader);
          }
        });

    exitGameButton.setOnAction(event -> System.exit(0));

    imagePane.getChildren().add(ControllerUtils.createLogoPane("giveup"));
  }
}
