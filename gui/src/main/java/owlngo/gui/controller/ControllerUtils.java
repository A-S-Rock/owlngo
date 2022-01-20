package owlngo.gui.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Utility class offers often-used methods for creating scenes, logos, avatars.
 */
public class ControllerUtils {

  /**
   * Creates a new scene with a fxml-window.
   *
   * @param event is a click on a connected button
   * @param fxmlLoader is a sceneBuilder designed window
   */
  static void createScene(ActionEvent event, FXMLLoader fxmlLoader) {
    try {
      Parent root = fxmlLoader.load();
      Stage scene = new Stage();
      scene.setTitle("Owlngo");
      scene.setScene(new Scene(root, 1200, 800));
      scene.setResizable(true);
      scene.show();
      ((Node) (event.getSource())).getScene().getWindow().hide();
    } catch (IOException e) {
      System.out.println("IO Exception while loading a fxml-window");
    }
  }
}
