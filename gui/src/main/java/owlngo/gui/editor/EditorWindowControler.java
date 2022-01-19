package owlngo.gui.editor;

import java.io.File;
import java.io.IOException;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
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
import javax.swing.JFileChooser;
import owlngo.gui.data.ElementsInPlayfield;
import owlngo.gui.data.MethodsForElement;
import owlngo.gui.playfield.PlayfieldWindowControler;
import owlngo.gui.playfield.ViewUtils;

/**
 * The class handles all actions on the window EditorWindow.fxml The class also sets all enums
 * ElementInPlayfield in the Array ElementsInPlayfield This is done by the keys g,s,e,o. The element
 * is set when the mouse is clicked on the pane
 */
public class EditorWindowControler {
  // 6.1. 14.20
  @FXML GridPane gridPaneEditorWindow;
  /// @FXML GridPane gridPaneChessBoard;
  // Name must be as fx:ID in gridPane in PlayfieldToControlerFirstVersion.fxml
  // fx:id="gridPaneChessBoard"

  @FXML Label displayToUser;
  // Name must be as ID:ID  in Text in PlayfieldToControlerFirstVersion.fxml

  @FXML
  void initialize() {
    System.out.println("initialilize windows EditorWindow.fxml");
    ElementsInPlayfield.setAllToNoElement(); // Define all Elements

    setPanesOnPlayfield();
  }

  /**
   * This method sets panes in the gridpane The method sets the Background of pane depending on the
   * content of elementsInPlayfield (in the beginning no element) The method sete event handlers for
   * each pane[][]. The method called by the event handler is setResetElement.
   */
  private void setPanesOnPlayfield() {
    // For CheckStyle purposes, some values need to be stored locally to shorten lines.
    final int size = MethodsForElement.SIZE;

    // define size of Pane[][]
    Pane[][] pane = new Pane[size][size];

    for (int columnIndex = 0; columnIndex < MethodsForElement.SIZE; columnIndex++) {
      for (int rowIndex = 0; rowIndex < MethodsForElement.SIZE; rowIndex++) {
        // Initialisation
        pane[rowIndex][columnIndex] = new Pane();
        // Set Background of pane depending on the content of elementsInPlayfield
        setBackgroundOfPaneDependingOnContent(pane[rowIndex][columnIndex], rowIndex, columnIndex);
        // Put pane[][] into gridPane of EditorWindow
        gridPaneEditorWindow.add(pane[rowIndex][columnIndex], columnIndex, rowIndex);

        // Set event handler for each pane[][]
        // The called method is setResetElement
        int finalColumnIndex = columnIndex;
        int finalRowIndex = rowIndex;
        pane[rowIndex][columnIndex].setOnMousePressed(
            (evt) ->
                setResetElement(
                    pane[finalRowIndex][finalColumnIndex], finalRowIndex, finalColumnIndex));
      }
    }
  }

  /**
   * This method defines the elements in ElementsInPlayfield by clicking a pane. The kind of element
   * is defined by the letter g, s, e, o (ground, start, end, owl) If one of those elements is
   * clicked it is again set to no element
   */
  private void setResetElement(Pane pane, int row, int column) {
    System.out.println("setResetElement");
    if (ElementsInPlayfield.getElement(row, column)
        == ElementsInPlayfield.ElementInPlayfield.NO_ELEMENT) {
      System.out.print("I am set");
      System.out.print("Key:" + StoreLastKey.getLastKeyPressed() + " ");
      System.out.println(StoreLastKey.getLastKeyPressedAsString());

      if (MethodsForElement.validKey(StoreLastKey.getLastKeyPressedAsString())) {
        // set in elementsInPlayfield the elementInPlayfield depending on the key pressed
        final ElementsInPlayfield.ElementInPlayfield elementInPlayfield =
            (MethodsForElement.getElementInPlayfield(StoreLastKey.getLastKeyPressedAsString()));
        ElementsInPlayfield.setElementTo(elementInPlayfield, row, column);
      }
      // Set Background of pane depending on the content of elementsInPlayfield
      System.out.println("isGround=" + ElementsInPlayfield.isGround(row, column));
      System.out.println("isOwl=" + ElementsInPlayfield.isOwl(row, column));

      setBackgroundOfPaneDependingOnContent(pane, row, column);

    } else {
      System.out.println("I am reset");
      // set in elementsInPlayfield the elementInPlayfield to noElement
      final ElementsInPlayfield.ElementInPlayfield elementInPlayfield =
          ElementsInPlayfield.ElementInPlayfield.NO_ELEMENT;
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

  /**
   * This method starts the PlayfieldWindow() Also an event Handler for the keybourd is started,
   * which calls the
   *
   * <p>elementsInPlayfield (in the beginning no element) The method sete event handlers for each
   * pane[][]. The method called by the event handler is DummyGameForTesting.moveOwl.
   */
  @FXML
  private void startPlayfieldWindow() {
    System.out.println("startPayfieldWindow");
    rotate360();

    ElementsInPlayfield.setLevelForGameDependingOnElementsInPlayfield();

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("/PlayfieldWindow.fxml")));
    System.out.println("FxmlLoader" + fxmlLoader);

    try {
      Parent root = fxmlLoader.load();
      System.out.println("Root:" + root);
      Stage stage = new Stage();
      stage.setTitle("Owlngo Playfield");
      Scene scene = new Scene(root, 800, 600);
      // Set up DummyGameForTesting
      // DummyGameForTesting.setup();
      // Set up keyHandler
      scene.setOnKeyPressed(
          event -> {
            KeyCode keyCode = event.getCode();
            System.out.println("Tastendruck:" + keyCode);
            // DummyGameForTesting.moveOwl(keyCode);
            PlayfieldWindowControler.interpreteKeys(keyCode);
          });

      stage.setScene(scene);
      // stage.setScene(new Scene(root, 800, 600));
      stage.setResizable(true);
      stage.show();
      //     ((Node) event.getSource()).getScene().getWindow().hide();  // hide StartWindow
    } catch (IOException e) {
      System.out.println("Exeption Line 38 " + e);
      Platform.exit();
    }
  }

  @FXML
  void startGameView() {
    System.out.println("loadElementsInPlayfield");
    ElementsInPlayfield.setLevelForGameDependingOnElementsInPlayfield();

    Stage primaryStage = new Stage();
    primaryStage.setTitle("Owlngo");
    ViewUtils.setSceneToGameView(primaryStage);
    primaryStage.setResizable(true);
    primaryStage.show();
  }

  @FXML
  void saveElementsInPlayfield() {
    System.out.println("saveElementsInPlayfield");
    JFileChooser fileChooser = new JFileChooser();
    int valueFileChooser = fileChooser.showSaveDialog(null);
    if (valueFileChooser == JFileChooser.APPROVE_OPTION) {
      File fileName = new File(fileChooser.getSelectedFile().getAbsolutePath());
      System.out.println(fileName);
    }
  }

  @FXML
  void loadElementsInPlayfield() {
    System.out.println("loadElementsInPlayfield");
    JFileChooser fileChooser = new JFileChooser();
    int valueFileChooser = fileChooser.showOpenDialog(null);
    if (valueFileChooser == JFileChooser.APPROVE_OPTION) {
      File fileName = new File(fileChooser.getSelectedFile().getAbsolutePath());
      System.out.println(fileName);
    }
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
