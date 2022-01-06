package owlngo.gui.editor;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/** Starter class for the editor window. */
// 6.1. 14.20
public class Editor extends Application {
  public static void main(String[] args) {
    launch(args);
  }


  /**
   * Called by FXML when FXML is started
   */
  @Override
  public void start(Stage primaryStage) {
    StoreLastKey.setSpaceAsDefault();
    // set a default key. This is required to get no Null pointer
    // exception when getLastKeyPressed() is called without a key being pressed.

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("/EditorWindow.fxml")));
    System.out.println("FxmlLoader" + fxmlLoader);

    try {
      Parent root = fxmlLoader.load();
      System.out.println("Root:" + root);
      Stage stage = new Stage();
      stage.setTitle("Owlngo Editor");
      Scene scene = new Scene(root, 800, 600);
      // Set up keyHandler
      scene.setOnKeyPressed(
          event -> {
            KeyCode keyCode = event.getCode();
            StoreLastKey.setLastKeyPressed(keyCode); // StoreKey in order to get
            // the last press key asynchonous during mouse click events
          });

      stage.setScene(scene);
      stage.setResizable(true);
      stage.show();
    } catch (IOException e) {
      Platform.exit();
    }
  }
}
