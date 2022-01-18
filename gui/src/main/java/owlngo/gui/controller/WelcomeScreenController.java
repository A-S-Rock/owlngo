package owlngo.gui.controller;

import com.sun.glass.ui.View;
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
import owlngo.gui.playfield.ViewUtils;


/**
 * Contoller class for WelcomeScreen.fxml.
 */
public class WelcomeScreenController {

  @FXML Button startRandomGameButton;

  @FXML
  void initialize() {
    startRandomGameButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GameOverScreen.fxml"));
            System.out.println(fxmlLoader);
            try {
              Parent root = fxmlLoader.load();
              System.out.println(root.toString());
              Stage scene = new Stage();
              scene.setTitle("Owlngo");
              scene.setScene(new Scene(root, 1200, 800));
              scene.setResizable(true);
              scene.show();
              ((Node) (event.getSource())).getScene().getWindow().hide();
              ViewUtils.setSceneToGameView(scene);
            } catch (IOException e) {
              System.out.println("IO Exception");
              // e.getCause().getCause().toString();
            }
          }
        });
  }
}
