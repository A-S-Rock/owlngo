package owlngo.gui.data;

import static owlngo.gui.data.MethodsForElement.OBJECT_TYPE_ELEMENT_IN_PLAYFIELD_MAP;

import java.util.List;
import owlngo.game.level.Coordinate;
import owlngo.game.level.Level;
import owlngo.game.level.objects.ObjectInGame.ObjectType;

/** The class stores all elements of the gamefield and provides utilities for them. */
public class ElementsInPlayfield {

  private static final ElementInPlayfield[][] ELEMENTS_IN_PLAYFIELD =
      new ElementInPlayfield[MethodsForElement.SIZE][MethodsForElement.SIZE];
  private static Level LEVEL = new Level(MethodsForElement.SIZE, MethodsForElement.SIZE);

  /** Changes the elements in the playing field to empty elements. */
  public static void setAllToNoElement() {
    for (int row = 0; row < MethodsForElement.SIZE; row++) {
      for (int column = 0; column < MethodsForElement.SIZE; column++) {
        ELEMENTS_IN_PLAYFIELD[row][column] = ElementInPlayfield.NO_ELEMENT;
      }
    }
  }

  /**
   * Sets the element at the position (row, column).
   *
   * @param element that is set
   * @param row position on the playfield
   * @param column position on the playfield
   */
  public static void setElementTo(ElementInPlayfield element, int row, int column) {
    ELEMENTS_IN_PLAYFIELD[row][column] = element;
  }

  /**
   * Gets the element at the position (row, column).
   *
   * @param row position on the playfield
   * @param column position on the playfield
   */
  public static ElementInPlayfield getElement(int row, int column) {
    return ELEMENTS_IN_PLAYFIELD[row][column];
  }

  /**
   * Returns true if the element at (row, column) is a ground element. For anmimation different
   * ground elements may be used.
   *
   * @param row position on the playfield
   * @param column position on the playfield
   */
  public static boolean isGround(int row, int column) {
    List<ElementInPlayfield> groundList =
        List.of(
            ElementInPlayfield.GROUND_NO_LAWN,
            ElementInPlayfield.GROUND_TOP_LAWN,
            ElementInPlayfield.GROUND_LEFT_TOP_LAWN,
            ElementInPlayfield.GROUND_LEFT_TOP_RIGHT_LAWN,
            ElementInPlayfield.GROUND_TOP_RIGHT_LAWN);
    return groundList.contains(ELEMENTS_IN_PLAYFIELD[row][column]);
  }

  /**
   * Returns true if the element at (row, column) is an owl element. For anmimation different owl
   * elements may be used.
   *
   * @param row position on the playfield
   * @param column position on the playfield
   */
  public static boolean isOwl(int row, int column) {
    List<ElementInPlayfield> owlList = List.of(ElementInPlayfield.OWL1, ElementInPlayfield.OWL);
    return owlList.contains(ELEMENTS_IN_PLAYFIELD[row][column]);
  }

  /** Sets the level based on the information of the array. */
  public static void setLevelForGameDependingOnElementsInPlayfield() {
    for (int columnIndex = 0; columnIndex < MethodsForElement.SIZE; columnIndex++) {
      for (int rowIndex = 0; rowIndex < MethodsForElement.SIZE; rowIndex++) {
        Coordinate coordinate = Coordinate.of(rowIndex, columnIndex);
        if (isGround(rowIndex, columnIndex)) {
          LEVEL = LEVEL.withGroundAt(coordinate);
        } else if (isOwl(rowIndex, columnIndex)) {
          LEVEL = LEVEL.withNewPlayerAt(coordinate);
        } else if (ELEMENTS_IN_PLAYFIELD[rowIndex][columnIndex] == ElementInPlayfield.START) {
          LEVEL = LEVEL.withStartAt(coordinate);
        } else if (ELEMENTS_IN_PLAYFIELD[rowIndex][columnIndex] == ElementInPlayfield.END) {
          LEVEL = LEVEL.withFinishAt(coordinate);
        } else if (ELEMENTS_IN_PLAYFIELD[rowIndex][columnIndex] == ElementInPlayfield.NO_ELEMENT) {
          LEVEL = LEVEL.withAirAt(coordinate);
          // No element is not an element in the game
        } else if (ELEMENTS_IN_PLAYFIELD[rowIndex][columnIndex] == ElementInPlayfield.DANGER) {
          LEVEL = LEVEL.withFireAt(coordinate);
        } else if (ELEMENTS_IN_PLAYFIELD[rowIndex][columnIndex] == ElementInPlayfield.FOOD) {
          LEVEL = LEVEL.withFoodAt(coordinate);
        }
      }
    }
  }

  /** Returns the level created for the GUI. */
  public static Level getLevel() {
    return LEVEL.copyOf();
  }

  /** Sets the information in the array depending on the level in the dataManager. */
  public static void setElementsInPlayfieldDependingOnLevelFromDataManager() {
    DataManager manager = DataManager.getInstance();
    final Level level = manager.getLevelContent();
    int maxColumns = level.getNumColumns();
    int maxRows = level.getNumRows();
    for (int columnIndex = 0; columnIndex < maxColumns; columnIndex++) {
      for (int rowIndex = 0; rowIndex < maxRows; rowIndex++) {
        Coordinate coordinate = Coordinate.of(rowIndex, columnIndex);
        ObjectType objectType = level.getObjectInGameAt(coordinate).getType();
        ELEMENTS_IN_PLAYFIELD[rowIndex][columnIndex] =
            OBJECT_TYPE_ELEMENT_IN_PLAYFIELD_MAP.get(objectType);
      }
    }
  }

  /**
   * The enum distiguishes between all the different graphic elements used as background.
   *
   * <ul>
   *   <li>{@code OWL}: Owl
   *   <li>{@code OWL1}: Other picture of the owl
   *   <li>{@code START}: Where the game starts
   *   <li>{@code END}: Position where the game ends
   *   <li>{@code GROUND_NO_LAWN}: Ground element
   *   <li>{@code GROUND_TOP_LAWN}: Ground element
   *   <li>{@code GROUND_TOP_RIGHT_LAWN}: Ground element
   *   <li>{@code GROUND_TOP_RIGHT_LAWN}: Ground element
   *   <li>{@code GROUND_LEFT_TOP_RIGHT_LAWN}: Ground element
   *   <li>{@code NO_ELEMENT}: No element
   * </ul>
   */
  public enum ElementInPlayfield {
    OWL,
    OWL1,
    START,
    END,
    GROUND_NO_LAWN,
    GROUND_TOP_LAWN,
    GROUND_LEFT_TOP_LAWN,
    GROUND_TOP_RIGHT_LAWN,
    GROUND_LEFT_TOP_RIGHT_LAWN,
    NO_ELEMENT,
    DANGER,
    FOOD
  }
}
