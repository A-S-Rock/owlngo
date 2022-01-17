package owlngo.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import owlngo.gui.playfield.ViewUtils;

/** Main class that shows scenes in a window. */
public class Main extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Owlngo");
    primaryStage.isResizable();
    ViewUtils.setSceneToWelcomeScreen(primaryStage);
    //ViewUtils.setSceneToGameView(primaryStage);
    primaryStage.setResizable(true);
    primaryStage.show();
  }
}
