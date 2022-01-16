package owlngo.gui.playfield;

import javafx.scene.Scene;
import javafx.stage.Stage;
import owlngo.game.GameState.GameStatus;
import owlngo.game.OwlnGo;

public class ViewUtils {

  static final int NUM_LEVEL_COLUMNS = 10;
  static final int NUM_LEVEL_ROWS = 10;

  public static void setSceneToGameView(Stage stage) {
    OwlnGo game = new OwlnGo(NUM_LEVEL_ROWS, NUM_LEVEL_COLUMNS);

    game.getGameState()
        .propertyStatus()
        .addListener(
            ((observable, oldValue, newValue) -> {
              if (newValue == GameStatus.ONGOING) {
                return;
              }
            }));

    stage.setScene(new Scene(new GameView(game)));
  }
}
