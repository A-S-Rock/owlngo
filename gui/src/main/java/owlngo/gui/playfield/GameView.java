package owlngo.gui.playfield;

import java.util.Map;
import java.util.Objects;
import javafx.beans.property.MapProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import owlngo.game.OwlnGo;
import owlngo.game.level.Level;
import owlngo.game.level.objects.ObjectInGame;
import owlngo.game.level.objects.ObjectInGame.ObjectType;

/**
 * Class shows our playingfield on the left side and a information field with buttons, etc. on the
 * right side.
 */
public class GameView extends HBox {

  private final OwlnGo game = new OwlnGo();
  static final int TILE_SIZE = 40;

  /** Constructor without a parameter. */
  public GameView() {
    getChildren().addAll(createLevelView(game), createSidePanel(game));
  }

  /**
   * Constructor that uses a given game.
   *
   * @param game is a given owlngo game
   */
  public GameView(OwlnGo game) {
    getChildren().addAll(createLevelView(game), createSidePanel(game));
  }

  private Node createLevelView(OwlnGo game) {
    TilePane levelView = new TilePane();

    Level level = game.getGameState().getLevel();
    final int rows = level.getNumRows();
    final int cols = level.getNumColumns();

    levelView.setPrefRows(rows);
    levelView.setPrefColumns(cols);

    Map<Integer, MapProperty<Integer, ObjectInGame>> levelProperties = level.propertyLevelLayout();

    for (int currentRow = 0; currentRow < rows; currentRow++) {
      MapProperty<Integer, ObjectInGame> rowProperty =
          Objects.requireNonNull(levelProperties.get(currentRow));

      for (int currentColumn = 0; currentColumn < cols; currentColumn++) {
        StackPane tileContent = new StackPane();

        final ObjectInGame objectInGame = rowProperty.get(currentColumn);
        if (objectInGame.getType().equals(ObjectType.GROUND)) {
          tileContent.getChildren().add(new GroundView());
        } else if (objectInGame.getType().equals(ObjectType.PLAYER)) {
          tileContent.getChildren().add(new AirView());
          tileContent.getChildren().add(new PlayerView());
        } else if (objectInGame.getType().equals(ObjectType.AIR)) {
          tileContent.getChildren().add(new AirView());
        } else if (objectInGame.getType().equals(ObjectType.START)) {
          tileContent.getChildren().add(new AirView());
          tileContent.getChildren().add(new StartView());
        } else if (objectInGame.getType().equals(ObjectType.FINISH)) {
          tileContent.getChildren().add(new AirView());
          tileContent.getChildren().add(new FinishView());
        } else {
          throw new AssertionError("Object type does not exist.");
        }

        rowProperty.addListener(
            (observable, oldValue, newValue) -> {
              tileContent.getChildren().clear();
              if (objectInGame.getType().equals(ObjectType.PLAYER)) {
                tileContent.getChildren().add(new AirView());
                tileContent.getChildren().add(new PlayerView());
              }
            });

        levelView.getChildren().add(tileContent);
      }
    }

    return levelView;
  }

  private Node createSidePanel(OwlnGo game) {

    return new VBox(new Button("Test Button"));
  }
}
