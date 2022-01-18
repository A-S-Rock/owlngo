package owlngo.gui.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import owlngo.game.OwlnGo;
import owlngo.gui.playfield.GameView;

/** Contoller class for GameViewScreen.fxml. */
public class GameViewScreenController {
  @FXML Button backToMainMenuButton;
  @FXML Button giveUpButton;
  @FXML Pane gamePane;

  @FXML
  void initialize() {
    OwlnGo game = new OwlnGo(10, 10);
    gamePane.getChildren().addAll(new GameView(game));

    backToMainMenuButton.setOnAction(
        new EventHandler<>() {
          @Override
          public void handle(ActionEvent event) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/WelcomeScreen.fxml"));
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

    giveUpButton.setOnAction(
        new EventHandler<>() {
          @Override
          public void handle(ActionEvent event) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GameOverScreen.fxml"));
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
  }
}
