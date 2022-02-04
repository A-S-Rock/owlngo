package owlngo.gui.gamefield.action;

import java.util.Map;
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

    String pressedKey = event.getText();

    if (pressedKey.equals("a")) {
      new Thread(
              new Task<Void>() {
                @Override
                protected Void call() throws InterruptedException {
                  game.moveLeft();
                  return null;
                }
              })
          .start();
    }
    if (pressedKey.equals("w")) {
      new Thread(
              new Task<Void>() {
                @Override
                protected Void call() throws InterruptedException {
                  game.moveUp();
                  return null;
                }
              })
          .start();
    }
    if (pressedKey.equals("d")) {
      new Thread(
              new Task<Void>() {
                @Override
                protected Void call() throws InterruptedException {
                  game.moveRight();
                  return null;
                }
              })
          .start();
    }
    if (pressedKey.equals("e")) {
      new Thread(
              new Task<Void>() {
                @Override
                protected Void call() throws InterruptedException {
                  game.moveUpRight();
                  return null;
                }
              })
          .start();
    }
    if (pressedKey.equals("q")) {
      new Thread(
              new Task<Void>() {
                @Override
                protected Void call() throws InterruptedException {
                  game.moveUpLeft();
                  return null;
                }
              })
          .start();
    }
    if (pressedKey.equals("f")) {
      new Thread(
              new Task<Void>() {
                @Override
                protected Void call() throws InterruptedException {
                  game.getSideConditions().setInFlightMode();
                  if (!game.getSideConditions().isInFlightMode()) {
                    game.moveContinousFall();
                  }
                  return null;
                }
              })
          .start();
    }
    if (pressedKey.equals("c")) {
      new Thread(
              new Task<Void>() {
                @Override
                protected Void call() throws InterruptedException {
                  game.moveDownRight();
                  return null;
                }
              })
          .start();
    }
    if (pressedKey.equals("x")) {
      new Thread(
              new Task<Void>() {
                @Override
                protected Void call() throws InterruptedException {
                  game.moveDown();
                  return null;
                }
              })
          .start();
    }
    if (pressedKey.equals("y")) {
      new Thread(
              new Task<Void>() {
                @Override
                protected Void call() throws InterruptedException {
                  game.moveDownLeft();
                  return null;
                }
              })
          .start();
    }
  }
}
