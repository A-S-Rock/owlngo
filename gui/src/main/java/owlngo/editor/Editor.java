package owlngo.editor;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

// This class opens the Playfield-Playfield by the start method that is required by fxml
// This class also sets a handle for key events on the scene.

public class Editor extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  // public void start(Stage primaryStage) throws Exception {
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
          new EventHandler<>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
              KeyCode keyCode = event.getCode();
              // System.out.println("Tastendruck:"+ keyCode);
              StoreLastKey.setLastKeyPressed(event.getCode()); // StoreKey in order to get
              // the last press key asynchonous during mouse click events
            }
          });

      stage.setScene(scene);
      // stage.setScene(new Scene(root, 800, 600));
      stage.setResizable(true);
      stage.show();
      //       ((Node) event.getSource()).getScene().getWindow().hide();  // hide StartWindow
    } catch (IOException e) {
      e.getCause().getCause();
      System.exit(0);
    }
  }
}
