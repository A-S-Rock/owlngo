package owlngo.gui.playfield.action;

import java.util.Map;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import owlngo.game.OwlnGo;
import owlngo.game.level.objects.ObjectInGame;

/** Manages possible keyboard actions for the player view in the GUI. */
public final class PlayerAction implements EventHandler<KeyEvent> {

  private final int row;
  private final int column;
  private final OwlnGo game;
  private final Region levelView;
  private final Map<ObjectInGame, Node> individualObjects;

  /**
   * Creates a specific action for the given parameters.
   *
   * @param row the current row
   * @param column the current column
   * @param game the game instance
   * @param levelView the level layout
   * @param individualObjects possible movable objects in-game
   */
  public PlayerAction(
      int row,
      int column,
      OwlnGo game,
      Region levelView,
      Map<ObjectInGame, Node> individualObjects) {
    this.row = row;
    this.column = column;
    this.game = game;
    this.levelView = levelView;
    this.individualObjects = Map.copyOf(individualObjects);
  }

  @Override
  public void handle(KeyEvent event) {
    // TODO: Animation

    String pressedKey = event.getText();
    if (pressedKey.equals("a")) {
      game.moveBasicLeft();
    }
    if (pressedKey.equals("w")) {
      game.moveBasicUp();
    }
    if (pressedKey.equals("d")) {
      game.moveBasicRight();
    }
    if (pressedKey.equals("e")) {
      new Thread(
              new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                  game.moveJumpRight();
                  return null;
                }
              })
          .start();
    }
    if (pressedKey.equals("q")) {
      game.moveJumpLeft();
    }
  }
}
