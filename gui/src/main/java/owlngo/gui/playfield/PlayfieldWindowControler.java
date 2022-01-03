package owlngo.gui.playfield;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import owlngo.gui.data.ElementsInPlayfield;
import owlngo.gui.data.MethodsForElement;

/** Controller class for the playfield window. */
public class PlayfieldWindowControler {

  private static final Pane[][] pane = new Pane[MethodsForElement.size][MethodsForElement.size];

  @FXML GridPane gridPanePlayfieldWindow;
  // Name must be as fx:ID in gridPane in PlayfieldToControlerFirstVersion.fxml
  // fx:id="gridPaneChessBoard"

  /** Updates the panes on the field. */
  public static void changeAllPanesDependingOnElementsInPlayfied() {
    System.out.println(("changeAllPanesDependingOnElementsInPlayfied"));
    for (int columnIndex = 0; columnIndex < MethodsForElement.size; columnIndex++) {
      for (int rowIndex = 0; rowIndex < MethodsForElement.size; rowIndex++) {
        BackgroundFill backgroundFill;
        backgroundFill =
            MethodsForElement.getBackgroundFill(
                ElementsInPlayfield.getElement(rowIndex, columnIndex));
        Background background = new Background(backgroundFill);
        pane[rowIndex][columnIndex].setBackground(background);
      }
    }
  }

  // Set Background of pane depending on the content of elementsInPlayfield
  private static Pane setBackgroundOfPaneDependingOnContent(int row, int column) {
    Pane pane = new Pane();
    BackgroundFill backgroundFill;

    backgroundFill =
        MethodsForElement.getBackgroundFill(ElementsInPlayfield.getElement(row, column));
    Background background = new Background(backgroundFill);
    pane.setBackground(background);
    return pane;
  }

  @FXML
  private void initialize() {
    System.out.println("PlayfieldWindowControler");
    initializePanes();
    setPanesOnPlayfield();
  }

  private void initializePanes() {
    System.out.println("initializePanes");
    for (int columnIndex = 0; columnIndex < MethodsForElement.size; columnIndex++) {
      for (int rowIndex = 0; rowIndex < MethodsForElement.size; rowIndex++) {
        pane[rowIndex][columnIndex] = new Pane();
      }
    }
  }

  // faster
  /*
  static private void setBackgroundOfPaneDependingOnContent(pane, int row, int column) {
    BackgroundFill backgroundFill;
    backgroundFill =
        MethodsForElement.getBackgroundFill(ElementsInPlayfield.getElement(row, column));
    Background background = new Background(backgroundFill);
    pane.setBackground(background);
  }
  */

  private void setPanesOnPlayfield() {
    System.out.println("setPanesOnBoard");
    for (int columnIndex = 0; columnIndex < MethodsForElement.size; columnIndex++) {
      for (int rowIndex = 0; rowIndex < MethodsForElement.size; rowIndex++) {
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

  void rotate360() {
    RotateTransition rt = new RotateTransition(Duration.millis(1000), gridPanePlayfieldWindow);
    rt.setByAngle(360);
    rt.setCycleCount(1);
    rt.setAutoReverse(false);
    rt.play();
  }

  /*
  void rotate() {
    RotateTransition rt = new RotateTransition(Duration.millis(1000), gridPanePlayfieldWindow);
    rt.setByAngle(180);
    rt.setCycleCount(1);
    rt.setAutoReverse(true);
    rt.play();
  }
   */
}
