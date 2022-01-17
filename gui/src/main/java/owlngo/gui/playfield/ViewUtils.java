package owlngo.gui.playfield;

import javafx.scene.Scene;
import javafx.stage.Stage;
import owlngo.game.GameState;
import owlngo.game.GameState.GameStatus;
import owlngo.game.OwlnGo;

/** Serves as an utility class for various view - dependant tasks. */
public class ViewUtils {

  static final int NUM_LEVEL_COLUMNS = 10;
  static final int NUM_LEVEL_ROWS = 10;

  /**
   * Sets the scene of the current window to the game window. Also, the game now checks if the game
   * has stopped.
   *
   * @param stage current GUI stage
   */
  public static void setSceneToGameView(Stage stage) {
    OwlnGo game = new OwlnGo(NUM_LEVEL_ROWS, NUM_LEVEL_COLUMNS);

    game.getGameState()
        .propertyStatus()
        .addListener(
            ((observable, oldValue, newValue) -> {
              if (newValue == GameStatus.ONGOING) {
                // TODO: Implement victory screen after game stops
                System.out.println(" ");
              }
            }));

    stage.setScene(new Scene(new GameView(game)));
  }

  public static double getTileX(double levelWidth, int column) {
    return (column * levelWidth / ViewUtils.NUM_LEVEL_COLUMNS);
  }

  public static double getTileY(double levelHeight, int row) {
    return (row * levelHeight / ViewUtils.NUM_LEVEL_ROWS);
  }
}
