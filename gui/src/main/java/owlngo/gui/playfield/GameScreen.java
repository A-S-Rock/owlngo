package owlngo.gui.playfield;

import javafx.application.Application;
import javafx.stage.Stage;
import owlngo.gui.playfield.view.ViewUtils;

/** Main class that shows scenes in a window. */
public class GameScreen extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Owlngo");
    ViewUtils.setSceneToGameView(primaryStage);
    primaryStage.setResizable(true);
    primaryStage.show();
  }
}
