package owlngo.game;

import owlngo.game.GameState.GameStatus;
import owlngo.game.level.Coordinate;
import owlngo.game.level.Move;
import owlngo.game.level.objects.Player;

/**
 * Represents an Owlngo game. This class implements the game logic. Similar to Bauernschach from
 * Task 4.
 */
public class OwlnGo {

  static final int DEFAULT_NUM_ROWS = 30;
  static final int DEFAULT_NUM_COLS = 30;

  private GameState gameState;

  /** Constructs an Owlngo game instance with the prespecified level dimensions. */
  public OwlnGo() {
    gameState = new GameState(DEFAULT_NUM_ROWS, DEFAULT_NUM_COLS);
  }

  /** Constructs an Owlngo game instance with the given level dimensions. */
  public OwlnGo(int numRows, int numCols) {
    gameState = new GameState(numRows, numCols);
  }

  /** Get the current GameState. */
  public final GameState getGameState() {
    return gameState;
  }

  private void checkWinningConditions(Move move) {
    Coordinate finishCoordinate = gameState.getLevel().getCopyOfFinishObject().getCoordinate();
    if (move.getNewCoordinate().equals(finishCoordinate)) {
      gameState = gameState.with(GameStatus.WIN);
    } else if (move.getNewCoordinate().getRow() == 0) {
      gameState = gameState.with(GameStatus.LOSE);
    }
  }

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
      moveFall();
    }
  }

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
      moveFall();
    }
  }

  public void moveJump() {
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
      moveFall();
    }
  }

  public void moveFall() {
    if (!gameState.isGameRunning()) {
      System.out.println("Game is not running.");
      return;
    }
    Player player = gameState.getPlayer();

    Move move = player.getFallMove();
    gameState.moveObjectInGame(move);

    checkWinningConditions(move);
    if (gameState.isGameRunning()) {
      gameState.getLevel().updatePossibleMovesOfPlayer();
    }
  }
}
