package owlngo.game.level.objects;

import java.util.ArrayList;
import java.util.List;
import owlngo.game.level.Coordinate;
import owlngo.game.level.Level;
import owlngo.game.level.Move;
import owlngo.game.level.Move.MoveType;

/**
 * The player representation in the game. These objects need the ability to move on the level. This
 * class has been adjusted from the Task 4 template.
 */
public final class Player implements ObjectInGame {

  private final ObjectType objectType;
  private final Coordinate coordinate;
  private final List<Move> possibleMoves;

  private Player(ObjectType objectType, Coordinate coordinate, List<Move> possibleMoves) {
    this.objectType = objectType;
    this.coordinate = coordinate;
    this.possibleMoves = possibleMoves;
  }

  /**
   * Creates a fresh player object at the given position.
   *
   * @param coord the coordinate to put the player at
   * @return the player object
   */
  public static Player createPlayer(Coordinate coord) {
    return new Player(ObjectType.PLAYER, coord, new ArrayList<>());
  }

  public Move getFallMove() {
    List<Move> fallMoves =
        getPossibleMoves().stream().filter(move -> move.getMoveType() == MoveType.FALL).toList();
    if (fallMoves.size() != 1) {
      return Move.newFallMove(coordinate);
    } else {
      return fallMoves.get(0);
    }
  }

  public Move getLeftMove() {
    List<Move> leftMoves =
        getPossibleMoves().stream().filter(move -> move.getMoveType() == MoveType.LEFT).toList();
    if (leftMoves.size() != 1) {
      return Move.newLeftMove(coordinate);
    } else {
      return leftMoves.get(0);
    }
  }

  public Move getRightMove() {
    List<Move> rightMoves =
        getPossibleMoves().stream().filter(move -> move.getMoveType() == MoveType.RIGHT).toList();
    if (rightMoves.size() != 1) {
      return Move.newRightMove(coordinate);
    } else {
      return rightMoves.get(0);
    }
  }

  public Move getJumpMove() {
    List<Move> jumpMoves =
        getPossibleMoves().stream().filter(move -> move.getMoveType() == MoveType.JUMP).toList();
    if (jumpMoves.size() != 1) {
      return Move.newJumpMove(coordinate);
    } else {
      return jumpMoves.get(0);
    }
  }

  public void updatePossibleMoves(Level level) {
    assert isValid();
    possibleMoves.clear();
    checkFallMove(level);
    checkRightMove(level);
    checkLeftMove(level);
    checkJumpMove(level);
  }

  private void checkFallMove(Level level) {
    final int column = coordinate.getColumn();
    final int newRow = coordinate.getRow() - 1;
    Coordinate newCoordinate = Coordinate.of(newRow, column);
    // check if the new position is within bounds and not occupied
    if (level.isPositionWithinBounds(newCoordinate)
        && level.hasObjectInGameAt(newCoordinate)
        && level.getObjectInGameAt(newCoordinate).getType() != ObjectType.GROUND) {
      possibleMoves.add(Move.newFallMove(newCoordinate));
    }
  }

  private void checkRightMove(Level level) {
    final int row = coordinate.getRow();
    final int newColumn = coordinate.getColumn() + 1;
    Coordinate newCoordinate = Coordinate.of(row, newColumn);
    // check if the new position is within bounds and not occupied
    if (level.isPositionWithinBounds(newCoordinate)
        && level.hasObjectInGameAt(newCoordinate)
        && level.getObjectInGameAt(newCoordinate).getType() != ObjectType.GROUND) {
      possibleMoves.add(Move.newRightMove(newCoordinate));
    }
  }

  private void checkLeftMove(Level level) {
    final int row = coordinate.getRow();
    final int newColumn = coordinate.getColumn() - 1;
    Coordinate newCoordinate = Coordinate.of(row, newColumn);
    // check if the new position is within bounds and not occupied
    if (level.isPositionWithinBounds(newCoordinate)
        && level.hasObjectInGameAt(newCoordinate)
        && level.getObjectInGameAt(newCoordinate).getType() != ObjectType.GROUND) {
      possibleMoves.add(Move.newLeftMove(newCoordinate));
    }
  }

  private void checkJumpMove(Level level) {
    final int column = coordinate.getColumn();
    final int newRow = coordinate.getRow() + 1;
    Coordinate newCoordinate = Coordinate.of(newRow, column);
    // check if the new position is within bounds and not occupied
    if (level.isPositionWithinBounds(newCoordinate)
        && level.hasObjectInGameAt(newCoordinate)
        && level.getObjectInGameAt(newCoordinate).getType() != ObjectType.GROUND) {
      possibleMoves.add(Move.newJumpMove(newCoordinate));
    }
  }

  /** Returns an immutable list of the possible moves. */
  public List<Move> getPossibleMoves() {
    return List.copyOf(possibleMoves);
  }

  /** Returns an immutable list of the possible move's coordinates. */
  public List<Coordinate> getPossibleMoveCoordinates() {
    List<Coordinate> coordList = new ArrayList<>();
    for (Move move : possibleMoves) {
      coordList.add(move.getNewCoordinate());
    }
    return coordList;
  }

  @Override
  public ObjectInGame withNewPosition(Coordinate coordinate) {
    assert isValid();
    return createPlayer(coordinate);
  }

  @Override
  public ObjectInGame copyOf() {
    if (isNone()) {
      throw new AssertionError("Error: Player cannot be NONE!");
    }
    return new Player(objectType, coordinate, possibleMoves);
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
    return "Player{" + "objectType=" + objectType + ", coordinate=" + coordinate + "}";
  }
}
