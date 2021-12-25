package owlngo.editor;

// This class contains a two dimentional array of the enumes elementInPlayfield.
// It represents all graphic elements that are displayed on all the panes in the gridPane.

public class ElementsInPlayfield {
  // Constant values for window
  final static int numberOfPanesInRowAnColumn = 30;
  private static ElementInPlayfield [][] elementInPlayfield = new ElementInPlayfield[numberOfPanesInRowAnColumn][numberOfPanesInRowAnColumn];

  static public void setAllToNoElement(){
    for (int row = 0; row < numberOfPanesInRowAnColumn; row++) {
      for (int column = 0; column < numberOfPanesInRowAnColumn; column++) {
        elementInPlayfield [row][column] = ElementInPlayfield.NO_ELEMENT;
      }
    }
  }

  static public void setElementTo(ElementInPlayfield element,int row,int column){
    elementInPlayfield [row][column] = element;
  }

  static public ElementInPlayfield getElement (int row, int column){
    return elementInPlayfield[row][column];

  }

  }