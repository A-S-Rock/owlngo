package owlngo.playfield;

import javafx.scene.input.KeyCode;
// import owlngo.dataForEditorAndGamefield.ElementInPlayfield;
import owlngo.data.ElementsInPlayfield;


public final class DummyGameForTesting {
  static int column;
  static int row;
  static int columnOld;
  static int rowOld;

  static ElementsInPlayfield.ElementInPlayfield old;

  public static void setup() {
    column = 15;
    row = 15;
    old = ElementsInPlayfield.getElement(row, column);
    ElementsInPlayfield.setElementTo(ElementsInPlayfield.ElementInPlayfield.OWL, row, column);
    PlayfieldWindowControler.changeAllPanesDependingOnElementsInPlayfied(); // Update Graphics
  }

  public static void moveOwl(KeyCode keyCode) {
    columnOld = column;
    rowOld = row;
    switch (keyCode) {
      case NUMPAD8:
        row = row - 1;
        changeValueInElementsInPlayfield();
        break;
      case NUMPAD2:
        row = row + 1;
        changeValueInElementsInPlayfield();
        break;
      case NUMPAD6:
        column = column + 1;
        changeValueInElementsInPlayfield();
        break;
      case NUMPAD4:
        column = column - 1;
        changeValueInElementsInPlayfield();
        break;
    }
  }

  private static void changeValueInElementsInPlayfield() {
    ElementsInPlayfield.setElementTo(old, rowOld, columnOld);
    old = ElementsInPlayfield.getElement(row, column);
    ElementsInPlayfield.setElementTo(ElementsInPlayfield.ElementInPlayfield.OWL, row, column);
    PlayfieldWindowControler.changeAllPanesDependingOnElementsInPlayfied(); // Update Graphics
  }
}
