package owlngo.game.level.objects;

import owlngo.game.level.Coordinate;
import owlngo.game.level.Level;

/** Represents the general objects in {@link Level}. */
public interface ObjectInGame {

  enum ObjectType {
    NONE,
    PLAYER,
    START,
    FINISH,
    GROUND
  }

  /** Creates a new object ingame at the given position. */
  ObjectInGame withNewPosition(Coordinate coordinate);

  /** Returns an immutable instance of an ingame object. */
  ObjectInGame copyOf();

  /** Returns the object type of the ingame object. */
  ObjectType getType();

  /** Returns the current position of the ingame object. */
  Coordinate getCoordinate();

  /**
   * Checks if this ingame object has valid attributes.
   *
   * @return <code>true</code> if object attributes meet the requirements, otherwise <code>false
   *     </code>
   */
  boolean isValid();

  /**
   * Checks if this ingame object is equal to the global dummy object <code>ObjectType.NONE</code>.
   *
   * @return <code>true</code> if object attributes meet the requirements, otherwise <code>false
   *     </code>
   */
  boolean isNone();
}
