package owlngo.game.level.objects;

import owlngo.game.level.Coordinate;
import owlngo.game.level.Level;
import owlngo.game.level.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * The player representation in the game. These objects need the ability to move on the level. This
 * class has been adjusted from the Task 4 template.
 */
public class Player implements ObjectInGame {

  private final ObjectType objectType;
  private final Coordinate coordinate;
  private final List<Move> possibleMoves;

  /**
   * Creates a fresh player object at the given position.
   *
   * @param coord the coordinate to put the player at
   * @return the player object
   */
  public static Player createPlayer(Coordinate coord) {
    return new Player(ObjectType.PLAYER, coord, new ArrayList<>());
  }

  private Player(ObjectType objectType, Coordinate coordinate, List<Move> possibleMoves) {
    this.objectType = objectType;
    this.coordinate = coordinate;
    this.possibleMoves = possibleMoves;
  }

  /** Returns a clone of the object. */
  public Player copyOf() {
    if (isNone()) {
      throw new AssertionError("Error: Player cannot be NONE!");
    }
    return new Player(objectType, coordinate, possibleMoves);
  }

  /**
   * Creates a new instance of player where coordinate is as given and with empty possible moves.
   * Other members are as in this instance.
   */
  public Player withNewPosition(Coordinate coordinate) {
    assert isValid();
    return createPlayer(coordinate);
  }

  public void updatePossibleMoves(Level level) {
    assert isValid();
    possibleMoves.clear();
    checkRightwardMove(level);
    checkLeftwardMove(level);
    checkJumpMove(level);
  }

  private void checkRightwardMove(Level level) {
    final int row = coordinate.getRow();
    final int newColumn = coordinate.getColumn() + 1;
    Coordinate newCoordinate = Coordinate.of(row, newColumn);
    // check if the new position is within bounds and not occupied
    if (level.isPositionWithinBounds(newCoordinate) && !level.hasObjectInGameAt(newCoordinate)) {
      possibleMoves.add(Move.newRightwardMove(newCoordinate));
    }
  }

  private void checkLeftwardMove(Level level) {
    final int row = coordinate.getRow();
    final int newColumn = coordinate.getColumn() - 1;
    Coordinate newCoordinate = Coordinate.of(row, newColumn);
    // check if the new position is within bounds and not occupied
    if (level.isPositionWithinBounds(newCoordinate) && !level.hasObjectInGameAt(newCoordinate)) {
      possibleMoves.add(Move.newLeftwardMove(newCoordinate));
    }
  }

  private void checkJumpMove(Level level) {
    final int column = coordinate.getColumn();
    final int newRow = coordinate.getRow() + 1;
    Coordinate newCoordinateAtBottomOfJump = Coordinate.of(newRow, column);
    // check if the new position is within bounds and not occupied
    if (level.isPositionWithinBounds(newCoordinateAtBottomOfJump)
        && !level.hasObjectInGameAt(newCoordinateAtBottomOfJump)) {
      possibleMoves.add(Move.newJumpMove(newCoordinateAtBottomOfJump));
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
}
