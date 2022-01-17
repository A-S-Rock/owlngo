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
import javafx.stage.Stage;

/**
 * Contoller class for GameOverScreen.fxml.
 */
public class GameOverScreenController {

  @FXML Button backToWelcomeScreenButton;

  @FXML
  void initialize() {
    backToWelcomeScreenButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GameOverScreen.fxml"));
            try {
              Parent root = fxmlLoader.load();
              Stage scene = new Stage();
              scene.setTitle("Bauernschach v1.0");
              scene.setScene(new Scene(root, 800, 600));
              scene.setResizable(false);
              scene.show();
              ((Node) (event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
              System.exit(0);
            }
          }
        });
  }
}
