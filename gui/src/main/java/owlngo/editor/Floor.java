package owlngo.editor;


public class Floor {

  // Constant values for window
  final static int numberOfPanesInRowAnColumn = 30;
  private static Boolean[][] isFloor = new Boolean[numberOfPanesInRowAnColumn][numberOfPanesInRowAnColumn];

  public void Floor() {
    for (int row = 0; row < numberOfPanesInRowAnColumn; row++) {
      for (int column = 0; column < numberOfPanesInRowAnColumn; column++) {
        isFloor[row][column] = false;
      }
    }
  }

  public void setAllToFalse() {
    for (int row = 0; row < numberOfPanesInRowAnColumn; row++) {
      for (int column = 0; column < numberOfPanesInRowAnColumn; column++) {
        isFloor[row][column] = false;
      }
    }
  }

  public void setFloorToTrue(int row, int column) {
    isFloor[row][column] = true;
  }

  public void setFloorToFalse(int row, int column) {
    isFloor[row][column] = false;
  }

  public Boolean getFloor(int row, int column) {
    return isFloor[row][column];
  }

}
