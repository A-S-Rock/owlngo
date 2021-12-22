package owlngo.editor;

public class ElementsInPlayfield {
  // Constant values for window
  final static int numberOfPanesInRowAnColumn = 30;
  private static ElementInPlayfield [][] elementInPlayfield = new ElementInPlayfield[numberOfPanesInRowAnColumn][numberOfPanesInRowAnColumn];


  public void ElementsInPlayfield(){
    for (int row = 0; row < numberOfPanesInRowAnColumn; row++) {
      for (int column = 0; column < numberOfPanesInRowAnColumn; column++) {
        elementInPlayfield [row][column] = ElementInPlayfield.NO_ELEMENT;
      }
    }
  }

  public void setAllToNoElement(){
    for (int row = 0; row < numberOfPanesInRowAnColumn; row++) {
      for (int column = 0; column < numberOfPanesInRowAnColumn; column++) {
        elementInPlayfield [row][column] = ElementInPlayfield.NO_ELEMENT;
      }
    }
  }


  public void setElementTo(ElementInPlayfield element,int row,int column){
    elementInPlayfield [row][column] = element;
  }

  public ElementInPlayfield getElement (int row, int column){
    return elementInPlayfield[row][column];

  }

  }