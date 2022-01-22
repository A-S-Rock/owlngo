package owlngo.gui.editor;

import java.io.File;
import java.io.IOException;
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
import javax.swing.JFileChooser;
import owlngo.gui.data.ElementsInPlayfield;
import owlngo.gui.data.MethodsForElement;
import owlngo.gui.playfield.PlayfieldWindowControler;
import owlngo.gui.playfield.view.ViewUtils;

/** Handles all actions on the editor window. */
public class EditorWindowControler {

  @FXML GridPane gridPaneEditorWindow;

  @FXML
  void initialize() {
    System.out.println("initialilize windows EditorWindow.fxml");
    ElementsInPlayfield.setAllToNoElement(); // Define all Elements

    setPanesOnPlayfield();
  }

  /**
   * Sets the background depending on the elements in the playing field (in the beginning no
   * elements).
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
   * Defines the elements by clicking on them. The kind of element is defined by the letter g, s, e,
   * o (ground, start, end, owl). If one of those elements is clicked, it is set to no element.
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

  private void setBackgroundOfPaneDependingOnContent(Pane pane, int row, int column) {
    BackgroundFill backgroundFill;
    backgroundFill =
        MethodsForElement.getBackgroundFill(ElementsInPlayfield.getElement(row, column));
    Background background = new Background(backgroundFill);
    pane.setBackground(background);
  }

  /** Starts the playfield window GUI. */
  @FXML
  private void startPlayfieldWindow() {
    ElementsInPlayfield.setLevelForGameDependingOnElementsInPlayfield();

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("/PlayfieldWindow.fxml")));

    try {
      Parent root = fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle("Owlngo Playfield");
      Scene scene = new Scene(root, 800, 600);
      // Set up keyHandler
      scene.setOnKeyPressed(
          event -> {
            KeyCode keyCode = event.getCode();
            PlayfieldWindowControler.interpreteKeys(keyCode);
          });

      stage.setScene(scene);
      stage.setResizable(true);
      stage.show();
    } catch (IOException e) {
      System.err.println("Couldn't load playing field window!");
      Platform.exit();
    }
  }

  @FXML
  void startGameView() {
    ElementsInPlayfield.setLevelForGameDependingOnElementsInPlayfield();

    Stage primaryStage = new Stage();
    primaryStage.setTitle("Owlngo");
    ViewUtils.setSceneToGameViewWithLevel(primaryStage, ElementsInPlayfield.getLevel());
    primaryStage.setResizable(true);
    primaryStage.show();
  }

  @FXML
  void saveElementsInPlayfield() {
    JFileChooser fileChooser = new JFileChooser();
    int valueFileChooser = fileChooser.showSaveDialog(null);
    if (valueFileChooser == JFileChooser.APPROVE_OPTION) {
      File fileName = new File(fileChooser.getSelectedFile().getAbsolutePath());
      System.out.println(fileName);
    }
  }

  @FXML
  void loadElementsInPlayfield() {
    JFileChooser fileChooser = new JFileChooser();
    int valueFileChooser = fileChooser.showOpenDialog(null);
    if (valueFileChooser == JFileChooser.APPROVE_OPTION) {
      File fileName = new File(fileChooser.getSelectedFile().getAbsolutePath());
      System.out.println(fileName);
    }
  }
}
