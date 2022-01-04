package owlngo.game.level.objects;

import owlngo.game.level.Coordinate;

/** Represents a general map layout object of various type. */
public class LevelObject implements ObjectInGame {

  private final ObjectType objectType;
  private final Coordinate coordinate;

  /**
   * Creates a new start object for the game at the given coordinate.
   *
   * @param coord coordinate to put the start object to
   * @return the start object
   */
  public static LevelObject createNoneObject(Coordinate coord) {
    return new LevelObject(ObjectType.NONE, coord);
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

  private LevelObject(ObjectType objectType, Coordinate coordinate) {
    this.objectType = objectType;
    this.coordinate = coordinate;
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

  @Override
  public String toString() {
    return "LevelObject{" + "objectType=" + objectType + ", coordinate=" + coordinate + "}";
  }
}
