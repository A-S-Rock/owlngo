package owlngo.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import owlngo.gui.playfield.GameView;

/** Main class that shows scenes in a window. */
public class Main extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Owlngo");
    Scene scene =
        new Scene(new GameView(), 1400, 900); // ToDo remove/replace the magic numbers here
    primaryStage.setScene(scene);
    primaryStage.setResizable(true);
    primaryStage.show();
  }
}
