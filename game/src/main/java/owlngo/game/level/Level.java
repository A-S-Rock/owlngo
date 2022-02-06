package owlngo.game.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import owlngo.game.OwlnGo;
import owlngo.game.level.objects.LevelObject;
import owlngo.game.level.objects.ObjectInGame;
import owlngo.game.level.objects.ObjectInGame.ObjectType;
import owlngo.game.level.objects.Player;

/** This class represents the level of the {@link OwlnGo} game. Similar to Task 4 ChessBoard. */
public final class Level {
  private final int numRows;
  private final int numCols;
  private final transient Map<Integer, MapProperty<Integer, ObjectInGame>> levelLayout;
  private final List<ObjectInGame> objectsInGame;
  private transient Player playerObject;
  private transient LevelObject startObject;
  private transient LevelObject finishObject;

  /**
   * Constructs a default level with fixed positions of player, start and finish for the given
   * dimensions.
   *
   * @param numRows number of rows
   * @param numCols number of columns
   */
  public Level(int numRows, int numCols) {
    if (numRows <= 3 || numCols <= 3) {
      throw new IllegalArgumentException("Level dimensions cannot be lower or equal to 3.");
    }
    this.numRows = numRows;
    this.numCols = numCols;
    levelLayout = new HashMap<>();
    objectsInGame = new ArrayList<>();
    startObject = LevelObject.createStartObject(Coordinate.of(0, 0));
    playerObject = Player.createPlayer(Coordinate.of(0, 0));
    assert playerObject.getCoordinate().equals(startObject.getCoordinate());

    finishObject = LevelObject.createFinishObject(Coordinate.of(0, 0));

    for (int i = 0; i < numRows; ++i) {
      for (int j = 0; j < numCols; ++j) {
        Coordinate coordinate = Coordinate.of(i, j);
        ObjectInGame object = LevelObject.createAirObject(coordinate);
        objectsInGame.add(object); // player, start and finish are absent
        setObjectInGameAt(object, coordinate);
      }
    }
  }

  private Level(Level sourceLevel) {
    numRows = sourceLevel.getNumRows();
    numCols = sourceLevel.getNumColumns();
    if (numRows <= 3 || numCols <= 3) {
      throw new IllegalArgumentException("Level dimensions cannot be lower or equal to 3.");
    }

    levelLayout = new HashMap<>();

    List<ObjectInGame> clonedObjectsInGame = new ArrayList<>();
    for (int i = 0; i < numRows; ++i) {
      for (int j = 0; j < numCols; ++j) {
        Coordinate coordinate = Coordinate.of(i, j);
        ObjectInGame clonedObjectInGame = sourceLevel.getObjectInGameAt(coordinate).copyOf();
        setObjectInGameAt(clonedObjectInGame, coordinate);
        clonedObjectsInGame.add(clonedObjectInGame);
      }
    }
    objectsInGame = new ArrayList<>(clonedObjectsInGame);

    playerObject = sourceLevel.getCopyOfPlayer();
    startObject = sourceLevel.getCopyOfStartObject();
    finishObject = sourceLevel.getCopyOfFinishObject();
  }

  /**
   * Creates a demo level with the given dimensions. At the lowest level, all but those at column
   * multiples of 4 are ground elements. Start is set one row over the ground on the far left side,
   * the player is over it. Finish is set on the same level as start, but on the far right side.
   *
   * @param numRows row count the level will have
   * @param numCols column count the level will have
   * @return the described demo level
   */
  public static Level createDemoLevel(int numRows, int numCols) {
    final Level level = new Level(numRows, numCols);

    final int maxRow = numRows - 1;
    final int maxCol = numCols - 1;

    for (int currentCol = 0; currentCol <= maxCol; currentCol++) {
      if (currentCol != 0 && currentCol % 4 == 0) { // hole in the ground at column multiples of 4
        continue;
      }
      final Coordinate coordinate = Coordinate.of(maxRow, currentCol);
      final ObjectInGame object = LevelObject.createGroundObject(coordinate);
      level.replaceObjectInGameWith(object, object.getCoordinate());
    }

    final LevelObject newStartObject = LevelObject.createStartObject(Coordinate.of(maxRow - 1, 0));
    level.replaceObjectInGameWith(newStartObject, newStartObject.getCoordinate());

    final int startObjectRow = newStartObject.getCoordinate().getRow();
    final int startObjectColumn = newStartObject.getCoordinate().getColumn();

    final Coordinate playerCoordinate = Coordinate.of(startObjectRow, startObjectColumn + 1);
    final Player newPlayerObject = Player.createPlayer(playerCoordinate);
    level.replaceObjectInGameWith(newPlayerObject, newPlayerObject.getCoordinate());

    final LevelObject newFinishObject =
        LevelObject.createFinishObject(Coordinate.of(maxRow - 1, maxCol));
    level.replaceObjectInGameWith(newFinishObject, newFinishObject.getCoordinate());

    return level;
  }

  /**
   * Creates a new level with a new player set at the given coordinate.
   *
   * @param coordinate position of the new player
   * @return an immutable copy of the level with the new player
   */
  public Level withNewPlayerAt(Coordinate coordinate) {
    replaceObjectInGameWith(Player.createPlayer(coordinate), coordinate);
    return new Level(this);
  }

  /**
   * Creates a new level with the start set at the given coordinate.
   *
   * @param coordinate position of start
   * @return an immutable copy of the level with start at the new location
   */
  public Level withStartAt(Coordinate coordinate) {
    replaceObjectInGameWith(LevelObject.createStartObject(coordinate), coordinate);
    return new Level(this);
  }

  /**
   * Creates a new level with the finish set at the given coordinate.
   *
   * @param coordinate position of finish
   * @return an immutable copy of the level with finish at the new location
   */
  public Level withFinishAt(Coordinate coordinate) {
    replaceObjectInGameWith(LevelObject.createFinishObject(coordinate), coordinate);
    return new Level(this);
  }

  /**
   * Creates a new level with an air object set at the given coordinate.
   *
   * @param coordinate position of air
   * @return an immutable copy of the level with air at the new location
   */
  public Level withAirAt(Coordinate coordinate) {
    replaceObjectInGameWith(LevelObject.createAirObject(coordinate), coordinate);
    return new Level(this);
  }

  /**
   * Creates a new level with a ground object set at the given coordinate.
   *
   * @param coordinate position of ground
   * @return an immutable copy of the level with ground at the new location
   */
  public Level withGroundAt(Coordinate coordinate) {
    replaceObjectInGameWith(LevelObject.createGroundObject(coordinate), coordinate);
    return new Level(this);
  }

  /**
   * Creates a new level with a food object set at the given coordinate.
   *
   * @param coordinate position of food
   * @return an immutable copy of the level with food at the new location
   */
  public Level withFoodAt(Coordinate coordinate) {
    replaceObjectInGameWith(LevelObject.createFoodObject(coordinate), coordinate);
    return new Level(this);
  }

  /**
   * Creates a new level with a fire object set at the given coordinate.
   *
   * @param coordinate position of fire
   * @return an immutable copy of the level with fire at the new location
   */
  public Level withFireAt(Coordinate coordinate) {
    replaceObjectInGameWith(LevelObject.createFireObject(coordinate), coordinate);
    return new Level(this);
  }

  /** Moves the object to the new position. */
  public void moveObjectInGame(ObjectInGame object, Coordinate newCoordinate) {
    final Coordinate oldCoordinate = object.getCoordinate();

    replaceObjectInGameWith(LevelObject.createAirObject(oldCoordinate), oldCoordinate);
    replaceObjectInGameWith(startObject, startObject.getCoordinate()); // reset start
    replaceObjectInGameWith(finishObject, finishObject.getCoordinate()); // reset finish
    replaceObjectInGameWith(object, newCoordinate);
  }

  private void replaceObjectInGameWith(ObjectInGame objectInGame, Coordinate coordinate) {
    // Replace object at its location with background
    removeObjectInGame(objectInGame);

    // Replace dummy air at given coordinate with new object.
    objectsInGame.removeIf(object -> object.getCoordinate().equals(coordinate));

    ObjectInGame newObject = objectInGame.withNewPosition(coordinate);

    if (newObject.getType() == ObjectType.PLAYER) {
      playerObject = (Player) newObject.copyOf();
    } else if (newObject.getType() == ObjectType.START) {
      startObject = (LevelObject) newObject.copyOf();
    } else if (newObject.getType() == ObjectType.FINISH) {
      finishObject = (LevelObject) newObject.copyOf();
    }

    objectsInGame.add(newObject);
    setObjectInGameAt(newObject, coordinate);
  }

  /** Removes the object from the level. */
  private void removeObjectInGame(ObjectInGame objectInGame) {
    assert !objectInGame.isNone();
    Coordinate coordinate = objectInGame.getCoordinate();
    setObjectInGameAt(LevelObject.createAirObject(coordinate), coordinate);
    boolean wasRemoved = objectsInGame.remove(objectInGame);
    assert wasRemoved;
  }

  /** Returns an immutable copy of the level. */
  public Level copyOf() {
    return new Level(this);
  }

  /** Gets the number of rows of the board. */
  public int getNumRows() {
    return numRows;
  }

  /** Gets the number of columns of the board. */
  public int getNumColumns() {
    return numCols;
  }

  /** Returns an immutable copy of the player in the game. */
  public Player getCopyOfPlayer() {
    return (Player) playerObject.copyOf();
  }

  /** Returns an immutable copy of the start in the game. */
  public LevelObject getCopyOfStartObject() {
    return (LevelObject) startObject.copyOf();
  }

  /** Returns an immutable copy of the finish in the game. */
  public LevelObject getCopyOfFinishObject() {
    return (LevelObject) finishObject.copyOf();
  }

  /** Returns an immutable list of the added objects ingame. */
  public List<ObjectInGame> getListOfObjectsInGame() {
    List<ObjectInGame> clonedList = new ArrayList<>();
    for (ObjectInGame object : objectsInGame) {
      clonedList.add(object.copyOf());
    }
    return List.copyOf(clonedList);
  }

  /** Gets the piece at the given position. */
  public ObjectInGame getObjectInGameAt(Coordinate coordinate) {
    if (!levelLayout.containsKey(coordinate.getRow())
        || !levelLayout.containsKey(coordinate.getColumn())) {
      throw new IndexOutOfBoundsException("Coordinate does not exist in board map: " + coordinate);
    }
    return levelLayout.get(coordinate.getRow()).get(coordinate.getColumn());
  }

  /** Returns the layout property of the level. */
  @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP")
  public Map<Integer, MapProperty<Integer, ObjectInGame>> propertyLevelLayout() {
    return levelLayout;
  }

  private void setObjectInGameAt(ObjectInGame objectInGame, Coordinate coordinate) {
    assert objectInGame.isNone();
    final int row = coordinate.getRow();
    levelLayout.putIfAbsent(row, new SimpleMapProperty<>(FXCollections.observableHashMap()));
    levelLayout.get(row).put(coordinate.getColumn(), objectInGame);
  }

  /** Returns whether there is an object placed at the given position. */
  public boolean hasObjectInGameAt(Coordinate coordinate) {
    return !getObjectInGameAt(coordinate).isNone();
  }

  /** Returns whether the given position is with the board's bounds. */
  public boolean isPositionWithinBounds(Coordinate coordinate) {
    final int row = coordinate.getRow();
    final int column = coordinate.getColumn();
    return (row >= 0) && (row < getNumRows()) && (column >= 0) && (column < getNumColumns());
  }

  /** Update the possible moves of the chess pieces with the given color. */
  public void updatePossibleMovesOfPlayer() {
    playerObject.updatePossibleMoves(this);
  }
}
