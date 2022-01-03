package owlngo.game.level;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import owlngo.game.OwlnGo;
import owlngo.game.level.objects.LevelObject;
import owlngo.game.level.objects.ObjectInGame;
import owlngo.game.level.objects.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** This class represents the level of the {@link OwlnGo} game. Similar to Task 4 ChessBoard. */
public final class Level {
  private final int numRows;
  private final int numCols;
  private final Map<Integer, MapProperty<Integer, ObjectInGame>> levelLayout;
  private final List<ObjectInGame> objectsInGame;
  private final Player player;
  private final LevelObject startObject;
  private final LevelObject finishObject;

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

  private void setObjectsInGameAt(ObjectInGame objectInGame, Coordinate coordinate) {
    assert objectInGame.isNone() || (objectInGame.getCoordinate().equals(coordinate));
    final int row = coordinate.getRow();
    levelLayout.putIfAbsent(row, new SimpleMapProperty<>(FXCollections.observableHashMap()));
    levelLayout.get(row).put(coordinate.getColumn(), objectInGame);
  }




}
