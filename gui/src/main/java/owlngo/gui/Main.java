package owlngo.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import owlngo.game.OwlnGo;
import owlngo.gui.playfield.GameView;

/** Main class that shows scenes in a window. */
public class Main extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Owlngo");
    OwlnGo game = new OwlnGo(10, 10);
    GameView gameView = new GameView(game);
    Scene scene = new Scene(gameView, 1400, 900); // ToDo remove/replace the magic numbers here
    scene.setOnKeyPressed(
        event -> {
          KeyCode keyCode = event.getCode();
          gameView.interpreteKeyEntries(keyCode, game);
        });
    primaryStage.setScene(scene);
    primaryStage.setResizable(true);
    primaryStage.show();
  }
}
