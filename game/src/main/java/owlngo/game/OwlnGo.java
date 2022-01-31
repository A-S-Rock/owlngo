package owlngo.game;

import javafx.application.Platform;
import owlngo.game.GameState.GameStatus;
import owlngo.game.level.Coordinate;
import owlngo.game.level.Level;
import owlngo.game.level.Move;
import owlngo.game.level.objects.ObjectInGame.ObjectType;
import owlngo.game.level.objects.Player;

/**
 * Represents an Owlngo game. This class implements the game logic. Similar to Bauernschach from
 * Task 4.
 */
public class OwlnGo {

  static final int DEFAULT_NUM_ROWS = 30;
  static final int DEFAULT_NUM_COLS = 30;
  static final int ENDURANCE = 10;

  private GameState gameState;
  private final SideConditions sideConditions;

  /**
   * Constructs an Owlngo game instance with the prespecified level dimensions and prespecified
   * sideConditions.
   */
  public OwlnGo() {
    gameState = new GameState(DEFAULT_NUM_ROWS, DEFAULT_NUM_COLS);
    sideConditions = new SideConditions(ENDURANCE);
  }

  /**
   * Constructs an Owlngo game instance with the given level dimensions and prespecified
   * sideConditions.
   */
  public OwlnGo(int numRows, int numCols) {
    gameState = new GameState(numRows, numCols);
    sideConditions = new SideConditions(ENDURANCE);
  }

  /** Constructs an Owlngo game instance with a given level and prespecified sideConditions. */
  public OwlnGo(Level level) {
    gameState = new GameState(level);
    sideConditions = new SideConditions(ENDURANCE);
  }

  /** Get the current GameState. */
  public final GameState getGameState() {
    return gameState;
  }

  private void checkWinningConditions(Move move) {
    Coordinate finishCoordinate = gameState.getLevel().getCopyOfFinishObject().getCoordinate();
    if (move.getNewCoordinate().equals(finishCoordinate)) {
      gameState = gameState.with(GameStatus.WIN);
    } else if (move.getNewCoordinate().getRow() == gameState.getLevel().getNumRows() - 1
        || sideConditions.getEndurance() == 0) {
      gameState = gameState.with(GameStatus.LOSE);
    }
  }

  /** Moves the player to the right. */
  public void moveBasicRight() {
    if (!gameState.isGameRunning()) {
      System.out.println("Game is not running.");
      return;
    }
    Player player = gameState.getPlayer();
    Move move = player.getRightMove();
    gameState.moveObjectInGame(move);
    checkWinningConditions(move);
    if (gameState.isGameRunning()) {
      gameState.getLevel().updatePossibleMovesOfPlayer();
    }
  }

  /** Moves the player to the left. */
  public void moveBasicLeft() {
    if (!gameState.isGameRunning()) {
      System.out.println("Game is not running.");
      return;
    }
    Player player = gameState.getPlayer();
    Move move = player.getLeftMove();
    gameState.moveObjectInGame(move);
    checkWinningConditions(move);
    if (gameState.isGameRunning()) {
      gameState.getLevel().updatePossibleMovesOfPlayer();
    }
  }

  /** Lets the player jump. */
  public void moveBasicUp() {
    if (!gameState.isGameRunning()) {
      System.out.println("Game is not running.");
      return;
    }
    Player player = gameState.getPlayer();
    Move move = player.getJumpMove();
    gameState.moveObjectInGame(move);
    checkWinningConditions(move);
    if (gameState.isGameRunning()) {
      gameState.getLevel().updatePossibleMovesOfPlayer();
    }
  }

  /** Lets the player fall. */
  public void moveBasicDown() {
    if (!gameState.isGameRunning()) {
      System.out.println("Game is not running.");
      return;
    }
    Player player = gameState.getPlayer();
    Move move = player.getFallMove();
    gameState.moveObjectInGame(move);
    checkWinningConditions(move);
    // This might be obsolete because updates takes place in moveObjectInGame(move).
    if (gameState.isGameRunning()) {
      gameState.getLevel().updatePossibleMovesOfPlayer();
    }
  }

  /** Lets the player jump right up and fall down. */
  public void moveJumpRight() throws InterruptedException {
    Platform.runLater(this::moveBasicUp);
    Thread.sleep(300);
    Platform.runLater(this::moveBasicRight);
    Thread.sleep(300);
    Platform.runLater(this::moveBasicRight);
    Thread.sleep(300);
    Platform.runLater(this::moveContinousFall);
  }

  /** Lets the player jump left. */
  public void moveJumpLeft() {
    moveBasicUp();
    moveBasicLeft();
    moveBasicLeft();
    moveContinousFall();
  }

  /** Lets the player jump left. */
  public void moveFlyUp() {
    moveBasicUp();
    sideConditions.decreaseEndurance();
    /*
    //ToDo: there is no other key input
    if ()
    {
      moveContinousFall();
    }

     */

  }

  /** Lets the player fall continously to the next GROUND-object. */
  public void moveContinousFall() {
    if (!gameState.isGameRunning()) {
      System.out.println("Game is not running.");
      return;
    }
    while (gameState.getLevel().getObjectInGameAt(getActualCoordinateBelowPlayer()).getType()
        != ObjectType.GROUND) {
      Player player = gameState.getPlayer();
      Move move = player.getFallMove();
      gameState.moveObjectInGame(move);
      checkWinningConditions(move);
      gameState.getLevel().updatePossibleMovesOfPlayer();
      if (move.getNewCoordinate() == player.getCoordinate()) {
        break;
      }
      // ToDo: Integrate slow falling.
    }
    // This might be obsolete because updates takes place in moveObjectInGame(move).
    if (gameState.isGameRunning()) {
      gameState.getLevel().updatePossibleMovesOfPlayer();
    }
  }

  Coordinate getActualCoordinateBelowPlayer() {
    Player player = gameState.getPlayer();
    int rowBelowPlayer = gameState.getPlayer().getCoordinate().getRow() - 1;
    return Coordinate.of(rowBelowPlayer, player.getCoordinate().getColumn());
  }
}
