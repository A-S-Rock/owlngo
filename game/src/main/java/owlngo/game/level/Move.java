package owlngo.game.level;

import owlngo.game.level.objects.ObjectInGame;

public final class Move {

    /** Represents the types of moves. */
    public enum MoveType { //TODO: Add extended JavaDocs for each move type.
        FORWARD,
        BACKWARD,
        JUMP
    }

    final MoveType moveType;
    final Coordinate newCoordinate;

    /** Constructs a new Move instance that moves the character forward. */
    public static Move newForwardMove(Coordinate coordinate) {
        return new Move(MoveType.FORWARD, coordinate); // Task4, ChessPiece
    }

    /** Constructs a new Move instance that moves the character backward. */
    public static Move newBackwardMove(Coordinate coordinate) {
        return new Move(MoveType.BACKWARD, coordinate); // Task4, ChessPiece
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
