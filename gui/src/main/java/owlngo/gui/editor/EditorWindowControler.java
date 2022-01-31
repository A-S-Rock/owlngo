package owlngo.gui.editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
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
import javax.swing.JOptionPane;
import owlngo.gui.controller.ControllerUtils;
import owlngo.gui.data.DataManager;
import owlngo.gui.data.ElementsInPlayfield;
import owlngo.gui.data.ElementsInPlayfield.ElementInPlayfield;
import owlngo.gui.data.MethodsForElement;
import owlngo.gui.playfield.PlayfieldWindowControler;

/** Handles all actions on the editor window. */
public class EditorWindowControler {

  @FXML GridPane gridPaneEditorWindow;

  private static final String PANE_BLACK_BORDER = "-fx-border-color:#CCCCCC; -fx-border-width:1px;";

  @FXML
  void initialize() {
    ElementsInPlayfield.setAllToNoElement(); // Define all Elements
    setPanesOnPlayfield();

    Platform.runLater(
        () ->
            gridPaneEditorWindow
                .getScene()
                .setOnKeyPressed(
                    event -> {
                      KeyCode keyCode = event.getCode();
                      StoreLastKey.setLastKeyPressed(keyCode); // StoreKey in order to get
                      // the last press key asynchonous during mouse click events
                    }));
  }

  /** Starts the Old playfield window GUI. */
  @FXML
  private void startPlayfieldWindow() {
    ElementsInPlayfield.setLevelForGameDependingOnElementsInPlayfield();

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("/PlayfieldWindow.fxml")));

    try {
      Parent root = fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle("Owlngo Playfield");
      Scene scene = new Scene(root, 1200, 800);
      // Set up keyHandler
      scene.setOnKeyPressed(
          event -> {
            KeyCode keyCode = event.getCode();
            PlayfieldWindowControler.interpreteKeys(keyCode);
          });

      stage.setScene(scene);
      stage.setResizable(true);
      stage.show();
      gridPaneEditorWindow.getScene().getWindow().hide();
    } catch (IOException e) {
      System.err.println("Couldn't load playing field window!");
      Platform.exit();
    }
  }

  /** Starts the GameViewScreen. */
  @FXML
  void startGameView() {
    // If owl, start, end is in playfield the game can be started
    if (owlElementInElementsOfPlayfield()
        && startElementInElementsInPlayfield()
        && endElementInElementsOfPlayfield()) {
      ElementsInPlayfield.setLevelForGameDependingOnElementsInPlayfield();
      DataManager.getInstance().setLevelContent(ElementsInPlayfield.getLevel());

      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GameViewScreen.fxml"));
      ControllerUtils.createScene(null, fxmlLoader);
      gridPaneEditorWindow.getScene().getWindow().hide();
    } else {
      JOptionPane.showMessageDialog(null, " Start, End and Owl must be in the playfield.");
    }
  }

  @FXML
  void saveElementsInPlayfield() {
    JFileChooser fileChooser = new JFileChooser();
    int valueFileChooser = fileChooser.showSaveDialog(null);
    if (valueFileChooser == JFileChooser.APPROVE_OPTION) {
      File fileName = new File(fileChooser.getSelectedFile().getAbsolutePath());
      try (PrintWriter printWriter = new PrintWriter(fileName, StandardCharsets.UTF_8)) {
        for (int columnIndex = 0; columnIndex < MethodsForElement.SIZE; columnIndex++) {
          int sum = 0;
          for (int rowIndex = 0; rowIndex < MethodsForElement.SIZE; rowIndex++) {
            int number = ElementsInPlayfield.getElement(rowIndex, columnIndex).ordinal();
            sum = sum + number;
            printWriter.print(number);
            printWriter.print(",");
          }
          printWriter.print(sum);
          printWriter.println();
        }
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        System.err.println("FileNotFoundException in saveElementsInPlayfield()");
      } catch (IOException e) {
        e.printStackTrace();
        System.err.println("IOException in saveElementsInPlayfield()");
      }
    }
  }

  @FXML
  void loadElementsInPlayfield() throws IOException {

    JFileChooser fileChooser = new JFileChooser();
    int valueFileChooser = fileChooser.showOpenDialog(null);
    if (valueFileChooser == JFileChooser.APPROVE_OPTION) {
      File fileName = new File(fileChooser.getSelectedFile().getAbsolutePath());
      if (!errorInFormat(fileName)) {
        int response =
            JOptionPane.showConfirmDialog(null, "Format ok. Confirm that file is loaded");
        if (response == JOptionPane.YES_OPTION) {
          ElementsInPlayfield.setAllToNoElement();
          setElementsInPlayfieldDependingOnFile(fileName);
          setPanesOnPlayfield();
        }
      } else {
        JOptionPane.showMessageDialog(null, "Wrong format");
      }
    }
  }

  @FXML
  void loadWelcomeScreen() throws IOException {
    Stage primaryStage = new Stage();
    FXMLLoader fxmlLoaderWellcome = new FXMLLoader(getClass().getResource("/WelcomeScreen.fxml"));

    Parent root = fxmlLoaderWellcome.load();
    primaryStage.setTitle("Owlngo");
    primaryStage.setScene(new Scene(root));
    primaryStage.setResizable(true);
    primaryStage.show();
    gridPaneEditorWindow.getScene().getWindow().hide();
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
        pane[rowIndex][columnIndex].setStyle(PANE_BLACK_BORDER);
      }
    }
  }

  /**
   * Defines the elements by clicking on them. The kind of element is defined by the letter g, s, e,
   * o (ground, start, end, owl). If one of those elements is clicked, it is set to no element.
   */
  private void setResetElement(Pane pane, int row, int column) {
    if (ElementsInPlayfield.getElement(row, column)
        == ElementsInPlayfield.ElementInPlayfield.NO_ELEMENT) {
      // set

      if (MethodsForElement.validKey(StoreLastKey.getLastKeyPressedAsString())) {
        // set in elementsInPlayfield the elementInPlayfield depending on the key pressed
        final ElementsInPlayfield.ElementInPlayfield elementInPlayfield =
            (MethodsForElement.getElementInPlayfield(StoreLastKey.getLastKeyPressedAsString()));
        ElementsInPlayfield.setElementTo(elementInPlayfield, row, column);
      }
      // Set Background of pane depending on the content of elementsInPlayfield

      setBackgroundOfPaneDependingOnContent(pane, row, column);

    } else {
      // reset
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

  private boolean owlElementInElementsOfPlayfield() {
    int countOwls = 0;

    for (int column = 0; column < MethodsForElement.SIZE; column++) {
      for (int row = 0; row < MethodsForElement.SIZE; row++) {
        if (ElementsInPlayfield.getElement(row, column) == ElementInPlayfield.OWL) {
          countOwls++;
        }
      }
    }

    return countOwls == 1;
  }

  private boolean startElementInElementsInPlayfield() {
    int countStarts = 0;

    for (int column = 0; column < MethodsForElement.SIZE; column++) {
      for (int row = 0; row < MethodsForElement.SIZE; row++) {
        if (ElementsInPlayfield.getElement(row, column) == ElementInPlayfield.START) {
          countStarts++;
        }
      }
    }

    return countStarts == 1;
  }

  private boolean endElementInElementsOfPlayfield() {
    int countEnds = 0;

    for (int column = 0; column < MethodsForElement.SIZE; column++) {
      for (int row = 0; row < MethodsForElement.SIZE; row++) {
        if (ElementsInPlayfield.getElement(row, column) == ElementInPlayfield.END) {
          countEnds++;
        }
      }
    }

    return countEnds == 1;
  }

  boolean errorInFormat(File fileName) throws IOException {
    FileReader fileReader = new FileReader(fileName, StandardCharsets.UTF_8);
    BufferedReader bufferedReader = new BufferedReader(fileReader);
    boolean wrongFormat = false;
    for (int columnIndex = 0; columnIndex < MethodsForElement.SIZE; columnIndex++) {
      String lineInput = bufferedReader.readLine();

      if (lineInput == null) {
        wrongFormat = true;
        break;
      }
      String[] partOfline = lineInput.split(",");
      int sum = 0;
      for (int rowIndex = 0; rowIndex < MethodsForElement.SIZE; rowIndex++) {
        // the string should not be empty
        if (noNumber(partOfline[rowIndex])) {
          wrongFormat = true;
          break;
        }
        // calculate sum
        int number = Integer.parseInt(partOfline[rowIndex]);
        sum = sum + number;
      }
      if (wrongFormat) {
        break;
      }
      String checkSum = partOfline[partOfline.length - 1];
      if (noNumber(checkSum)) {
        wrongFormat = true;
        break;
      }
      // test check sum
      if (sum != Integer.parseInt(checkSum)) {
        wrongFormat = true;
        break;
      }
    }
    fileReader.close();
    bufferedReader.close();
    return wrongFormat;
  }

  boolean noNumber(String eingabe) {
    // the string should not be empty
    if (eingabe.length() == 0) {
      return true;
    }
    // is only allowed to contain numbers
    for (int x = 0; x < eingabe.length(); x++) {
      String letter = eingabe.substring(x, x + 1);
      if (!letter.matches("[0-9]")) {
        return true;
      }
    }
    return false;
  }

  void setElementsInPlayfieldDependingOnFile(File fileName) throws IOException {
    final ElementInPlayfield[] elementArray = ElementInPlayfield.values();

    FileReader fileReader = new FileReader(fileName, StandardCharsets.UTF_8);
    BufferedReader bufferedReader = new BufferedReader(fileReader);
    for (int columnIndex = 0; columnIndex < MethodsForElement.SIZE; columnIndex++) {
      String lineInput = bufferedReader.readLine();
      if (lineInput == null) {
        break;
      }
      String[] partOfline = lineInput.split(",");
      for (int rowIndex = 0; rowIndex < MethodsForElement.SIZE; rowIndex++) {
        int number = Integer.parseInt(partOfline[rowIndex]);
        ElementsInPlayfield.setElementTo(elementArray[number], rowIndex, columnIndex);
      }
    }
    fileReader.close();
    bufferedReader.close();
  }
}