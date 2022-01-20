package owlngo.gui.controller;

import java.io.IOException;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import owlngo.gui.playfield.GameView;

/** Contoller class for WelcomeScreen.fxml. */
public class WelcomeScreenController {
  @FXML Button startRandomGameButton;
  @FXML Button loadLevelButton;
  @FXML Button exitGameButton;
  @FXML Button highscoreButton;
  @FXML Pane imagePane;

  private int LOGO_WIDTH = 300;
  private int LOGO_HEIGHT = LOGO_WIDTH;

  @FXML
  void initialize() {
    startRandomGameButton.setOnAction(
        new EventHandler<>() {
          @Override
          public void handle(ActionEvent event) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GameViewScreen.fxml"));
            try {
              Parent root = fxmlLoader.load();
              Stage scene = new Stage();
              scene.setTitle("Owlngo");
              scene.setScene(new Scene(root, 1200, 800));
              scene.setResizable(true);
              scene.show();
              ((Node) (event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
              System.out.println("IO Exception");
            }
          }
        });

    loadLevelButton.setOnAction(
        new EventHandler<>() {
          @Override
          public void handle(ActionEvent event) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LoadLevelScreen.fxml"));
            try {
              Parent root = fxmlLoader.load();
              Stage scene = new Stage();
              scene.setTitle("Owlngo");
              scene.setScene(new Scene(root, 1200, 800));
              scene.setResizable(true);
              scene.show();
              ((Node) (event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
              System.out.println("IO Exception");
            }
          }
        });

    highscoreButton.setOnAction(
        new EventHandler<>() {
          @Override
          public void handle(ActionEvent event) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HighscoreScreen.fxml"));
            try {
              Parent root = fxmlLoader.load();
              Stage scene = new Stage();
              scene.setTitle("Owlngo");
              scene.setScene(new Scene(root, 1200, 800));
              scene.setResizable(true);
              scene.show();
              ((Node) (event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
              System.out.println("IO Exception");
            }
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
              Objects.requireNonNull(getClass().getResource("/images/logo_animated.png"))
                  .toString());
    } catch (IllegalArgumentException e) {
      System.err.println("Image not found.");
    }
    Rectangle logoElement = new Rectangle(LOGO_WIDTH, LOGO_HEIGHT, Color.TRANSPARENT);
    logoElement.setFill(new ImagePattern(logoImage));
    return logoElement;
  }
}
