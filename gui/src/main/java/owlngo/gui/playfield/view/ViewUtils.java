package owlngo.gui.playfield.view;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import owlngo.game.GameState.GameStatus;
import owlngo.game.OwlnGo;
import owlngo.game.level.Level;

/** Serves as an utility class for various view - dependant tasks. */
public final class ViewUtils {

  // TODO: These values should be from the game.
  public static final int NUM_LEVEL_COLUMNS = 30;
  public static final int NUM_LEVEL_ROWS = 30;

  public static final int DEFAULT_SCENE_WIDTH = 1200;
  public static final int DEFAULT_SCENE_HEIGHT = DEFAULT_SCENE_WIDTH * 2 / 3;

  /**
   * Sets the scene of the current window to the game window. Also, the game now checks if the game
   * has stopped.
   *
   * @param stage current GUI stage
   */
  public static void setSceneToGameView(Stage stage) {
    final OwlnGo game = new OwlnGo(NUM_LEVEL_ROWS, NUM_LEVEL_COLUMNS);
    setGameStateListener(stage, game);
  }

  /**
   * Resets the scene to a custom level game.
   *
   * @param stage the current stage
   * @param level the custom level typically created by the editor
   */
  public static void setSceneToGameViewWithLevel(Stage stage, Level level) {
    final OwlnGo game = new OwlnGo(level);
    setGameStateListener(stage, game);
  }

  /**
   * Sets a listener for game status changes and reacts by opening the appropriate window.
   *
   * @param stage current stage
   * @param game game the status is listened to
   */
  public static void setGameStateListener(Stage stage, OwlnGo game) {
    final FXMLLoader fxmlWinWindow =
        new FXMLLoader(ViewUtils.class.getResource("/GameSolvedScreen.fxml"));
    final FXMLLoader fxmlLoseWindow =
        new FXMLLoader(ViewUtils.class.getResource("/GameOverScreen.fxml"));

    game.getGameState()
        .propertyStatus()
        .addListener(
            ((observable, oldValue, newValue) -> {
              if (newValue == GameStatus.LOSE) {
                try {
                  Parent root = fxmlLoseWindow.load();
                  Stage loseWindow = new Stage();
                  loseWindow.setTitle("Owlngo");
                  loseWindow.setScene(new Scene(root, DEFAULT_SCENE_WIDTH, DEFAULT_SCENE_HEIGHT));
                  loseWindow.setResizable(false);
                  loseWindow.show();
                  ((Stage) (stage.getScene().getWindow())).close();
                } catch (IOException e) {
                  System.out.println("Unable to load LoseWindow");
                }
                System.out.println("Game Lost");
              }
              if (newValue == GameStatus.WIN) {
                try {
                  Parent root = fxmlWinWindow.load();
                  Stage winWindow = new Stage();
                  winWindow.setTitle("Owlngo");
                  winWindow.setScene(new Scene(root, DEFAULT_SCENE_WIDTH, DEFAULT_SCENE_HEIGHT));
                  winWindow.setResizable(false);
                  winWindow.show();
                  ((Stage) (stage.getScene().getWindow())).close();
                } catch (IOException e) {
                  System.out.println("Unable to load WinWindow");
                }
                System.out.println("Game Won");
              }
            }));
  }

  public static double getTileX(double levelWidth, int column) {
    return (column * levelWidth / ViewUtils.NUM_LEVEL_COLUMNS);
  }

  public static double getTileY(double levelHeight, int row) {
    return (row * levelHeight / ViewUtils.NUM_LEVEL_ROWS);
  }
}
