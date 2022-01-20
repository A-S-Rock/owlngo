package owlngo.gui.playfield.view;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import owlngo.game.GameState.GameStatus;
import owlngo.game.OwlnGo;

/** Serves as an utility class for various view - dependant tasks. */
public final class ViewUtils {

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

    final FXMLLoader fxmlWinWindow =
        new FXMLLoader(ViewUtils.class.getResource("/GameSolvedScreen.fxml"));
    final FXMLLoader fxmlLoseWindow =
        new FXMLLoader(ViewUtils.class.getResource("/GameOverScreen.fxml"));
    System.out.println(fxmlWinWindow);

    game.getGameState()
        .propertyStatus()
        .addListener(
            ((observable, oldValue, newValue) -> {
              if (newValue == GameStatus.LOSE) {
                try {
                  Parent root = fxmlLoseWindow.load();
                  Stage loseWindow = new Stage();
                  loseWindow.setTitle("Owlngo");
                  loseWindow.setScene(new Scene(root, 1200, 800));
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
                  winWindow.setScene(new Scene(root, 1200, 800));
                  winWindow.setResizable(false);
                  winWindow.show();
                  ((Stage) (stage.getScene().getWindow())).close();
                } catch (IOException e) {
                  System.out.println("Unable to load WinWindow");
                }
                System.out.println("Game Won");
              }
            }));

    // Scene gameView = new Scene(new GameView(game), 1200, 800);
    // stage.setScene(gameView);
    stage.setScene(new Scene(new GameView(game)));
  }




  public static double getTileX(double levelWidth, int column) {
    return (column * levelWidth / ViewUtils.NUM_LEVEL_COLUMNS);
  }

  public static double getTileY(double levelHeight, int row) {
    return (row * levelHeight / ViewUtils.NUM_LEVEL_ROWS);
  }
}
