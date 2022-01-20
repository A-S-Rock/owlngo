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

/** Contoller class for HighscoreScreen.fxml. */
public class HighscoreScreenController {

  @FXML Button backToWelcomeScreenButton;

  @FXML
  void initialize() {
    backToWelcomeScreenButton.setOnAction(
        new EventHandler<>() {
          @Override
          public void handle(ActionEvent event) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/WelcomeScreen.fxml"));
            try {
              Parent root = fxmlLoader.load();
              Stage scene = new Stage();
              scene.setTitle("Owlngo");
              scene.setScene(new Scene(root, 1200, 800));
              scene.setResizable(false);
              scene.show();
              ((Node) (event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
              System.out.println("IO Exception");
            }
          }
        });
  }
}
