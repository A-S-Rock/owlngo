package owlngo.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** Main class that shows scenes in a window. */
public class Main extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/WelcomeScreen.fxml"));

    Parent root = fxmlLoader.load();

    primaryStage.setTitle("Owlngo");
    primaryStage.isResizable();
    primaryStage.setScene(new Scene(root));
    // ViewUtils.setSceneToWelcomeScreen(primaryStage);
    // ViewUtils.setSceneToGameView(primaryStage);
    primaryStage.setResizable(true);
    primaryStage.show();
  }
}
