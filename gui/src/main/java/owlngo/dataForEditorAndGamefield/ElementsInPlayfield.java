package owlngo.dataForEditorAndGamefield;

// This class contains a two dimentional array of the enumes elementInPlayfield.
// It represents all graphic elements that are displayed on all the panes in the gridPane.

public class ElementsInPlayfield {

  private static final ElementInPlayfield[][] elementInPlayfield =
      new ElementInPlayfield[MethodsForElement.numberOfPanesInRowAnColumn]
          [MethodsForElement.numberOfPanesInRowAnColumn];

  public static void setAllToNoElement() {
    for (int row = 0; row < MethodsForElement.numberOfPanesInRowAnColumn; row++) {
      for (int column = 0; column < MethodsForElement.numberOfPanesInRowAnColumn; column++) {
        elementInPlayfield[row][column] = ElementInPlayfield.NO_ELEMENT;
      }
    }
  }

  public static void setElementTo(ElementInPlayfield element, int row, int column) {
    elementInPlayfield[row][column] = element;
  }

  public static ElementInPlayfield getElement(int row, int column) {
    return elementInPlayfield[row][column];
  }
}
