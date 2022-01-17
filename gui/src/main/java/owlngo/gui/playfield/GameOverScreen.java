package owlngo.gui.playfield;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameOverScreen extends VBox {

  public GameOverScreen(Stage primaryStage) {
    setAlignment(Pos.TOP_CENTER);
    setSpacing(40);
    primaryStage.setTitle("Game over screen");
    getChildren().addAll(createGameOverText());
  }

  private Node createGameOverText() {
    Text gameOverText = new Text("Game over!");
    gameOverText.setStyle("-fx-font: 72 arial;");
    return gameOverText;
  }
}
