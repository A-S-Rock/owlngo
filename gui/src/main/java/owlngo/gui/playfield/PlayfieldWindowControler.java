package owlngo.gui.playfield;

import static owlngo.gui.data.MethodsForElement.OBJECT_TYPE_ELEMENT_IN_PLAYFIELD_MAP;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import owlngo.game.OwlnGo;
import owlngo.game.level.Coordinate;
import owlngo.game.level.objects.ObjectInGame;
import owlngo.gui.data.ElementsInPlayfield;
import owlngo.gui.data.MethodsForElement;

/**
 * Handles all actions on the playfield window. For testing only. Obsolete because one already
 * exists.
 */
public class PlayfieldWindowControler {

  private static final Pane[][] PANE = new Pane[MethodsForElement.SIZE][MethodsForElement.SIZE];
  private static final OwlnGo GAME = new OwlnGo(ElementsInPlayfield.getLevel());

  @FXML GridPane gridPanePlayfieldWindow;

  private static void getElementsOfPlayfieldFromGame() {
    for (int columnIndex = 0; columnIndex < MethodsForElement.SIZE; columnIndex++) {
      for (int rowIndex = 0; rowIndex < MethodsForElement.SIZE; rowIndex++) {
        Coordinate coordinate = Coordinate.of(rowIndex, columnIndex);
        ObjectInGame.ObjectType objectType =
            GAME.getGameState().getLevel().getObjectInGameAt(coordinate).getType();
        ElementsInPlayfield.ElementInPlayfield elementsInPlayfield;
        elementsInPlayfield = OBJECT_TYPE_ELEMENT_IN_PLAYFIELD_MAP.get(objectType);
        ElementsInPlayfield.setElementTo(elementsInPlayfield, rowIndex, columnIndex);
      }
    }
  }

  /**
   * The method changes the background of the panes in the gridpane depending on all the elements
   * set in ElementsInPlayfield.
   */
  public static void changeAllPanesDependingOnElementsInPlayfied() {
    for (int columnIndex = 0; columnIndex < MethodsForElement.SIZE; columnIndex++) {
      for (int rowIndex = 0; rowIndex < MethodsForElement.SIZE; rowIndex++) {

        BackgroundFill backgroundFill;
        backgroundFill =
            MethodsForElement.getBackgroundFill(
                ElementsInPlayfield.getElement(rowIndex, columnIndex));
        Background background = new Background(backgroundFill);
        PANE[rowIndex][columnIndex].setBackground(background);
      }
    }
  }

  private static Pane setBackgroundOfPaneDependingOnContent(int row, int column) {
    Pane pane = new Pane();
    BackgroundFill backgroundFill;

    backgroundFill =
        MethodsForElement.getBackgroundFill(ElementsInPlayfield.getElement(row, column));
    Background background = new Background(backgroundFill);
    pane.setBackground(background);
    return pane;
  }

  /**
   * Depending on keyboard entries on the Num Keybord of the letters WASD an action for the game is
   * started.
   *
   * @param keyCode keyboard entry
   */
  public static void interpreteKeys(KeyCode keyCode) {
    if (keyCode == KeyCode.NUMPAD8
        || keyCode == KeyCode.getKeyCode("w")
        || keyCode == KeyCode.getKeyCode("W")) {
      GAME.moveBasicJump();
    } else if ((keyCode == KeyCode.NUMPAD6)
        || (keyCode == KeyCode.getKeyCode("d"))
        || (keyCode == KeyCode.getKeyCode("D"))) {
      GAME.moveBasicRight();
    } else if (keyCode == KeyCode.NUMPAD4
        || keyCode == KeyCode.getKeyCode("a")
        || keyCode == KeyCode.getKeyCode("A")) {
      GAME.moveBasicLeft();
    }
    getElementsOfPlayfieldFromGame();
    changeAllPanesDependingOnElementsInPlayfied();
  }

  @FXML
  private void initialize() {
    getElementsOfPlayfieldFromGame();
    initializePanes();
    setPanesOnPlayfield();
  }

  private void initializePanes() {
    for (int columnIndex = 0; columnIndex < MethodsForElement.SIZE; columnIndex++) {
      for (int rowIndex = 0; rowIndex < MethodsForElement.SIZE; rowIndex++) {
        PANE[rowIndex][columnIndex] = new Pane();
      }
    }
  }

  private void setPanesOnPlayfield() {
    for (int columnIndex = 0; columnIndex < MethodsForElement.SIZE; columnIndex++) {
      for (int rowIndex = 0; rowIndex < MethodsForElement.SIZE; rowIndex++) {
        // Set Background of pane depending on the content of elementsInPlayfield

        PANE[rowIndex][columnIndex] = setBackgroundOfPaneDependingOnContent(rowIndex, columnIndex);
        gridPanePlayfieldWindow.add(PANE[rowIndex][columnIndex], columnIndex, rowIndex);
      }
    }
  }

  @FXML
  void restartGame() {
    // TODO: Actually start a new game.
  }
}
