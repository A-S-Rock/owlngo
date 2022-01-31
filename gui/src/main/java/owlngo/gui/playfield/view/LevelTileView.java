package owlngo.gui.playfield.view;

import javafx.scene.layout.StackPane;
import owlngo.game.GameState;
import owlngo.game.OwlnGo;
import owlngo.game.level.Coordinate;
import owlngo.game.level.objects.ObjectInGame;
import owlngo.game.level.objects.ObjectInGame.ObjectType;

/** Represents the view for an in-game object. */
public class LevelTileView extends StackPane {

  static final double TILE_WIDTH = 40;
  static final double TILE_HEIGHT = 40;

  public LevelTileView(int row, int column, OwlnGo game) {
    fillTile(row, column, game);
  }

  private void fillTile(int tileRow, int tileColumn, OwlnGo game) {
    GameState gameState = game.getGameState();

    getChildren().clear();
    getChildren().add(new AirView());

    ObjectInGame object =
        gameState.getLevel().getObjectInGameAt(Coordinate.of(tileRow, tileColumn));

    if (object.getType() == ObjectType.GROUND) {
      getChildren().clear();
      getChildren().add(new GroundView());
    } else if (object.getType() == ObjectType.START) {
      getChildren().add(new StartView());
    } else if (object.getType() == ObjectType.FINISH) {
      getChildren().add(new FinishView());
    } else if (object.getType() == ObjectType.FIRE) {
      getChildren().add(new FireView());
    } else if (object.getType() == ObjectType.FOOD) {
      getChildren().add(new FoodView());
    }
  }
}
