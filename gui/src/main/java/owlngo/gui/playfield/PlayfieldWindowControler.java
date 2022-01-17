package owlngo.gui.playfield;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import owlngo.game.OwlnGo;
import owlngo.game.level.Coordinate;
import owlngo.game.level.objects.ObjectInGame;
import owlngo.gui.data.ElementsInPlayfield;
import owlngo.gui.data.MethodsForElement;

import static owlngo.gui.data.MethodsForElement.OBJECT_TYPE_ELEMENT_IN_PLAYFIELD_MAP;

// This class handles all actions on the window EditorWindow.fxml
// It also set the enum ElementInPlayfield for each pane that is used
// in the gritPane of the window.

/**
 * The class handles all actions on the window PlayfieldWindow.fxml It displays the element in
 * ElementsInPlayfield as panes in a gridpane. The background is set according to the elements. A
 * key handler is setup that allows to move the owl with 8,4,2,6.
 */
public class PlayfieldWindowControler {
  // 6.1. 14.20
  // Constant values for window
  // static final int numberOfPanesInRowAnColumn = 30;
  private static final Pane[][] pane = new Pane[MethodsForElement.SIZE][MethodsForElement.SIZE];

  // private static final OwlnGo game = new OwlnGo(MethodsForElement.SIZE, MethodsForElement.SIZE);
  private static final OwlnGo game = new OwlnGo(ElementsInPlayfield.getLevel());

  private static Coordinate oldCoordinate;

  @FXML GridPane gridPanePlayfieldWindow;
  // Name must be as fx:ID in gridPane in PlayfieldToControlerFirstVersion.fxml
  // fx:id="gridPaneChessBoard"

  @FXML Label displayToUser;
  // Name must be as ID:ID  in Text in PlayfieldToControlerFirstVersion.fxml

  @FXML
  private void initialize() {
    System.out.println("PlayfieldWindowControler");
    // setChangeListner();
    getElementsOfPlayfieldFromGame();
    initializePanes();
    setPanesOnPlayfield();

    // OwlnGo game= new OwlnGo();
  }

  /*
  private static void setChangeListner() {
    oldCoordinate=game.getGameState().getPlayer().getCoordinate();
    game.getGameState()
        .propertyPlayer()
        .addListener(
            new ChangeListener<Player>() {
              @Override
              public void changed(
                  ObservableValue<? extends Player> observable, Player oldValue, Player newValue) {
                System.out.println(
                    "Es hat sich was geändert"
                        + game.getGameState().getPlayer().getCoordinate().toString());
                Coordinate newCoordinate = game.getGameState().getPlayer().getCoordinate();
                while ((oldCoordinate.getColumn() == newCoordinate.getColumn())
                    && (oldCoordinate.getRow() == newCoordinate.getRow())) {
                  newCoordinate = game.getGameState().getPlayer().getCoordinate();
                  System.out.print("*");
                }
                oldCoordinate = newCoordinate;
                for (int x = 1; x < 100; x++) {
                  changeAllPanesDependingOnElementsInPlayfied();
                }
              }
            });


  }
  */

  private static void getElementsOfPlayfieldFromGame() {
    for (int columnIndex = 0; columnIndex < MethodsForElement.SIZE; columnIndex++) {
      for (int rowIndex = 0; rowIndex < MethodsForElement.SIZE; rowIndex++) {
        Coordinate coordinate = Coordinate.of(rowIndex, columnIndex);
        ObjectInGame.ObjectType objectType =
            game.getGameState().getLevel().getObjectInGameAt(coordinate).getType();
        // System.out.print(objectType+" ");
        ElementsInPlayfield.ElementInPlayfield elementsInPlayfield;
        elementsInPlayfield = OBJECT_TYPE_ELEMENT_IN_PLAYFIELD_MAP.get(objectType);
        // System.out.print(elementsInPlayfield+" ");
        ElementsInPlayfield.setElementTo(elementsInPlayfield, rowIndex, columnIndex);
      }
    }
  }

  /**
   * The method changes the background of the panes in the gridpane depending on all the elements
   * set in ElementsInPlayfield.
   */
  public static void changeAllPanesDependingOnElementsInPlayfied() {
    System.out.println(("changeAllPanesDependingOnElementsInPlayfied"));
    for (int columnIndex = 0; columnIndex < MethodsForElement.SIZE; columnIndex++) {
      for (int rowIndex = 0; rowIndex < MethodsForElement.SIZE; rowIndex++) {
        // Set Background of pane depending on the content of elementsInPlayfield
        // not working
        // pane[rowIndex][columnIndex]=setBackgroundOfPaneDependingOnContent(
        // rowIndex,columnIndex);

        BackgroundFill backgroundFill;
        backgroundFill =
            MethodsForElement.getBackgroundFill(
                ElementsInPlayfield.getElement(rowIndex, columnIndex));
        Background background = new Background(backgroundFill);
        pane[rowIndex][columnIndex].setBackground(background);

        // working
        // setBackgroundOfPaneDependingOnContent(pane[rowIndex][columnIndex], rowIndex,
        // columnIndex);
      }
    }
  }

  // Set Background of pane depending on the content of elementsInPlayfield
  private static Pane setBackgroundOfPaneDependingOnContent(int row, int column) {
    // System.out.println("setBackgroundOfPaneDependingOnContent");
    Pane pane = new Pane();
    BackgroundFill backgroundFill;

    backgroundFill =
        MethodsForElement.getBackgroundFill(ElementsInPlayfield.getElement(row, column));
    Background background = new Background(backgroundFill);
    pane.setBackground(background);
    return pane;
  }

  /* Initializes the Gamefield with panes */
  private void initializePanes() {
    System.out.println("initializePanes");
    for (int columnIndex = 0; columnIndex < MethodsForElement.SIZE; columnIndex++) {
      for (int rowIndex = 0; rowIndex < MethodsForElement.SIZE; rowIndex++) {
        pane[rowIndex][columnIndex] = new Pane();
      }
    }
  }

  private void setPanesOnPlayfield() {
    System.out.println("setPanesOnBoard");
    // Pane[][] pane = new Pane[numberOfPanesInRowAnColumn][numberOfPanesInRowAnColumn];
    for (int columnIndex = 0; columnIndex < MethodsForElement.SIZE; columnIndex++) {
      for (int rowIndex = 0; rowIndex < MethodsForElement.SIZE; rowIndex++) {
        // Set Background of pane depending on the content of elementsInPlayfield

        pane[rowIndex][columnIndex] = setBackgroundOfPaneDependingOnContent(rowIndex, columnIndex);
        gridPanePlayfieldWindow.add(pane[rowIndex][columnIndex], columnIndex, rowIndex);
      }
    }
  }

  @FXML
  void restartGame() {

    rotate360();
  }

  public static void interpreteKeys(KeyCode keyCode) {
    if (keyCode == KeyCode.NUMPAD8 || keyCode==keyCode.getKeyCode("w")
        || keyCode==keyCode.getKeyCode("W")) {
      game.moveJump(false);
    } else if (keyCode == KeyCode.NUMPAD2 ) {

    } else if ((keyCode == KeyCode.NUMPAD6) || (keyCode==KeyCode.getKeyCode("d"))
        || (keyCode==KeyCode.getKeyCode("D"))) {
      game.moveRight();
    } else if (keyCode == KeyCode.NUMPAD4 || keyCode==keyCode.getKeyCode("a")
        || keyCode==keyCode.getKeyCode("A")) {
      game.moveLeft();
    }
    getElementsOfPlayfieldFromGame();
    changeAllPanesDependingOnElementsInPlayfied();
  }

  void rotate360() {
    RotateTransition rt = new RotateTransition(Duration.millis(1000), gridPanePlayfieldWindow);
    rt.setByAngle(360);
    rt.setCycleCount(1);
    rt.setAutoReverse(false);
    rt.play();
  }

  void rotate() {
    RotateTransition rt = new RotateTransition(Duration.millis(1000), gridPanePlayfieldWindow);
    rt.setByAngle(180);
    rt.setCycleCount(1);
    rt.setAutoReverse(true);
    rt.play();
  }
}
