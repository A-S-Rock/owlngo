package owlngo.editor;

import java.io.IOException;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import owlngo.data.ElementsInPlayfield;
import owlngo.data.MethodsForElement;
import owlngo.playfield.DummyGameForTesting;

/** Controller class for the editor window. */
public class EditorWindowControler {

  @FXML GridPane gridPaneEditorWindow;

  @FXML
  void initialize() {
    System.out.println("initialilize windows EditorWindow.fxml");
    ElementsInPlayfield.setAllToNoElement();
    setPanesOnPlayfield();
  }

  void setPanesOnPlayfield() {
    System.out.println("setPanesOnBoard");
    final int row = MethodsForElement.size;
    final int column = MethodsForElement.size;

    Pane[][] pane = new Pane[row][column];
    for (int columnIndex = 0; columnIndex < MethodsForElement.size; columnIndex++) {
      for (int rowIndex = 0; rowIndex < MethodsForElement.size; rowIndex++) {

        // Initialisation
        pane[rowIndex][columnIndex] = new Pane();

        // Set Background of pane depending on the content of elementsInPlayfield
        setBackgroundOfPaneDependingOnContent(pane[rowIndex][columnIndex], rowIndex, columnIndex);

        gridPaneEditorWindow.add(pane[rowIndex][columnIndex], columnIndex, rowIndex);
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
    if (ElementsInPlayfield.getElement(row, column)
        == ElementsInPlayfield.ElementInPlayfield.NO_ELEMENT) {
      System.out.print("I am set");
      System.out.print("Key:" + StoreLastKey.getLastKeyPressed() + " ");
      System.out.println(StoreLastKey.getLastKeyPressedAsString());

      if (MethodsForElement.validKey(StoreLastKey.getLastKeyPressedAsString())) {
        final ElementsInPlayfield.ElementInPlayfield elementInPlayfield =
            (MethodsForElement.getElementInPlayfield(StoreLastKey.getLastKeyPressedAsString()));
        ElementsInPlayfield.setElementTo(elementInPlayfield, row, column);
      }
      // Set Background of pane depending on the content of elementsInPlayfield
      System.out.println("isGround=" + ElementsInPlayfield.isGround(row, column));
      System.out.println("isOwl=" + ElementsInPlayfield.isOwl(row, column));

      setBackgroundOfPaneDependingOnContent(pane, row, column);

    } else {
      System.out.println("I am reset.");
      final ElementsInPlayfield.ElementInPlayfield elementInPlayfield =
          ElementsInPlayfield.ElementInPlayfield.NO_ELEMENT;
      ElementsInPlayfield.setElementTo(elementInPlayfield, row, column);

      setBackgroundOfPaneDependingOnContent(pane, row, column);
    }
  }

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

      DummyGameForTesting.setup();
      scene.setOnKeyPressed(
          event -> {
            KeyCode keyCode = event.getCode();
            System.out.println("Tastendruck:" + keyCode);
            DummyGameForTesting.moveOwl(keyCode);
          });

      stage.setScene(scene);
      stage.setResizable(true);
      stage.show();
    } catch (IOException e) {
      System.out.println("Exeption Line 38 " + e);
      Platform.exit();
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

  /*
  void rotate() {
    RotateTransition rt = new RotateTransition(Duration.millis(1000), gridPaneEditorWindow);
    rt.setByAngle(180);
    rt.setCycleCount(1);
    rt.setAutoReverse(true);
    rt.play();
  }
  */
}
