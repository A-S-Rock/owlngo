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
import owlngo.game.level.objects.Player;

/** This class represents the level of the {@link OwlnGo} game. Similar to Task 4 ChessBoard. */
public final class Level {
  private final int numRows;
  private final int numCols;
  private final Map<Integer, MapProperty<Integer, ObjectInGame>> levelLayout;
  private final List<ObjectInGame> objectsInGame;
  private final Player player;
  private final LevelObject startObject;
  private final LevelObject finishObject;

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
    player = Player.createPlayer(Coordinate.of(0, 1));
    startObject = LevelObject.createStartObject(Coordinate.of(0, 0));
    finishObject = LevelObject.createFinishObject(Coordinate.of(0, numCols - 1));

    for (int i = 0; i < numRows; ++i) {
      for (int j = 0; j < numCols; ++j) {
        Coordinate coordinate = Coordinate.of(i, j);
        ObjectInGame object = LevelObject.createNoneObject(coordinate);
        objectsInGame.add(object);
        setObjectsInGameAt(object, coordinate);
      }
    }
    objectsInGame.add(player);
    setObjectsInGameAt(player, player.getCoordinate());
    objectsInGame.add(startObject);
    setObjectsInGameAt(startObject, startObject.getCoordinate());
    objectsInGame.add(finishObject);
    setObjectsInGameAt(finishObject, finishObject.getCoordinate());
  }

  // TODO: Create factory methods for player, start and finish

  private Level(Level sourceLevel) {
    numRows = sourceLevel.getNumRows();
    numCols = sourceLevel.getNumColumns();
    levelLayout = new HashMap<>();

    List<ObjectInGame> clonedObjectsInGame = new ArrayList<>();
    for (int i = 0; i < numRows; ++i) {
      for (int j = 0; j < numCols; ++j) {
        Coordinate coordinate = Coordinate.of(i, j);
        ObjectInGame clonedObjectInGame = sourceLevel.getObjectInGameAt(coordinate).copyOf();
        setObjectsInGameAt(clonedObjectInGame, coordinate);
        clonedObjectsInGame.add(clonedObjectInGame);
      }
    }
    objectsInGame = List.copyOf(clonedObjectsInGame);

    player = sourceLevel.getCopyOfPlayer();
    startObject = sourceLevel.getCopyOfStartObject();
    finishObject = sourceLevel.getCopyOfFinishObject();
  }

  /** Returns an immutable copy of the player in the game. */
  public Player getCopyOfPlayer() {
    return player.copyOf();
  }

  /** Returns an immutable copy of the start in the game. */
  public LevelObject getCopyOfStartObject() {
    return (LevelObject) startObject.copyOf();
  }

  /** Returns an immutable copy of the finish in the game. */
  public LevelObject getCopyOfFinishObject() {
    return (LevelObject) finishObject.copyOf();
  }

  /** Returns the layout property of the level. */
  public Map<Integer, MapProperty<Integer, ObjectInGame>> propertyLevelLayout() {
    return levelLayout;
  }

  /** Returns an immutable copy of the level. */
  public Level copyOf() {
    return new Level(this);
  }

  /** Returns an immutable list of the added objects ingame. */
  public List<ObjectInGame> getListOfObjectsInGame() {
    List<ObjectInGame> clonedList = new ArrayList<>();
    for (ObjectInGame object : objectsInGame) {
      clonedList.add(object.copyOf());
    }
    return List.copyOf(clonedList);
  }

  private void setObjectsInGameAt(ObjectInGame objectInGame, Coordinate coordinate) {
    assert objectInGame.isNone() || (objectInGame.getCoordinate().equals(coordinate));
    final int row = coordinate.getRow();
    levelLayout.putIfAbsent(row, new SimpleMapProperty<>(FXCollections.observableHashMap()));
    levelLayout.get(row).put(coordinate.getColumn(), objectInGame);
  }

  /** Returns whether there is an object placed at the given position. */
  public boolean hasObjectInGameAt(Coordinate coordinate) {
    return !getObjectInGameAt(coordinate).isNone();
  }

  /** Gets the piece at the given position. */
  public ObjectInGame getObjectInGameAt(Coordinate coordinate) {
    if (!levelLayout.containsKey(coordinate.getRow())
        || !levelLayout.containsKey(coordinate.getColumn())) {
      throw new IndexOutOfBoundsException("Coordinate does not exist in board map: " + coordinate);
    }
    return levelLayout.get(coordinate.getRow()).get(coordinate.getColumn());
  }

  /** Returns whether the given position is with the board's bounds. */
  public boolean isPositionWithinBounds(Coordinate coordinate) {
    final int row = coordinate.getRow();
    final int column = coordinate.getColumn();
    return (row >= 0) && (row < getNumRows()) && (column >= 0) && (column < getNumColumns());
  }

  /** Gets the number of rows of the board. */
  public int getNumRows() {
    return numRows;
  }

  /** Gets the number of columns of the board. */
  public int getNumColumns() {
    return numCols;
  }


}
