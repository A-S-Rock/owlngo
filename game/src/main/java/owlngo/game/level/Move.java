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
   * RIGHT</code> direction and can also <code>JUMP</code>.
   */
  public enum MoveType { // TODO: Add extended JavaDocs for each move type.
    LEFT,
    RIGHT,
    JUMP
  }

  final MoveType moveType;
  final Coordinate newCoordinate;

  /** Constructs a new Move instance that moves the character rightward. */
  public static Move newRightwardMove(Coordinate coordinate) {
    return new Move(MoveType.RIGHT, coordinate); // Task4, ChessPiece
  }

  /** Constructs a new Move instance that moves the character leftward. */
  public static Move newLeftwardMove(Coordinate coordinate) {
    return new Move(MoveType.LEFT, coordinate); // Task4, ChessPiece
  }

  /** Constructs a new Move instance that lets the character jump. */
  public static Move newJumpMove(Coordinate coordinate) {
    return new Move(MoveType.JUMP, coordinate); // Task4, ChessPiece
  }

  private Move(MoveType moveType, Coordinate newCoordinate) {
    this.moveType = moveType;
    this.newCoordinate = newCoordinate;
  }

  /** Gets the type of the move. */
  public MoveType getMoveType() {
    return moveType;
  }

  /** Gets the new coordinate of the piece after applying the move. */
  public Coordinate getNewCoordinate() {
    return newCoordinate;
  }
}
