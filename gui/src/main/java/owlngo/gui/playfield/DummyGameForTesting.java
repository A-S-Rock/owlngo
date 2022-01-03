package owlngo.gui.playfield;

import javafx.scene.input.KeyCode;
import owlngo.gui.data.ElementsInPlayfield;

/** Serves as a dummy game for testing. */
public final class DummyGameForTesting {
  static int column;
  static int row;
  static int columnOld;
  static int rowOld;

  static ElementsInPlayfield.ElementInPlayfield old;

  /** Start setup for a game with fixed dimensions. */
  public static void setup() {
    column = 15;
    row = 15;
    old = ElementsInPlayfield.getElement(row, column);
    ElementsInPlayfield.setElementTo(ElementsInPlayfield.ElementInPlayfield.OWL, row, column);
    PlayfieldWindowControler.changeAllPanesDependingOnElementsInPlayfied(); // Update Graphics
  }

  /** Moves the owl with the used key. */
  public static void moveOwl(KeyCode keyCode) {
    columnOld = column;
    rowOld = row;
    if (keyCode == KeyCode.NUMPAD8) {
      row = row - 1;
      changeValueInElementsInPlayfield();
    } else if (keyCode == KeyCode.NUMPAD2) {
      row = row + 1;
      changeValueInElementsInPlayfield();
    } else if (keyCode == KeyCode.NUMPAD6) {
      column = column + 1;
      changeValueInElementsInPlayfield();
    } else if (keyCode == KeyCode.NUMPAD4) {
      column = column - 1;
      changeValueInElementsInPlayfield();
    }
  }

  private static void changeValueInElementsInPlayfield() {
    ElementsInPlayfield.setElementTo(old, rowOld, columnOld);
    old = ElementsInPlayfield.getElement(row, column);
    ElementsInPlayfield.setElementTo(ElementsInPlayfield.ElementInPlayfield.OWL, row, column);
    PlayfieldWindowControler.changeAllPanesDependingOnElementsInPlayfied(); // Update Graphics
  }
}
