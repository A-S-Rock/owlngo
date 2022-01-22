package owlngo.gui.controller;

import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/** Contoller class for WelcomeScreen.fxml. */
public class WelcomeScreenController {
  @FXML Button startRandomGameButton;
  @FXML Button loadLevelButton;
  @FXML Button exitGameButton;
  @FXML Button highscoreButton;
  @FXML Pane imagePane;

  private static final int LOGO_WIDTH = 300;
  private static final int LOGO_HEIGHT = LOGO_WIDTH;

  @FXML
  void initialize() {
    startRandomGameButton.setOnAction(
        new EventHandler<>() {
          @Override
          public void handle(ActionEvent event) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GameViewScreen.fxml"));
            ControllerUtils.createScene(event, fxmlLoader);
          }
        });

    loadLevelButton.setOnAction(
        new EventHandler<>() {
          @Override
          public void handle(ActionEvent event) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LoadLevelScreen.fxml"));
            ControllerUtils.createScene(event, fxmlLoader);
          }
        });

    highscoreButton.setOnAction(
        new EventHandler<>() {
          @Override
          public void handle(ActionEvent event) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HighscoreScreen.fxml"));
            ControllerUtils.createScene(event, fxmlLoader);
          }
        });

    exitGameButton.setOnAction(event -> System.exit(0));

    imagePane.getChildren().add(createLogoPane());
  }

  private Rectangle createLogoPane() {
    Image logoImage = null;
    try {
      logoImage =
          new Image(
              Objects.requireNonNull(getClass().getResource("/images/logo_animated.gif"))
                  .toString());
    } catch (IllegalArgumentException e) {
      System.err.println("Image not found.");
    }
    Rectangle logoElement = new Rectangle(LOGO_WIDTH, LOGO_HEIGHT, Color.TRANSPARENT);
    logoElement.setFill(new ImagePattern(logoImage));
    return logoElement;
  }
}
