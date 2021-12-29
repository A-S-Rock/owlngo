package owlngo.editor;

import javafx.animation.RotateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import owlngo.dataForEditorAndGamefield.ElementInPlayfield;
import owlngo.dataForEditorAndGamefield.ElementsInPlayfield;
import owlngo.dataForEditorAndGamefield.MethodsForElement;
import owlngo.playfield.DummyGameForTesting;

import java.io.IOException;

// This class handles all actions on the window EditorWindow.fxml
// It also set the enum ElementInPlayfield for each pane that is used in the gritPane of the window.

public class EditorWindowControler {

  @FXML GridPane gridPaneEditorWindow;
  /// @FXML GridPane gridPaneChessBoard;
  // Name must be as fx:ID in gridPane in PlayfieldToControlerFirstVersion.fxml
  // fx:id="gridPaneChessBoard"

  @FXML Label displayToUser;
  // Name must be as ID:ID  in Text in PlayfieldToControlerFirstVersion.fxml

  @FXML
  public void initialize() {
    System.out.println("initialilize windows EditorWindow.fxml");
    ElementsInPlayfield.setAllToNoElement();
    setPanesOnPlayfield();
  }
  // void setPanesOnBoard() throws FileNotFoundException
  void setPanesOnPlayfield() {
    System.out.println("setPanesOnBoard");
    Pane[][] pane =
        new Pane[MethodsForElement.numberOfPanesInRowAnColumn]
            [MethodsForElement.numberOfPanesInRowAnColumn];
    // floor.setAllToFalse();
    for (int columnIndex = 0;
        columnIndex < MethodsForElement.numberOfPanesInRowAnColumn;
        columnIndex++) {
      for (int rowIndex = 0; rowIndex < MethodsForElement.numberOfPanesInRowAnColumn; rowIndex++) {

        // Initialisation
        pane[rowIndex][columnIndex] = new Pane();

        // Set Background of pane depending on the content of elementsInPlayfield
        setBackgroundOfPaneDependingOnContent(pane[rowIndex][columnIndex], rowIndex, columnIndex);

        gridPaneEditorWindow.add(pane[rowIndex][columnIndex], columnIndex, rowIndex);
        // gridPaneChessBoard.add(pane[rowIndex][columnIndex], columnIndex, rowIndex);
        int finalColumnIndex = columnIndex;
        int finalRowIndex = rowIndex;
        pane[rowIndex][columnIndex].setOnMousePressed(
            (evt) ->
                setResetElement(
                    pane[finalRowIndex][finalColumnIndex], finalRowIndex, finalColumnIndex));
      }
    }
  }

  void setResetElement(Pane pane, int row, int column) {
    System.out.println("setResetElement");
    if (ElementsInPlayfield.getElement(row, column) == ElementInPlayfield.NO_ELEMENT) {
      // if (!floor.getFloor(row, column)) {
      System.out.print("I am set");
      System.out.print("Key:" + StoreLastKey.getLastKeyPressed() + " ");
      System.out.println(StoreLastKey.getLastKeyPressedAsString());

      if (MethodsForElement.validKey(StoreLastKey.getLastKeyPressedAsString())) {
        // set in elementsInPlayfield the elementInPlayfield depending on the key pressed
        final ElementInPlayfield elementInPlayfield =
            (MethodsForElement.getElementInPlayfield(StoreLastKey.getLastKeyPressedAsString()));
        ElementsInPlayfield.setElementTo(elementInPlayfield, row, column);
      }
      // Set Background of pane depending on the content of elementsInPlayfield
      setBackgroundOfPaneDependingOnContent(pane, row, column);

    } else {
      System.out.println("I am reset");
      // set in elementsInPlayfield the elementInPlayfield to noElement
      final ElementInPlayfield elementInPlayfield = ElementInPlayfield.NO_ELEMENT;
      ElementsInPlayfield.setElementTo(elementInPlayfield, row, column);

      // Set Background of pane depending on the content of elementsInPlayfield
      setBackgroundOfPaneDependingOnContent(pane, row, column);
    }
  }

  // Set Background of pane depending on the content of elementsInPlayfield
  private void setBackgroundOfPaneDependingOnContent(Pane pane, int row, int column) {
    BackgroundFill backgroundFill;
    backgroundFill =
        MethodsForElement.getBackgroundFill(ElementsInPlayfield.getElement(row, column));
    Background background = new Background(backgroundFill);
    pane.setBackground(background);
  }

  @FXML
  void startPlayfieldWindow() {
    System.out.println("startPayfieldWindow");
    rotate360();

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("/PlayfieldWindow.fxml")));
    System.out.println("FxmlLoader" + fxmlLoader);

    try {
      Parent root = fxmlLoader.load();
      System.out.println("Root:" + root);
      Stage stage = new Stage();
      stage.setTitle("Owlngo Playfield");
      Scene scene = new Scene(root, 800, 600);
      // Set up DummyGameForTesting
      DummyGameForTesting.setup();
      // Set up keyHandler
      scene.setOnKeyPressed(
          new EventHandler<>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {

              KeyCode keyCode = event.getCode();
              System.out.println("Tastendruck:" + keyCode);
              DummyGameForTesting.moveOwl(keyCode);
            }
          });

      stage.setScene(scene);
      // stage.setScene(new Scene(root, 800, 600));
      stage.setResizable(true);
      stage.show();
      //     ((Node) event.getSource()).getScene().getWindow().hide();  // hide StartWindow
    } catch (IOException e) {
      System.out.println("Exeption Line 38 " + e);
      e.getCause().getCause();
      System.exit(0);
    }
  }

  @FXML
  void loadElementsInPlayfield() {
    System.out.println("loadElementsInPlayfield");
  }

  @FXML
  void saveElementsInPlayfield() {
    System.out.println("saveElementsInPlayfield");
  }

  void rotate360() {
    RotateTransition rt = new RotateTransition(Duration.millis(1000), gridPaneEditorWindow);
    rt.setByAngle(360);
    rt.setCycleCount(1);
    rt.setAutoReverse(false);
    rt.play();
  }

  void rotate() {
    RotateTransition rt = new RotateTransition(Duration.millis(1000), gridPaneEditorWindow);
    rt.setByAngle(180);
    rt.setCycleCount(1);
    rt.setAutoReverse(true);
    rt.play();
  }
}
