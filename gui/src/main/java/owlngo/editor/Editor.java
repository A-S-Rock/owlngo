package owlngo.editor;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/** Starter class for the editor window. */
public class Editor extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    StoreLastKey.setSpaceAsDefault();

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
