package owlngo.communication.utils;

import java.util.ArrayList;
import java.util.List;
import owlngo.game.level.Level;
import owlngo.game.level.objects.ObjectInGame;

/**
 * Reduced {@link Level} object specialized for the network and savefile JSON protocol. Only holds
 * relevant information of the {@link Level} object because some of it can be self-generated.
 */
public final class LevelJson {
  private final int numRows;
  private final int numCols;
  private final List<ObjectInGame> objectsInGame;

  public LevelJson(Level level) {
    numRows = level.getNumRows();
    numCols = level.getNumColumns();
    objectsInGame = new ArrayList<>(level.getListOfObjectsInGame());
  }

  public Level createLevel() {
    Level level = new Level(numRows, numCols);
    for (ObjectInGame object : objectsInGame) {
      level.replaceObjectInGameWith(object, object.getCoordinate());
    }
    return level;
  }
}
