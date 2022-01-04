package owlngo.game.level;

import owlngo.game.OwlnGo;
import owlngo.game.level.objects.Player;

/**
 * This class represents the move of a {@link Player} on a {@link Level} of an {@link OwlnGo} game.
 * The source code from this class has been adjusted from the Task 4 code.
 */
public final class Move {

  /**
   * Represents the types of moves. A {@link Player} can move in the <code>LEFT</code> or <code>
   * RIGHT</code> direction and can also <code>JUMP</code> and <code>FALL</code>.
   */
  public enum MoveType {
    LEFT,
    RIGHT,
    JUMP,
    FALL
  }

  final MoveType moveType;
  final Coordinate newCoordinate;

  private Move(MoveType moveType, Coordinate newCoordinate) {
    this.moveType = moveType;
    this.newCoordinate = newCoordinate;
  }

  /** Constructs a new Move instance that moves the character rightward. */
  public static Move newRightMove(Coordinate coordinate) {
    return new Move(MoveType.RIGHT, coordinate); // Task4, ChessPiece
  }

  /** Constructs a new Move instance that moves the character leftward. */
  public static Move newLeftMove(Coordinate coordinate) {
    return new Move(MoveType.LEFT, coordinate); // Task4, ChessPiece
  }

  /** Constructs a new Move instance that lets the character jump. */
  public static Move newJumpMove(Coordinate coordinate) {
    return new Move(MoveType.JUMP, coordinate); // Task4, ChessPiece
  }

  /** Constructs a new Move instance that lets the character jump. */
  public static Move newFallMove(Coordinate coordinate) {
    return new Move(MoveType.FALL, coordinate); // Task4, ChessPiece
  }

  /** Gets the type of the move. */
  public MoveType getMoveType() {
    return moveType;
  }

  /** Returns a new Move for the given move type. */
  public Move getMoveByType(MoveType moveType) {
    if (moveType == MoveType.FALL) {
      return newFallMove(newCoordinate);
    } else if (moveType == MoveType.RIGHT) {
      return newRightMove(newCoordinate);
    } else if (moveType == MoveType.LEFT) {
      return newLeftMove(newCoordinate);
    } else if (moveType == MoveType.JUMP) {
      return newJumpMove(newCoordinate);
    }
    throw new AssertionError("Unknown movetype.");
  }

  /** Gets the new coordinate of the piece after applying the move. */
  public Coordinate getNewCoordinate() {
    return newCoordinate;
  }
}
