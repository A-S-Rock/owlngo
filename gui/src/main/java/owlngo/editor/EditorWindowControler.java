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
  public void initialize()  {
    System.out.println("initialilize windows EditorWindow.fxml");
    setPanesOnPlayfield();
  }
  // void setPanesOnBoard() throws FileNotFoundException
  void setPanesOnPlayfield() {
    System.out.println("setPanesOnBoard");
    Floor floor = new Floor();
    Pane[][] pane = new Pane[numberOfPanesInRowAnColumn][numberOfPanesInRowAnColumn];
    floor.setAllToFalse();
    for (int columnIndex = 0; columnIndex < numberOfPanesInRowAnColumn; columnIndex++) {
      for (int rowIndex = 0; rowIndex < numberOfPanesInRowAnColumn; rowIndex++) {
        Pane pane1 = new Pane();
        // white background

        BackgroundFill backgroundFill;
        // backgroundFill= new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);

        System.out.println("ElementInPlayfield"+MethodsForElement.getElementInPlayfield("o"));

        // System.out.println(MethodsForElement.getBackgroundFill(ElementInPlayfield.START));
        backgroundFill=MethodsForElement.getBackgroundFill(ElementInPlayfield.OWL);
        /*
        // background with little pictures
        Image image= new Image(new FileInputStream("C:\\ingo40x40.png"));
        ImagePattern imagePattern=new ImagePattern(image);
        BackgroundFill backgroundFill = new BackgroundFill(imagePattern,CornerRadii.EMPTY,Insets.EMPTY);
        */

        Background background = new Background(backgroundFill);
        pane1.setBackground(background);

        pane[rowIndex][columnIndex] = pane1;

        gridPaneChessBoard.add(pane[rowIndex][columnIndex], columnIndex, rowIndex);
        int finalColumnIndex = columnIndex;
        int finalRowIndex = rowIndex;
        pane[rowIndex][columnIndex].setOnMousePressed(
            (evt) ->
                setResetElement(
                    pane[finalRowIndex][finalColumnIndex], floor, finalRowIndex, finalColumnIndex));
      }
    }
  }


  void setResetElement(Pane pane, Floor floor, int row, int column) {
    System.out.println("selectDeselectPawn");
    if (!floor.getFloor(row, column)) {
      System.out.println("I am selected");
      System.out.println("Key:" + StoreLastKey.getLastKeyPressed());

      floor.setFloorToTrue(row, column);
      BackgroundFill backgroundFill =
          new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY);
      Background background = new Background(backgroundFill);
      pane.setBackground(background);
    } else {
      System.out.println("I am deselected as floor");
      floor.setFloorToFalse(row, column);
      BackgroundFill backgroundFill =
          new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
      Background background = new Background(backgroundFill);
      pane.setBackground(background);
    }
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
