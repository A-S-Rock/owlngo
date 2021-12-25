package owlngo.editor;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.FileNotFoundException;

public class EditorWindowControler {

  // Constant values for window
  static final int numberOfPanesInRowAnColumn = 30;
  // private static Boolean [][] isFloor=new
  // Boolean[numberOfPanesInRowAnColumn][numberOfPanesInRowAnColumn];

  @FXML GridPane gridPaneChessBoard;
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
    Pane[][] pane = new Pane[numberOfPanesInRowAnColumn][numberOfPanesInRowAnColumn];
    // floor.setAllToFalse();
    for (int columnIndex = 0; columnIndex < numberOfPanesInRowAnColumn; columnIndex++) {
      for (int rowIndex = 0; rowIndex < numberOfPanesInRowAnColumn; rowIndex++) {

        // Initialisation
        pane[rowIndex][columnIndex] = new Pane();

        // Set Background of pane depending on the content of elementsInPlayfield
        setBackgroundOfPaneDependingOnContent(pane[rowIndex][columnIndex], rowIndex, columnIndex);

        gridPaneChessBoard.add(pane[rowIndex][columnIndex], columnIndex, rowIndex);
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
  void restartGame() {
    rotate360();
  }

  void rotate360() {
    RotateTransition rt = new RotateTransition(Duration.millis(1000), gridPaneChessBoard);
    rt.setByAngle(360);
    rt.setCycleCount(1);
    rt.setAutoReverse(false);
    rt.play();
  }

  void rotate() {
    RotateTransition rt = new RotateTransition(Duration.millis(1000), gridPaneChessBoard);
    rt.setByAngle(180);
    rt.setCycleCount(1);
    rt.setAutoReverse(true);
    rt.play();
  }

  /*
  void showPossibleMoves(int pamNumber) {
    System.out.println("showPossibleMoves");
    List<Coordinate> coordinates = new ArrayList<>();
    coordinates = game.getGameState().getSelectedPiece().getPossibleMoveCoordinates();
    int moveNumber = 0;

    Circle[] circles = new Circle[maximumNumberOfPossibleMoves];
    for (Coordinate coordinate : coordinates) {
      int row = (coordinate.getRow());
      int column = (coordinate.getColumn());
      circles[moveNumber] = new Circle(0, 0, radiusOfMarker, markerColour);
      addMarkersToChessBoard(circles[moveNumber], column, row);
      int finalMoveNumber = moveNumber;
      circles[moveNumber].setOnMouseClicked((evt) -> {
        movePam(circles[finalMoveNumber], finalMoveNumber);
      });
      moveNumber++;
    }
  }
  */

  /*
  void movePam(Circle circle, int moveNumber) {
    System.out.println("movePam");
    // System.out.println("Go to position " + moveNumber);
    removeMarkers();
    game.move(moveNumber);
    removeAllCircles();
    if (game.getGameState().isGameRunning()) {
      setPawnsOnBoard();
    } else {
      // win, loose, equal
      displayToUser.setText(game.getGameState().getStatus().toString());
    }

  }
  */

  /*
  Boolean anyClickedCircle() {
    System.out.println("anyClickedCircle");
    Boolean out = false;
    List<Node> elementsInGridPaneChessBoard = new ArrayList<>();
    elementsInGridPaneChessBoard = gridPaneChessBoard.getChildren();
    for (Node node : elementsInGridPaneChessBoard) {
      if (node.getTypeSelector().matches("Circle")) {
        Circle circle = (Circle) node;
        double radius = circle.getRadius();
        if (radius == radiusOfClickedPan) {
          return true;   //    True
        }
      }
    }
    return false;   // False
  }
  */

  /*
  // This method deletes all markers. The markers show the possible steps
  void removeMarkers() {
    System.out.println("removeMarkers");
    // System.out.println(gridPaneChessBoard.getChildren());
    List<Node> elementsInGridPaneChessBoard = new ArrayList<>();
    elementsInGridPaneChessBoard = gridPaneChessBoard.getChildren();
    List<Integer> toBeDeleted = new ArrayList<>();
    int positionInListOfChildren = 0;
    for (Node node : elementsInGridPaneChessBoard) {
      if (node.getTypeSelector().matches("Circle")) {
        // System.out.print(node.getTypeSelector());
        Circle circle = (Circle) node;
        double radius = circle.getRadius();
        // System.out.print(radius);
        if (radius == radiusOfMarker) {
          toBeDeleted.add(positionInListOfChildren);
          // System.out.print(" " + positionInListOfChildren);
        }
      }
      // System.out.println();
      positionInListOfChildren++;
    }
    // System.out.println(toBeDeleted+" in the list of childen");
    for (int x = toBeDeleted.size() - 1; x >= 0; x = x - 1) {
      // System.out.println(toBeDeleted.get(x));
      gridPaneChessBoard.getChildren().remove((int) toBeDeleted.get(x));  // Delete all in the list
    }
  }
  */

  /*
  void removeAllCircles() {
    System.out.println("removeAllCircles");
    List<Node> elementsInGridPaneChessBoard = new ArrayList<>();
    elementsInGridPaneChessBoard = gridPaneChessBoard.getChildren();
    List<Integer> toBeDeleted = new ArrayList<>();
    int positionInListOfChildren = 0;
    for (Node node : elementsInGridPaneChessBoard) {
      if (node.getTypeSelector().matches("Circle")) {
        toBeDeleted.add(positionInListOfChildren);
      }
      positionInListOfChildren++;
    }
    // System.out.println(toBeDeleted+" in the list of childen");
    for (int x = toBeDeleted.size() - 1; x >= 0; x = x - 1) {
      // System.out.println(toBeDeleted.get(x));
      gridPaneChessBoard.getChildren().remove((int) toBeDeleted.get(x));  // Delete all in the list
    }
  }
  */
  /*
  void addCircleToChessBoard(Circle circle, int columnIndex, int rowIndex) {
    System.out.println("addCircleToChessBoard" + "col:" + columnIndex + "row:" + rowIndex);
    gridPaneChessBoard.add(circle, columnIndex, rowIndex);
    gridPaneChessBoard.setHalignment(circle, HPos.CENTER);
  }
  */
  /*
  void addMarkersToChessBoard(Circle circle, int columnIndex, int rowIndex) {
    System.out.println("addMarkersToChessBoard" + "col:" + columnIndex + "row:" + rowIndex);
    //System.out.println(circle);
    gridPaneChessBoard.add(circle, columnIndex, rowIndex);
    gridPaneChessBoard.setHalignment(circle, HPos.CENTER);
  }
  */

  // @FXML
  // Button button;

  /*
  void testOfGame() {
    // test game

    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        Coordinate coordinate = Coordinate.of(x, y);
        System.out.print(" " + game.getGameState().getChessBoard().getPieceAt(coordinate).getId() +
            game.getGameState().getChessBoard().getPieceAt(coordinate).getColor());
      }
      System.out.println();
    }
    // Test of game interaction

    game.selectPieceById(5);

    List<Coordinate> coordinates = new ArrayList<>();
    coordinates = game.getGameState().getSelectedPiece().getPossibleMoveCoordinates();

    for (Coordinate coordinate : coordinates) {
      System.out.print(coordinate.getRow());
      System.out.println(coordinate.getColumn());
      System.out.println();
    }


    System.out.println(game.getGameState().getSelectedPiece().getPossibleMoveCoordinates());
    game.move(1);
    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        Coordinate coordinate = Coordinate.of(x, y);
        // System.out.println("Coordinate"+coordinate);
        System.out.print(" " + game.getGameState().getChessBoard().getPieceAt(coordinate).getId() +
            game.getGameState().getChessBoard().getPieceAt(coordinate).getColor());
      }
      System.out.println();
    }
  }
   */
}
