package owlngo.data;

import java.util.List;

/** The class stores all elements of the gamefield. */
public class ElementsInPlayfield {

  private static final ElementInPlayfield[][] elementInPlayfield =
      new ElementInPlayfield[MethodsForElement.size][MethodsForElement.size];

  /** Sets all elements of ElementInPlayfield to ElementInPlayfield.NO_ELEMENT */
  public static void setAllToNoElement() {
    for (int row = 0; row < MethodsForElement.size; row++) {
      for (int column = 0; column < MethodsForElement.size; column++) {
        elementInPlayfield[row][column] = ElementInPlayfield.NO_ELEMENT;
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
    elementInPlayfield[row][column] = element;
  }

  /**
   * Returns the element at the position (row, column) on the playing field.
   *
   * @param row position on the playfield
   * @param column position on the playfield
   */
  public static ElementInPlayfield getElement(int row, int column) {
    return elementInPlayfield[row][column];
  }

  /**
   * Returns true if the element at (row, column) is a ground element. For anmimation, a different
   * ground element may be used.
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
    return groundList.contains(elementInPlayfield[row][column]);
  }

  /**
   * Returns true if the element at (row, column) is an owl element. For anmimation, a different owl
   * element may be used.
   *
   * @param row position on the playfield
   * @param column position on the playfield
   */
  public static boolean isOwl(int row, int column) {
    List<ElementInPlayfield> owlList = List.of(ElementInPlayfield.OWL1, ElementInPlayfield.OWL);
    return owlList.contains(elementInPlayfield[row][column]);
  }

  /**
   * The enum distiguishs betwenn all the different graphic elements used as background in the panes
   * of the gridPane. This graphic elements represent all diffent pieces on the playfield for the
   * game
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
    NO_ELEMENT
  }
}
