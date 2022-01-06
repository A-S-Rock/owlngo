package owlngo.gui.playfield;

import java.util.Map;
import java.util.Objects;
import javafx.beans.property.MapProperty;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import owlngo.game.OwlnGo;
import owlngo.game.level.Level;
import owlngo.game.level.objects.ObjectInGame;

public class GameView extends HBox {

  private OwlnGo game = new OwlnGo();
  static final int TILE_SIZE = 40;

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

        final ObjectInGame objectInGame = rowProperty.get(currentRow);
        if (objectInGame.getType().equals("GROUND")) {
          tileContent.getChildren().add(new GroundView());
        }

        final int finalCurrentColumn = currentColumn;
        rowProperty.addListener(
            (observable, oldValue, newValue) -> {
              tileContent.getChildren().clear();
              /* ToDo: Remove this Reminder of what Objects exist ingame -> NONE, PLAYER, START,
                  FINISH, AIR, GROUND */
              if (objectInGame.getType().equals("GROUND")) {
                tileContent.getChildren().add(new GroundView());
              }
              if (objectInGame.getType().equals("PLAYER")) {
                /* ToDo: Do something with the Player, probably the same as with all the other
                    objects -> show them */
              }
            });

        levelView.getChildren().add(tileContent);
      }
    }

    return levelView;
  }

  private Node createSidePanel(OwlnGo game) {
    return new VBox();
  }
}
