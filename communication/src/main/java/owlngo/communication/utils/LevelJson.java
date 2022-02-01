package owlngo.communication.utils;

import java.util.ArrayList;
import java.util.List;
import owlngo.game.level.Coordinate;
import owlngo.game.level.Level;
import owlngo.game.level.objects.ObjectInGame;
import owlngo.game.level.objects.ObjectInGame.ObjectType;

/**
 * Reduced {@link Level} object specialized for the network and savefile JSON protocol. Only holds
 * relevant information of the {@link Level} object because some of it can be self-generated.
 */
public final class LevelJson {
  private final int numRows;
  private final int numCols;
  private final List<ObjectInGame> objectsInGame;

  /**
   * Creates a level JSON class (not the JSON string) from a given level layout.
   *
   * @param level level to reduce to a more compact data class
   */
  public LevelJson(Level level) {
    numRows = level.getNumRows();
    numCols = level.getNumColumns();
    objectsInGame = new ArrayList<>(level.getListOfObjectsInGame());
  }

  /**
   * Creates the {@link Level} object from the stored data.
   *
   * @return the full {@link Level} object
   */
  public Level createLevel() {
    Level level = new Level(numRows, numCols);
    for (ObjectInGame object : objectsInGame) {
      final ObjectType type = object.getType();
      final Coordinate coordinate = object.getCoordinate();

      if (type == ObjectType.START) {
        level = level.withStartAt(coordinate);
      } else if (type == ObjectType.FINISH) {
        level = level.withFinishAt(coordinate);
      } else if (type == ObjectType.GROUND) {
        level = level.withGroundAt(coordinate);
      } else if (type == ObjectType.FOOD) {
        level = level.withFoodAt(coordinate);
      } else if (type == ObjectType.FIRE) {
        level = level.withFireAt(coordinate);
      }

      if (type == ObjectType.PLAYER) {
        level = level.withNewPlayerAt(coordinate);
      }
    }
    return level;
  }
}
