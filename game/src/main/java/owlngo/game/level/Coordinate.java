package owlngo.game.level;

import java.util.Objects;
import owlngo.game.OwlnGo;
import owlngo.game.level.objects.ObjectInGame;

/**
 * This class represents the position of a {@link ObjectInGame} on a {@link Level} of an {@link
 * OwlnGo} game. The Coordinate class is used similar in task4.
 */
public final class Coordinate implements Comparable<Coordinate> {
  private final int row;
  private final int column;

  private Coordinate(int row, int column) {
    this.row = row;
    this.column = column;
  }

  /** Creates a Coordinate instance with the given position. */
  public static Coordinate of(int row, int column) {
    return new Coordinate(row, column);
  }

  /** Gets the row number. */
  public int getRow() {
    return row;
  }

  /** Gets the column number. */
  public int getColumn() {
    return column;
  }

  @Override
  public int compareTo(Coordinate coordinate) {
    int rowDiff = getRow() - coordinate.getRow();
    if (rowDiff != 0) {
      return rowDiff;
    }
    return getColumn() - coordinate.getColumn();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Coordinate that = (Coordinate) o;
    return row == that.row && column == that.column;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, column);
  }

  @Override
  public String toString() {
    return "Coordinate{" + "row=" + row + ", column=" + column + '}';
  }
}
