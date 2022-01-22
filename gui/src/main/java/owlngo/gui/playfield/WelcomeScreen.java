package owlngo.gui.playfield;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** Main class that shows scenes in a window. */
public class WelcomeScreen extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/WelcomeScreen.fxml"));

    Parent root = fxmlLoader.load();

    primaryStage.setTitle("Owlngo");
    primaryStage.isResizable();
    primaryStage.setScene(new Scene(root));
    primaryStage.setResizable(true);
    primaryStage.show();
  }
}
