package owlngo.game;

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

  private GameState gameState;

  /** Constructs an Owlngo game instance with the prespecified level dimensions. */
  public OwlnGo() {
    gameState = new GameState(DEFAULT_NUM_ROWS, DEFAULT_NUM_COLS);
  }

  /** Constructs an Owlngo game instance with the given level dimensions. */
  public OwlnGo(int numRows, int numCols) {
    gameState = new GameState(numRows, numCols);
  }

  /** Constructs an Owlngo game instance with a given level. */
  public OwlnGo(Level level) {
    gameState = new GameState(level);
  }

  /** Get the current GameState. */
  public final GameState getGameState() {
    return gameState;
  }

  private void checkWinningConditions(Move move) {
    Coordinate finishCoordinate = gameState.getLevel().getCopyOfFinishObject().getCoordinate();
    if (move.getNewCoordinate().equals(finishCoordinate)) {
      gameState = gameState.with(GameStatus.WIN);
    } else if (move.getNewCoordinate().getRow() == gameState.getLevel().getNumRows() - 1) {
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
  public void moveBasicJump() {
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
  public void moveBasicFall() {
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
  public void moveJumpRight() {
    moveBasicJump();
    moveBasicRight();
    moveBasicRight();
    moveContinousFall();
  }

  /** Lets the player jump left. */
  public void moveJumpLeft() {
    moveBasicJump();
    moveBasicLeft();
    moveBasicLeft();
    moveContinousFall();
  }

  public void moveFly() {
    moveBasicJump();
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
