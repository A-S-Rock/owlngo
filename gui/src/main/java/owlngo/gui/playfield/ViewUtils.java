package owlngo.gui.playfield;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import owlngo.game.GameState.GameStatus;
import owlngo.game.OwlnGo;

/** Serves as an utility class for various view - dependant tasks. */
public final class ViewUtils {

  static final int NUM_LEVEL_COLUMNS = 20;
  static final int NUM_LEVEL_ROWS = 20;

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

    Scene gameView = new Scene(new GameView(game, stage), 1200, 800);
    stage.setScene(gameView);
  }

  public static void setSceneToWelcomeScreen(Stage stage) {
    Scene welcomeScene = new Scene(new WelcomeScreen(stage), 1200, 800);
    stage.setScene(welcomeScene);
  }

  public static void setSceneToGameOverScreen(Stage stage) {
    Scene gameOverScene = new Scene(new GameOverScreen(stage), 1200, 800);
    stage.setScene(gameOverScene);
  }

  public static double getTileX(double levelWidth, int column) {
    return (column * levelWidth / ViewUtils.NUM_LEVEL_COLUMNS);
  }

  public static double getTileY(double levelHeight, int row) {
    return (row * levelHeight / ViewUtils.NUM_LEVEL_ROWS);
  }

  public static Node createRandomGameButton(Stage stage) {
    Button button = new Button("Start new random game.");
    button.setStyle("-fx-font: 48 arial;");
    button.setOnMouseClicked(
        (evt) -> {
          setSceneToGameView(stage);
        }
    );
    return button;
  }

  public static Node createGiveUpButton(Stage stage) {
    Button button = new Button("Give up.");
    button.setStyle("-fx-font: 48 arial;");
    button.setOnMouseClicked(
        (evt) -> {
          setSceneToGameOverScreen(stage);
        }
    );
    return button;
  }
}
