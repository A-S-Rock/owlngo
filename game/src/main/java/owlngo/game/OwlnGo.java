package owlngo.game;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import javafx.application.Platform;
import owlngo.game.GameState.GameStatus;
import owlngo.game.level.Coordinate;
import owlngo.game.level.Level;
import owlngo.game.level.Move;
import owlngo.game.level.objects.ObjectInGame;
import owlngo.game.level.objects.ObjectInGame.ObjectType;
import owlngo.game.level.objects.Player;

/**
 * Represents an Owlngo game. This class implements the game logic. Similar to Bauernschach from
 * Task 4.
 */
public class OwlnGo {

  private static final int DEFAULT_NUM_ROWS = 30;
  private static final int DEFAULT_NUM_COLS = 30;
  private static final int ENDURANCE = 10;
  private static final boolean IN_FLIGHT_MODE = false;

  private GameState gameState;
  private final SideConditions sideConditions;

  /**
   * Constructs an Owlngo game instance with the prespecified level dimensions and prespecified
   * sideConditions.
   */
  public OwlnGo() {
    gameState = new GameState(DEFAULT_NUM_ROWS, DEFAULT_NUM_COLS);
    sideConditions = new SideConditions(ENDURANCE, IN_FLIGHT_MODE);
  }

  /**
   * Constructs an Owlngo game instance with the given level dimensions and prespecified
   * sideConditions.
   */
  public OwlnGo(int numRows, int numCols) {
    gameState = new GameState(numRows, numCols);
    sideConditions = new SideConditions(ENDURANCE, IN_FLIGHT_MODE);
  }

  /** Constructs an Owlngo game instance with a given level and prespecified sideConditions. */
  public OwlnGo(Level level) {
    gameState = new GameState(level);
    sideConditions = new SideConditions(ENDURANCE, IN_FLIGHT_MODE);
  }

  /** Get the current GameState. */
  public final GameState getGameState() {
    return gameState;
  }

  /** Get the current SideConditions. */
  @SuppressFBWarnings("EI_EXPOSE_REP")
  public final SideConditions getSideConditions() {
    return sideConditions;
  }

  public final int getMaxSideConditions() {
    return ENDURANCE;
  }

  /** Cheks winning conditions. */
  private void checkWinningConditions(Move move) {
    final Coordinate finishCoordinate =
        gameState.getLevel().getCopyOfFinishObject().getCoordinate();
    checkForFood(move);
    if (move.getNewCoordinate().equals(finishCoordinate)) {
      gameState = gameState.with(GameStatus.WIN);
    } else if (checkForDeath(move)) {
      gameState = gameState.with(GameStatus.LOSE);
    }
  }

  /** Checks if the player is dead. */
  private boolean checkForDeath(Move move) {
    // Check fall into oblivion.
    final boolean fellDown =
        move.getNewCoordinate().getRow() == gameState.getLevel().getNumRows() - 1;
    // Check exhaustion.
    final boolean exhausted = sideConditions.getEndurance() == 0;
    // Check if landed on fire.
    final List<Coordinate> fireCoordinates = gameState.getFireCoordinates();
    final boolean hitFire =
        (!fireCoordinates.isEmpty() && fireCoordinates.contains(move.getNewCoordinate()));

    return fellDown || exhausted || hitFire;
  }

  /** Checks if the player is dead. */
  private void checkForFood(Move move) {
    final List<Coordinate> foodCoordinates = gameState.getFoodCoordinates();
    if (!foodCoordinates.isEmpty() && foodCoordinates.contains(move.getNewCoordinate())) {
      sideConditions.resetEndurance(ENDURANCE);
    }
  }

  /** Moves the player one step to the right. */
  public void moveBasicRight() {
    if (!gameState.isGameRunning()) {
      System.out.println("Game is not running.");
      return;
    }
    final Player player = gameState.getPlayer();
    final Move move = player.getRightMove();
    gameState.moveObjectInGame(move);
    checkWinningConditions(move);
  }

  /** Moves the player one step to the left. */
  public void moveBasicLeft() {
    if (!gameState.isGameRunning()) {
      System.out.println("Game is not running.");
      return;
    }
    final Player player = gameState.getPlayer();
    final Move move = player.getLeftMove();
    gameState.moveObjectInGame(move);
    checkWinningConditions(move);
  }

  /** Moves the player one step up. */
  public void moveBasicUp() {
    if (!gameState.isGameRunning()) {
      System.out.println("Game is not running.");
      return;
    }
    final Player player = gameState.getPlayer();
    final Move move = player.getJumpMove();
    gameState.moveObjectInGame(move);
    checkWinningConditions(move);
  }

  /** Moves the player one step down. */
  public void moveBasicDown() {
    if (!gameState.isGameRunning()) {
      System.out.println("Game is not running.");
      return;
    }
    final Player player = gameState.getPlayer();
    final Move move = player.getFallMove();
    gameState.moveObjectInGame(move);
    checkWinningConditions(move);
  }

  /** Lets the player fall only a single step. */
  public void moveSingleStepFall() {
    final Player player = gameState.getPlayer();
    final Move move = player.getFallMove();
    gameState.moveObjectInGame(move);
    checkWinningConditions(move);
    gameState.getLevel().updatePossibleMovesOfPlayer();
  }

  /** Lets the player fall continously to the next GROUND-object. */
  @SuppressWarnings("BusyWait")
  public void moveContinousFall() throws InterruptedException {
    if (!gameState.isGameRunning()) {
      System.out.println("Game is not running.");
      return;
    }
    while (!checkForGroundBelowOwl()) {
      final Player player = gameState.getPlayer();
      final Move move = player.getFallMove();
      Platform.runLater(this::moveSingleStepFall);
      Thread.sleep(300);
      if (move.getNewCoordinate() == player.getCoordinate()) {
        break;
      }
    }
  }

  /** Lets the player walk/fly left. */
  public void moveLeft() throws InterruptedException {
    final boolean inFlightMode = sideConditions.isInFlightMode();
    Platform.runLater(this::moveBasicLeft);
    Thread.sleep(300);
    if (inFlightMode) {
      sideConditions.decreaseEndurance();
    } else {
      moveContinousFall();
    }
  }

  /** Lets the player walk/fly right. */
  public void moveRight() throws InterruptedException {
    final boolean inFlightMode = sideConditions.isInFlightMode();
    Platform.runLater(this::moveBasicRight);
    Thread.sleep(300);
    if (inFlightMode) {
      sideConditions.decreaseEndurance();
    } else {
      moveContinousFall();
    }
  }

  /** Lets the player walk/fly up. */
  public void moveUp() throws InterruptedException {
    final boolean inFlightMode = sideConditions.isInFlightMode();
    Platform.runLater(this::moveBasicUp);
    Thread.sleep(300);
    if (inFlightMode) {
      sideConditions.decreaseEndurance();
    } else {
      moveContinousFall();
    }
  }

  /** Lets the player walk/fly down. */
  public void moveDown() {
    if (sideConditions.isInFlightMode()) {
      Platform.runLater(this::moveBasicDown);
      sideConditions.decreaseEndurance();
    }
  }

  /** Lets the player jump right up and fall down or fly diagonaly up right. */
  public void moveUpRight() throws InterruptedException {
    if (sideConditions.isInFlightMode()) {
      Platform.runLater(this::moveBasicRight);
      Thread.sleep(300);
      Platform.runLater(this::moveBasicUp);
      sideConditions.decreaseEndurance();
    } else {
      Platform.runLater(this::moveBasicUp);
      Thread.sleep(300);
      Platform.runLater(this::moveBasicRight);
      Thread.sleep(300);
      Platform.runLater(this::moveBasicRight);
      Thread.sleep(300);
      moveContinousFall();
    }
  }

  /** Lets the player jump left up and fall down or fly diagonaly up left. */
  public void moveUpLeft() throws InterruptedException {
    if (sideConditions.isInFlightMode()) {
      Platform.runLater(this::moveBasicLeft);
      Thread.sleep(300);
      Platform.runLater(this::moveBasicUp);
      sideConditions.decreaseEndurance();
    } else {
      Platform.runLater(this::moveBasicUp);
      Thread.sleep(300);
      Platform.runLater(this::moveBasicLeft);
      Thread.sleep(300);
      Platform.runLater(this::moveBasicLeft);
      Thread.sleep(300);
      moveContinousFall();
    }
  }

  /** Lets the player fly diagonaly down right. */
  public void moveDownRight() throws InterruptedException {
    if (sideConditions.isInFlightMode()) {
      Platform.runLater(this::moveBasicRight);
      Thread.sleep(300);
      Platform.runLater(this::moveBasicDown);
      sideConditions.decreaseEndurance();
    }
  }

  /** Lets the player fly diagonaly down left. */
  public void moveDownLeft() throws InterruptedException {
    if (sideConditions.isInFlightMode()) {
      Platform.runLater(this::moveBasicLeft);
      Thread.sleep(300);
      Platform.runLater(this::moveBasicDown);
      sideConditions.decreaseEndurance();
    }
  }

  /** Returns the Coordinate below the Player. */
  Coordinate getActualCoordinateBelowPlayer() {
    final Player player = gameState.getPlayer();
    final int rowBelowPlayer = gameState.getPlayer().getCoordinate().getRow() - 1;
    return Coordinate.of(rowBelowPlayer, player.getCoordinate().getColumn());
  }

  /** Checks for a GROUND-object below the player. */
  boolean checkForGroundBelowOwl() {
    final ObjectInGame objectBelow =
        gameState.getLevel().getObjectInGameAt(getActualCoordinateBelowPlayer());
    return objectBelow.getType() == ObjectType.GROUND;
  }
}
