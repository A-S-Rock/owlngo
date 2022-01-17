package owlngo.gui.playfield;

import java.util.Scanner;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WelcomeScreen extends VBox {


  public WelcomeScreen(Stage primaryStage) {
    setAlignment(Pos.TOP_CENTER);
    setSpacing(40);
    primaryStage.setTitle("WelcomeScreen");
    getChildren().addAll(createTextLabel(), ViewUtils.createRandomGameButton(primaryStage));
  }


  private Node createTextLabel() {
    Node welcomeText = new Text("Welcome to OwlnGo");
    welcomeText.setStyle("-fx-font: 72 arial;");
    return welcomeText;
  }



}
