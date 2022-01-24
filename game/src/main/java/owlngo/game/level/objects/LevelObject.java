package owlngo.game.level.objects;

import java.util.Objects;
import owlngo.game.level.Coordinate;

/** Represents a general map layout object of various type. */
public final class LevelObject implements ObjectInGame {

  /** A global constant representing an empty dummy object. */
  public static final LevelObject NONE = new LevelObject(ObjectType.NONE, Coordinate.of(-1, -1));

  @SuppressWarnings("unused")
  private static final String objectJsonType = "levelObject";

  private final ObjectType objectType;
  private final Coordinate coordinate;

  private LevelObject(ObjectType objectType, Coordinate coordinate) {
    this.objectType = objectType;
    this.coordinate = coordinate;
  }

  /**
   * Creates a new start object for the game at the given coordinate.
   *
   * @param coord coordinate to put the start object to
   * @return the start object
   */
  public static LevelObject createStartObject(Coordinate coord) {
    return new LevelObject(ObjectType.START, coord);
  }

  /**
   * Creates a new finish object for the game at the given coordinate.
   *
   * @param coord coordinate to put the finish object to
   * @return the finish object
   */
  public static LevelObject createFinishObject(Coordinate coord) {
    return new LevelObject(ObjectType.FINISH, coord);
  }

  /**
   * Creates a new ground object for the game at the given coordinate.
   *
   * @param coord coordinate to put the ground object to
   * @return the ground object
   */
  public static LevelObject createGroundObject(Coordinate coord) {
    return new LevelObject(ObjectType.GROUND, coord);
  }

  /**
   * Creates a new air object for the game at the given coordinate.
   *
   * @param coord coordinate to put the air object to
   * @return the air object
   */
  public static LevelObject createAirObject(Coordinate coord) {
    return new LevelObject(ObjectType.AIR, coord);
  }

  @Override
  public ObjectInGame withNewPosition(Coordinate coordinate) {
    assert isValid();
    return new LevelObject(objectType, coordinate);
  }

  @Override
  public ObjectInGame copyOf() {
    return new LevelObject(objectType, coordinate);
  }

  @Override
  public ObjectType getType() {
    return objectType;
  }

  @Override
  public Coordinate getCoordinate() {
    return coordinate;
  }

  @Override
  public boolean isValid() {
    return objectType != null && coordinate.getRow() >= 0 && coordinate.getColumn() >= 0;
  }

  @Override
  public boolean isNone() {
    return objectType == ObjectType.NONE;
  }

  // Had to add equals() and hashCode() because of object comparison in lists.

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LevelObject that = (LevelObject) o;
    return objectType == that.objectType && coordinate.equals(that.coordinate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(objectType, coordinate);
  }

  @Override
  public String toString() {
    return "LevelObject{" + "objectType=" + objectType + ", coordinate=" + coordinate + "}";
  }
}
