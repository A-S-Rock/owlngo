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

  private static final int ANIMATION_UPDATE_TIME = 100;

  private static final int DEFAULT_NUM_ROWS = 30;
  private static final int DEFAULT_NUM_COLS = 30;
  private static final int ENDURANCE = 10;
  private static final boolean IN_FLIGHT_MODE = false;
  private static final boolean ACTIVE_MOVEMENT = false;
  private final SideConditions sideConditions;
  private GameState gameState;

  /**
   * Constructs an Owlngo game instance with the prespecified level dimensions and prespecified
   * sideConditions.
   */
  public OwlnGo() {
    gameState = new GameState(DEFAULT_NUM_ROWS, DEFAULT_NUM_COLS);
    sideConditions = new SideConditions(ENDURANCE, IN_FLIGHT_MODE, ACTIVE_MOVEMENT);
  }

  /**
   * Constructs an Owlngo game instance with the given level dimensions and prespecified
   * sideConditions.
   */
  public OwlnGo(int numRows, int numCols) {
    gameState = new GameState(numRows, numCols);
    sideConditions = new SideConditions(ENDURANCE, IN_FLIGHT_MODE, ACTIVE_MOVEMENT);
  }

  /** Constructs an Owlngo game instance with a given level and prespecified sideConditions. */
  public OwlnGo(Level level) {
    gameState = new GameState(level);
    sideConditions = new SideConditions(ENDURANCE, IN_FLIGHT_MODE, ACTIVE_MOVEMENT);
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

  public final int getMaxEndurance() {
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

  // Basic movement

  /** Moves the player one step to the right. */
  private void moveBasicRight() {
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
  private void moveBasicLeft() {
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
  private void moveBasicUp() {
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
  private void moveBasicDown() {
    if (!gameState.isGameRunning()) {
      System.out.println("Game is not running.");
      return;
    }
    final Player player = gameState.getPlayer();
    final Move move = player.getFallMove();
    gameState.moveObjectInGame(move);
    checkWinningConditions(move);
  }

  // Falling

  /** Lets the player fall only a single step. */
  private void moveSingleStepFall() {
    final Player player = gameState.getPlayer();
    final Move move = player.getFallMove();
    gameState.moveObjectInGame(move);
    checkWinningConditions(move);
    gameState.getLevel().updatePossibleMovesOfPlayer();
  }

  /** Lets the player fall continously to the next GROUND-object. */
  @SuppressWarnings("BusyWait")
  public void moveContinousFall() throws InterruptedException {
    while (!checkForGroundBelowOwl()) {
      if (!gameState.isGameRunning()) {
        System.out.println("Game is not running.");
        return;
      }
      if (!sideConditions.isInFlightMode()) {
        final Player player = gameState.getPlayer();
        final Move move = player.getFallMove();
        Platform.runLater(this::moveSingleStepFall);
        Thread.sleep(ANIMATION_UPDATE_TIME + 50);
        if (move.getNewCoordinate() == player.getCoordinate()) {
          break;
        }
      }
    }
  }

  // Advanced movement for GUI purposes.

  /** Lets the player walk/fly left. */
  public void moveLeft() throws InterruptedException {
    getSideConditions().setActiveMovement();
    final boolean inFlightMode = sideConditions.isInFlightMode();
    Platform.runLater(this::moveBasicLeft);
    if (inFlightMode) {
      sideConditions.decreaseEndurance();
    } else {
      Thread.sleep(ANIMATION_UPDATE_TIME);
      moveContinousFall();
    }
    getSideConditions().setActiveMovement();
  }

  /** Lets the player walk/fly right. */
  public void moveRight() throws InterruptedException {
    getSideConditions().setActiveMovement();
    final boolean inFlightMode = sideConditions.isInFlightMode();
    Platform.runLater(this::moveBasicRight);
    if (inFlightMode) {
      sideConditions.decreaseEndurance();
    } else {
      Thread.sleep(ANIMATION_UPDATE_TIME);
      moveContinousFall();
    }
    getSideConditions().setActiveMovement();
  }

  /** Lets the player walk/fly up. */
  public void moveUp() throws InterruptedException {
    getSideConditions().setActiveMovement();
    final boolean inFlightMode = sideConditions.isInFlightMode();
    Platform.runLater(this::moveBasicUp);
    if (inFlightMode) {
      sideConditions.decreaseEndurance();
    } else {
      Thread.sleep(ANIMATION_UPDATE_TIME);
      moveContinousFall();
    }
    getSideConditions().setActiveMovement();
  }

  /** Lets the player walk/fly down. */
  public void moveDown() throws InterruptedException {
    getSideConditions().setActiveMovement();
    if (sideConditions.isInFlightMode()) {
      Platform.runLater(this::moveBasicDown);
      sideConditions.decreaseEndurance();
      Thread.sleep(ANIMATION_UPDATE_TIME);
    }
    getSideConditions().setActiveMovement();
  }

  // Diagonal movement.

  /** Lets the player jump right up and fall down or fly diagonaly up right. */
  public void moveUpRight() throws InterruptedException {
    getSideConditions().setActiveMovement();
    if (sideConditions.isInFlightMode()) {
      Platform.runLater(this::moveBasicRight);
      Thread.sleep(ANIMATION_UPDATE_TIME);
      Platform.runLater(this::moveBasicUp);
      sideConditions.decreaseEndurance();
    } else {
      Platform.runLater(this::moveBasicUp);
      Thread.sleep(ANIMATION_UPDATE_TIME);
      Platform.runLater(this::moveBasicRight);
      Thread.sleep(ANIMATION_UPDATE_TIME);
      Platform.runLater(this::moveBasicRight);
      Thread.sleep(ANIMATION_UPDATE_TIME);
      moveContinousFall();
    }
    getSideConditions().setActiveMovement();
  }

  /** Lets the player jump left up and fall down or fly diagonaly up left. */
  public void moveUpLeft() throws InterruptedException {
    getSideConditions().setActiveMovement();
    if (sideConditions.isInFlightMode()) {
      Platform.runLater(this::moveBasicLeft);
      Thread.sleep(ANIMATION_UPDATE_TIME);
      Platform.runLater(this::moveBasicUp);
      sideConditions.decreaseEndurance();
    } else {
      Platform.runLater(this::moveBasicUp);
      Thread.sleep(ANIMATION_UPDATE_TIME);
      Platform.runLater(this::moveBasicLeft);
      Thread.sleep(ANIMATION_UPDATE_TIME);
      Platform.runLater(this::moveBasicLeft);
      Thread.sleep(ANIMATION_UPDATE_TIME);
      moveContinousFall();
    }
    getSideConditions().setActiveMovement();
  }

  /** Lets the player fly diagonaly down right. */
  public void moveDownRight() throws InterruptedException {
    getSideConditions().setActiveMovement();
    if (sideConditions.isInFlightMode()) {
      Platform.runLater(this::moveBasicRight);
      Thread.sleep(ANIMATION_UPDATE_TIME);
      Platform.runLater(this::moveBasicDown);
      sideConditions.decreaseEndurance();
    }
    getSideConditions().setActiveMovement();
  }

  /** Lets the player fly diagonaly down left. */
  public void moveDownLeft() throws InterruptedException {
    getSideConditions().setActiveMovement();
    if (sideConditions.isInFlightMode()) {
      Platform.runLater(this::moveBasicLeft);
      Thread.sleep(ANIMATION_UPDATE_TIME);
      Platform.runLater(this::moveBasicDown);
      sideConditions.decreaseEndurance();
    }
    getSideConditions().setActiveMovement();
  }

  // Checking methods.

  /** Returns the Coordinate below the Player. */
  private Coordinate getActualCoordinateBelowPlayer() {
    final Player player = gameState.getPlayer();

    int rowBelowPlayer = gameState.getPlayer().getCoordinate().getRow() + 1;
    if (rowBelowPlayer == gameState.getLevel().getNumRows()) {
      return Coordinate.of(rowBelowPlayer - 1, player.getCoordinate().getColumn());
    }
    return Coordinate.of(rowBelowPlayer, player.getCoordinate().getColumn());
  }

  /** Checks for a GROUND-object below the player. */
  private boolean checkForGroundBelowOwl() {
    final ObjectInGame objectBelow =
        gameState.getLevel().getObjectInGameAt(getActualCoordinateBelowPlayer());
    return objectBelow.getType() == ObjectType.GROUND;
  }

  /** Lets the player give up. */
  public void giveUp() {
    gameState = gameState.with(GameStatus.GIVE_UP);
  }
}
