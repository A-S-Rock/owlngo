package owlngo.editor;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Editor extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  // public void start(Stage primaryStage) throws Exception {
  public void start(Stage primaryStage) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("/EditorWindow.fxml")));
    System.out.println("FxmlLoader" + fxmlLoader);

    try {
      Parent root = fxmlLoader.load();
      System.out.println("Root:" + root);
      Stage stage = new Stage();
      stage.setTitle("Owlngo Editor");
      stage.setScene(new Scene(root, 800, 600));
      stage.setResizable(true);
      stage.show();
      //       ((Node) event.getSource()).getScene().getWindow().hide();  // hide StartWindow
    } catch (IOException e) {
      System.out.println("Exeption Line 38 " + e);
      e.getCause().getCause();
      System.exit(0);
    }
  }
}
