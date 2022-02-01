package owlngo.game;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import owlngo.game.level.Coordinate;
import owlngo.game.level.Level;
import owlngo.game.level.Move;
import owlngo.game.level.objects.ObjectInGame;
import owlngo.game.level.objects.ObjectInGame.ObjectType;
import owlngo.game.level.objects.Player;

/**
 * The class stores the current state of the {@link OwlnGo} game, including the game status and the
 * level. Similar to Task 4, Bauernschach.
 */
public class GameState {

  /**
   * Represents the status of the Owlngo game.
   *
   * <ul>
   *   <li>{@code ONGOING}: if the game is still running.
   *   <li>{@code WIN}: if the player won the game.
   *   <li>{@code LOSE}: if the player lost the game.
   * </ul>
   */
  public enum GameStatus {
    ONGOING,
    WIN,
    LOSE
  }

  private final Level level;
  private ObjectProperty<GameStatus> gameStatus = new SimpleObjectProperty<>();
  private ObjectProperty<Player> player = new SimpleObjectProperty<>();
  private final List<Coordinate> fireCoordinates = new ArrayList<>();

  /**
   * Contructs a GameState instance with a level of the given diemensions. Initially, the game
   * status is set to ONGOING.
   *
   * @param numRows the number of rows of the level
   * @param numCols the number of rows of the level
   */
  GameState(int numRows, int numCols) {
    if (numRows <= 3 || numCols <= 3) {
      throw new IllegalArgumentException("Level dimensions cannot be lower or equal to 3.");
    }
    level = Level.createDemoLevel(numRows, numCols);
    gameStatus.set(GameStatus.ONGOING);
    level.updatePossibleMovesOfPlayer();
    player.set(level.getCopyOfPlayer());

    for (ObjectInGame object : level.getListOfObjectsInGame()) {
      if (object.getType() == ObjectType.FIRE) {
        fireCoordinates.add(object.getCoordinate());
      }
    }
  }

  GameState(Level level) {
    if (level.getNumRows() <= 3 || level.getNumColumns() <= 3) {
      throw new IllegalArgumentException("Level dimensions cannot be lower or equal to 3.");
    }
    this.level = level;
    gameStatus.set(GameStatus.ONGOING);
    level.updatePossibleMovesOfPlayer();
    player.set(level.getCopyOfPlayer());

    for (ObjectInGame object : level.getListOfObjectsInGame()) {
      if (object.getType() == ObjectType.FIRE) {
        fireCoordinates.add(object.getCoordinate());
      }
    }
  }

  private GameState(
      Level level, ObjectProperty<GameStatus> gameStatus, ObjectProperty<Player> player) {
    this.level = level;
    this.gameStatus = gameStatus;
    this.player = player;

    for (ObjectInGame object : level.getListOfObjectsInGame()) {
      if (object.getType() == ObjectType.FIRE) {
        fireCoordinates.add(object.getCoordinate());
      }
    }
  }

  /**
   * Create a new instance of GameState where GameStatus is as given, other members are as in this
   * instance.
   */
  GameState with(GameStatus status) {
    gameStatus.set(status);
    return new GameState(level, gameStatus, player);
  }

  /** Returns whether the game is still running (game status = ONGOING). */
  public boolean isGameRunning() {
    return gameStatus.get() == GameStatus.ONGOING;
  }

  /** Returns a clone of the current level. */
  public final Level getLevel() {
    return level.copyOf();
  }

  public ObjectProperty<GameStatus> propertyStatus() {
    return gameStatus;
  }

  /** Gets the current game status. */
  public GameStatus getStatus() {
    return gameStatus.get();
  }

  public ObjectProperty<Player> propertyPlayer() {
    return player;
  }

  /** Returns the player. */
  public Player getPlayer() {
    return player.get();
  }

  /** Retruns the fire coordinates. */
  public List<Coordinate> getFireCoordinates() {
    return new ArrayList<>(fireCoordinates);
  }

  /** Applies the given move. */
  void moveObjectInGame(Move move) {
    level.moveObjectInGame(player.get(), move.getNewCoordinate());
    level.updatePossibleMovesOfPlayer();
    player.set(level.getCopyOfPlayer());
  }
}
