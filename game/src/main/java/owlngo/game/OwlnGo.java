package owlngo.game;

import owlngo.game.GameState.GameStatus;
import owlngo.game.level.Coordinate;
import owlngo.game.level.Level;
import owlngo.game.level.Move;
import owlngo.game.level.objects.Player;

/**
 * Represents an Owlngo game. This class implements the game logic. Similar to Bauernschach from
 * Task 4.
 */
public class OwlnGo {

  static final int DEFAULT_NUM_ROWS = 30;
  static final int DEFAULT_NUM_COLS = 30;
  static final int endurance = 10;

  private GameState gameState;
  private SideConditions sideConditions;

  /** Constructs an Owlngo game instance with the prespecified level dimensions and prespecified sideConditions. */
  public OwlnGo() {
    gameState = new GameState(DEFAULT_NUM_ROWS, DEFAULT_NUM_COLS);
    sideConditions = new SideConditions(endurance);
  }

  /** Constructs an Owlngo game instance with the given level dimensions and prespecified sideConditions. */
  public OwlnGo(int numRows, int numCols) {
    gameState = new GameState(numRows, numCols);
    sideConditions = new SideConditions(endurance);
  }

  /** Constructs an Owlngo game instance with a given level and prespecified sideConditions. */
  public OwlnGo(Level level) {
    gameState = new GameState(level);
    sideConditions = new SideConditions(endurance);
  }

  /** Get the current GameState. */
  public final GameState getGameState() {
    return gameState;
  }

  private void checkWinningConditions(Move move) {
    Coordinate finishCoordinate = gameState.getLevel().getCopyOfFinishObject().getCoordinate();
    if (move.getNewCoordinate().equals(finishCoordinate)) {
      gameState = gameState.with(GameStatus.WIN);
    } else if (move.getNewCoordinate().getRow() == gameState.getLevel().getNumRows() - 1 || sideConditions.getEndurance() == 0) {
      gameState = gameState.with(GameStatus.LOSE);
    }
  }

  /** Moves the player to the right. */
  public void moveRight() {
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
      //moveFall();
    }
  }

  /** Moves the player to the left. */
  public void moveLeft() {
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
      //moveFall();
    }
  }

  /** Lets the player jump. */
  public void moveJump(boolean activateFall) {
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
      if (activateFall) {
        moveFall();
      }
    }
  }

  /** Lets the player fall. */
  public void moveFall() {
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
}
