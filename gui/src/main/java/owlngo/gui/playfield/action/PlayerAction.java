package owlngo.gui.playfield.action;

import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import owlngo.game.OwlnGo;
import owlngo.game.level.objects.ObjectInGame;

public final class PlayerAction implements EventHandler<KeyEvent> {

  private final int row;
  private final int column;
  private final OwlnGo game;
  private final Region levelView;
  private final Map<ObjectInGame, Node> individualObjects;

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
    this.individualObjects = individualObjects;
  }

  @Override
  public void handle(KeyEvent event) {
    // TODO: Animation
    System.out.println("Das hier funktioniert!");
    String pressedKey = event.getText();
    if (pressedKey.equals("a") || event.getCode() == KeyCode.LEFT) {
      game.moveLeft();
      System.out.println("Moving left.");
    }
    if (pressedKey.equals("w") || event.getCode() == KeyCode.UP) {
      game.moveJump(false);
      System.out.println("Jumping.");
    }
    if (pressedKey.equals("d") || event.getCode() == KeyCode.RIGHT) {
      game.moveRight();
      System.out.println("Moving right.");
    }
  }
}
