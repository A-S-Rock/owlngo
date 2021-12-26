package owlngo.playfield;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import owlngo.dataForEditorAndGamefield.ElementInPlayfield;
import owlngo.dataForEditorAndGamefield.ElementsInPlayfield;
import owlngo.dataForEditorAndGamefield.MethodsForElement;
import owlngo.editor.StoreLastKey;


// This class handles all actions on the window EditorWindow.fxml
// It also set the enum ElementInPlayfield for each pane that is used
// in the gritPane of the window.

public class PlayfieldWindowControler {
  // Constant values for window

  // static final int numberOfPanesInRowAnColumn = 30;
  static private Pane[][] pane =
      new Pane[MethodsForElement.numberOfPanesInRowAnColumn]
          [MethodsForElement.numberOfPanesInRowAnColumn];

  @FXML GridPane gridPanePlayfieldWindow;
  // Name must be as fx:ID in gridPane in PlayfieldToControlerFirstVersion.fxml
  // fx:id="gridPaneChessBoard"

  @FXML Label displayToUser;
  // Name must be as ID:ID  in Text in PlayfieldToControlerFirstVersion.fxml

  @FXML
  private void initialize() {
    System.out.println("PlayfieldWindowControler");
    initializePanes();
    setPanesOnPlayfield();
  }

  private void initializePanes(){
    System.out.println("initializePanes");
    for (int columnIndex = 0;
         columnIndex < MethodsForElement.numberOfPanesInRowAnColumn; columnIndex++) {
      for (int rowIndex = 0;
           rowIndex < MethodsForElement.numberOfPanesInRowAnColumn; rowIndex++) {
        pane[rowIndex][columnIndex] = new Pane();
      }
    }
  }

  private void setPanesOnPlayfield() {
    System.out.println("setPanesOnBoard");
    // Pane[][] pane = new Pane[numberOfPanesInRowAnColumn][numberOfPanesInRowAnColumn];
    for (int columnIndex = 0;
         columnIndex < MethodsForElement.numberOfPanesInRowAnColumn; columnIndex++) {
      for (int rowIndex = 0;
           rowIndex < MethodsForElement.numberOfPanesInRowAnColumn; rowIndex++) {
        // Set Background of pane depending on the content of elementsInPlayfield

        pane[rowIndex][columnIndex]=setBackgroundOfPaneDependingOnContent( rowIndex, columnIndex);
        gridPanePlayfieldWindow.add(pane[rowIndex][columnIndex], columnIndex, rowIndex);
      }
    }
  }

  static public void changeAllPanesDependingOnElementsInPlayfied(){
    System.out.println(("changeAllPanesDependingOnElementsInPlayfied"));
    for (int columnIndex = 0;
         columnIndex < MethodsForElement.numberOfPanesInRowAnColumn; columnIndex++) {
      for (int rowIndex = 0;
           rowIndex < MethodsForElement.numberOfPanesInRowAnColumn; rowIndex++) {
        // Set Background of pane depending on the content of elementsInPlayfield
        // not working
        // pane[rowIndex][columnIndex]=setBackgroundOfPaneDependingOnContent(  rowIndex,columnIndex);

        BackgroundFill backgroundFill;
        backgroundFill=
            MethodsForElement.getBackgroundFill(ElementsInPlayfield.getElement(rowIndex,columnIndex));
        Background background = new Background(backgroundFill);
        pane[rowIndex][columnIndex].setBackground(background);

        // working
        // setBackgroundOfPaneDependingOnContent(pane[rowIndex][columnIndex], rowIndex, columnIndex);
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



  // Set Background of pane depending on the content of elementsInPlayfield
  static private Pane setBackgroundOfPaneDependingOnContent(int row, int column) {
    // System.out.println("setBackgroundOfPaneDependingOnContent");
    Pane pane = new Pane();
    BackgroundFill backgroundFill;

    backgroundFill =
        MethodsForElement.getBackgroundFill(ElementsInPlayfield.getElement(row, column));
    Background background = new Background(backgroundFill);
    pane.setBackground(background);
    return pane;
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

  void rotate() {
    RotateTransition rt = new RotateTransition(Duration.millis(1000), gridPanePlayfieldWindow);
    rt.setByAngle(180);
    rt.setCycleCount(1);
    rt.setAutoReverse(true);
    rt.play();
  }
}
