package owlngo.game;

import com.google.common.truth.Truth;
import java.util.List;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import owlngo.game.GameState.GameStatus;
import owlngo.game.level.Coordinate;
import owlngo.game.level.Level;
import owlngo.game.level.objects.ObjectInGame;
import owlngo.game.level.objects.ObjectInGame.ObjectType;

/** Tests the basic behaviour for {@link OwlnGo} games. */
public class OwlnGoBasicTest {

  private static final int NUM_ROWS = 10;
  private static final int NUM_COLUMNS = 10;
  private static final int NUM_OBJECTS = NUM_ROWS * NUM_COLUMNS; // player is extra

  /** This panel initializer is needed to run tests involved with <code>Platform#runlater</code>. */
  //private final JFXPanel tempPaner = new JFXPanel();

  @Test
  public void testGameDimensions() {
    final OwlnGo game = new OwlnGo(NUM_ROWS, NUM_COLUMNS);
    final int rows = game.getGameState().getLevel().getNumRows();
    final int columns = game.getGameState().getLevel().getNumColumns();

    final List<ObjectInGame> objectsInGame =
        game.getGameState().getLevel().getListOfObjectsInGame();
    final int numberOfObjectsInGame = objectsInGame.size();

    Truth.assertThat(rows).isEqualTo(NUM_ROWS);
    Truth.assertThat(columns).isEqualTo(NUM_COLUMNS);
    Truth.assertThat(numberOfObjectsInGame).isEqualTo(NUM_OBJECTS);
  }

  @Test
  public void testFactoryMethod_WithNewPlayerAt() {
    final OwlnGo game = new OwlnGo(NUM_ROWS, NUM_COLUMNS);
    final Level level = game.getGameState().getLevel();
    final Coordinate arbitraryCoordinate = Coordinate.of(5, 5);

    checkIfObjectInGameOnCoordinateHasType(level, arbitraryCoordinate, ObjectType.PLAYER);
  }

  @Test
  public void testFactoryMethod_WithStartAt() {
    final OwlnGo game = new OwlnGo(NUM_ROWS, NUM_COLUMNS);
    final Level level = game.getGameState().getLevel();
    final Coordinate arbitraryCoordinate = Coordinate.of(5, 5);

    checkIfObjectInGameOnCoordinateHasType(level, arbitraryCoordinate, ObjectType.START);
  }

  @Test
  public void testFactoryMethod_WithFinishAt() {
    final OwlnGo game = new OwlnGo(NUM_ROWS, NUM_COLUMNS);
    final Level level = game.getGameState().getLevel();
    final Coordinate arbitraryCoordinate = Coordinate.of(5, 5);

    checkIfObjectInGameOnCoordinateHasType(level, arbitraryCoordinate, ObjectType.FINISH);
  }

  @Test
  public void testFactoryMethod_WithGroundAt() {
    final OwlnGo game = new OwlnGo(NUM_ROWS, NUM_COLUMNS);
    final Level level = game.getGameState().getLevel();
    final Coordinate arbitraryCoordinate = Coordinate.of(5, 5);

    checkIfObjectInGameOnCoordinateHasType(level, arbitraryCoordinate, ObjectType.GROUND);
  }

  @Test
  public void testFactoryMethod_WithFoodAt() {
    final OwlnGo game = new OwlnGo(NUM_ROWS, NUM_COLUMNS);
    final Level level = game.getGameState().getLevel();
    final Coordinate arbitraryCoordinate = Coordinate.of(5, 5);

    checkIfObjectInGameOnCoordinateHasType(level, arbitraryCoordinate, ObjectType.FOOD);
  }

  @Test
  public void testFactoryMethod_WithFireAt() {
    final OwlnGo game = new OwlnGo(NUM_ROWS, NUM_COLUMNS);
    final Level level = game.getGameState().getLevel();
    final Coordinate arbitraryCoordinate = Coordinate.of(5, 5);

    checkIfObjectInGameOnCoordinateHasType(level, arbitraryCoordinate, ObjectType.FIRE);
  }

  @Test
  public void testFactoryMethod_WithAirAt() {
    final OwlnGo game = new OwlnGo(NUM_ROWS, NUM_COLUMNS);
    final Level level = game.getGameState().getLevel();
    // Last row is filled with some grounds, these should not be air tiles in this level.
    final Coordinate arbitraryCoordinate = Coordinate.of(NUM_COLUMNS - 1, 0);

    checkIfObjectInGameOnCoordinateHasType(level, arbitraryCoordinate, ObjectType.AIR);
  }

  private void checkIfObjectInGameOnCoordinateHasType(
      Level level, Coordinate coordinate, ObjectType type) {
    // Get object in game at an arbitrary Coordinate like (5, 5) and check if it is not the desired
    // object type.
    final ObjectInGame arbitraryObject = level.getObjectInGameAt(coordinate);
    Truth.assertThat(arbitraryObject.getType()).isNotEqualTo(type);

    // Set object at this exact Coordinate and check again.
    Level newLevel;

    switch (type) {
      case START -> newLevel = level.withStartAt(coordinate);
      case FINISH -> newLevel = level.withFinishAt(coordinate);
      case GROUND -> newLevel = level.withGroundAt(coordinate);
      case PLAYER -> newLevel = level.withNewPlayerAt(coordinate);
      case FIRE -> newLevel = level.withFireAt(coordinate);
      case FOOD -> newLevel = level.withFoodAt(coordinate);
      case AIR -> newLevel = level.withAirAt(coordinate);
      default -> throw new IllegalStateException(
          "Unexpected value: " + type); // NONE not present in game
    }

    final ObjectInGame newObjectInGame = newLevel.getObjectInGameAt(coordinate);
    Truth.assertThat(newObjectInGame.getType()).isEqualTo(type);
  }

  @Test
  public void testWinCondition() {
    final OwlnGo game = new OwlnGo(NUM_ROWS, NUM_COLUMNS);
    final GameStatus status = game.getGameState().getStatus();
    Truth.assertThat(status).isEqualTo(GameStatus.ONGOING);

    final Level level = game.getGameState().getLevel();
    final Coordinate finishCoordinate = level.getCopyOfFinishObject().getCoordinate();
    final Coordinate coordinateLeftOfFinish =
        Coordinate.of(finishCoordinate.getRow(), finishCoordinate.getColumn() - 1);

    final Level levelWithPlayerInFrontOfFinish = level.withNewPlayerAt(coordinateLeftOfFinish);

    final OwlnGo gameWithPlayerInFrontOfFinish = new OwlnGo(levelWithPlayerInFrontOfFinish);
    try {
      gameWithPlayerInFrontOfFinish.moveRight();
    } catch (InterruptedException e) {
      Assertions.fail();
    }

    final GameStatus newStatus = gameWithPlayerInFrontOfFinish.getGameState().getStatus();
    Truth.assertThat(newStatus).isEqualTo(GameStatus.WIN);
  }

  @Test
  public void testGiveUp() {
    final OwlnGo game = new OwlnGo(NUM_ROWS, NUM_COLUMNS);
    final GameStatus status = game.getGameState().getStatus();
    Truth.assertThat(status).isEqualTo(GameStatus.ONGOING);

    game.giveUp();
    final GameStatus newStatus = game.getGameState().getStatus();
    Truth.assertThat(newStatus).isEqualTo(GameStatus.GIVE_UP);
  }

  @Test
  public void testLoseConditionWithFalling() {
    final OwlnGo game = new OwlnGo(NUM_ROWS, NUM_COLUMNS);
    final GameStatus status = game.getGameState().getStatus();
    Truth.assertThat(status).isEqualTo(GameStatus.ONGOING);

    // Going three moves to the right in this level causes the player to fall.
    for (int repititions = 0; repititions < 3; repititions++) {
      try {
        game.moveRight();
      } catch (InterruptedException e) {
        Assertions.fail();
      }
    }

    final GameStatus newStatus = game.getGameState().getStatus();
    Truth.assertThat(newStatus).isEqualTo(GameStatus.LOSE);
  }

  @Test
  public void testLoseConditionWithFire() {
    final OwlnGo game = new OwlnGo(NUM_ROWS, NUM_COLUMNS);
    final GameStatus status = game.getGameState().getStatus();
    Truth.assertThat(status).isEqualTo(GameStatus.ONGOING);

    // Create fire in front of player and move right into it.
    final Level level = game.getGameState().getLevel();
    final Coordinate playerCoordinate = level.getCopyOfPlayer().getCoordinate();
    final Coordinate fireCoordinate =
        Coordinate.of(playerCoordinate.getRow(), playerCoordinate.getColumn() + 1);

    final Level levelWithFireInFrontOfPlayer = level.withFireAt(fireCoordinate);

    final OwlnGo gameWithFireInFrontOfPlayer = new OwlnGo(levelWithFireInFrontOfPlayer);

    try {
      gameWithFireInFrontOfPlayer.moveRight();
      Platform.runLater(
          () -> {
            final GameStatus newStatus = game.getGameState().getStatus();
            Truth.assertThat(newStatus).isEqualTo(GameStatus.LOSE);
          });
    } catch (InterruptedException e) {
      Assertions.fail();
    }
  }

  @Test
  public void testLoseConditionWithExhaustion() {
    final OwlnGo game = new OwlnGo(NUM_ROWS, NUM_COLUMNS);
    final GameStatus status = game.getGameState().getStatus();
    Truth.assertThat(status).isEqualTo(GameStatus.ONGOING);

    // Get endurance of player and deplete endurance completely in flight mode.
    final int endurance = game.getMaxEndurance();
    game.getSideConditions().setInFlightMode();

    for (int steps = 0; steps < endurance - 1; steps++) {
      try {
        game.moveLeft();
        game.moveRight();
      } catch (InterruptedException e) {
        Assertions.fail();
      }
    }

    // Check if after exact endurance - 1 steps the game is still running.

    Platform.runLater(
        () -> {
          final GameStatus newStatusWithEnduranceLeft = game.getGameState().getStatus();
          Truth.assertThat(newStatusWithEnduranceLeft).isEqualTo(GameStatus.ONGOING);

          try {
            game.moveRight();
          } catch (InterruptedException e) {
            Assertions.fail();
          }
        });

    // Now run one more move - the game should be lost.

    Platform.runLater(
        () -> {
          final GameStatus newStatusWithDepletedEndurance = game.getGameState().getStatus();
          Truth.assertThat(newStatusWithDepletedEndurance).isEqualTo(GameStatus.LOSE);
        });
  }
}
