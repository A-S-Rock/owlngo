package owlngo.gui.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import owlngo.communication.Connection;
import owlngo.communication.messages.UpdateLevelStatsRequest;
import owlngo.game.GameState.GameStatus;
import owlngo.game.OwlnGo;
import owlngo.gui.data.CommunicationManager;
import owlngo.gui.data.DataManager;
import owlngo.gui.gamefield.view.GameView;
import owlngo.gui.gamefield.view.ViewUtils;

/** Contoller class for GameViewScreen.fxml. */
public class GameViewScreenController {

  private final OwlnGo game;
  private final DataManager dataManager = DataManager.getInstance();
  private final CommunicationManager communicationManager = CommunicationManager.getInstance();
  private final Connection connection = communicationManager.getConnection();

  @FXML Button backToMainMenuButton;
  @FXML Button giveUpButton;
  @FXML AnchorPane gamePane;
  @FXML ProgressBar enduranceBar;
  @FXML Label timerLabel;

  /** Allows the controller to load a different level if possible. */
  public GameViewScreenController() {
    game = new OwlnGo(dataManager.getLevelContent());
  }

  @FXML
  void initialize() {
    gamePane.getChildren().addAll(createGameNode(game));

    final LevelTimer levelTimer = new LevelTimer();
    levelTimer.startTimer(0); // start at 0 seconds
    timerLabel.textProperty().bind(levelTimer.timeStringProperty());

    game.getGameState()
        .propertyStatus()
        .addListener(
            ((observable, oldValue, newValue) -> {
              levelTimer.stopTimer();

              final String levelName = dataManager.getLevelNameContent();
              final String username = communicationManager.getUsername();
              boolean hasWon;
              final String currentTime = levelTimer.timeStringProperty.getValue().substring(6);

              if (newValue == GameStatus.WIN) {
                hasWon = true;
                dataManager.setTimeStringProperty(levelTimer.timeStringProperty());
              } else {
                hasWon = false;
              }

              final UpdateLevelStatsRequest updateStatsRequest =
                  new UpdateLevelStatsRequest(levelName, hasWon, currentTime, username);
              connection.write(updateStatsRequest);
            }));

    backToMainMenuButton.setOnAction(
        new EventHandler<>() {
          @Override
          public void handle(ActionEvent event) {
            game.giveUp(); // halts the game
            FXMLLoader fxmlLoader =
                new FXMLLoader(
                    Objects.requireNonNull(getClass().getResource("/WelcomeScreen.fxml")));
            ControllerUtils.createScene(event, fxmlLoader);
          }
        });

    giveUpButton.setOnAction(
        new EventHandler<>() {
          @Override
          public void handle(ActionEvent event) {
            game.giveUp();
            FXMLLoader fxmlLoader =
                new FXMLLoader(
                    Objects.requireNonNull(getClass().getResource("/GameGivenUpScreen.fxml")));
            ControllerUtils.createScene(event, fxmlLoader);
          }
        });

    final Task<Void> task =
        new Task<>() {
          @Override
          protected Void call() {
            while (game.getGameState().isGameRunning()) {
              int i = game.getSideConditions().getEndurance();
              updateProgress(i, 10);
              if (game.getSideConditions().isInFlightMode()) {
                enduranceBar.setStyle("-fx-accent: red;");
              } else {
                enduranceBar.setStyle("-fx-accent: gray;");
              }
            }
            return null;
          }
        };
    enduranceBar.progressProperty().bind(task.progressProperty());
    new Thread(task).start();
  }

  private Node createGameNode(OwlnGo game) {

    final GameView gameView = new GameView(game);
    Platform.runLater(
        () -> {
          final Stage stage = (Stage) gameView.getScene().getWindow();
          ViewUtils.setGameStateListener(stage, game);
        });

    return gameView;
  }

  /**
   * Inner class to construct a timer for timeStringProperty measuring level completions. Source:
   * https://stackoverflow.com/questions/9355303/javafx-stopwatch-timer
   */
  private static final class LevelTimer {
    private static final String TIMER_PREFIX = "Time: ";
    private static final int TIMER_MINUTES = 0;
    private static final int TIMER_SECONDS = 1;
    private static final int TIMER_MILLIS = 2;
    private static final int TIMER_REFRESH_RATE = 10;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss:SS");
    private final SimpleStringProperty timeStringProperty;
    private final Timer timer = new Timer("Level time", true);
    private long time;
    private TimerTask timerTask;
    private boolean timing = false;

    public LevelTimer() {
      timeStringProperty = new SimpleStringProperty("00:00:00");
    }

    public void startTimer(final long time) {
      this.time = time;
      timing = true;
      timerTask =
          new TimerTask() {
            @Override
            public void run() {
              if (!timing) {
                try {
                  timerTask.cancel();
                } catch (Exception e) {
                  e.printStackTrace();
                }
              } else {
                Platform.runLater(() -> updateTime());
              }
            }
          };
      timer.scheduleAtFixedRate(timerTask, TIMER_REFRESH_RATE, TIMER_REFRESH_RATE);
    }

    public void stopTimer() {
      timing = false;
    }

    private void updateTime() {
      this.time += TIMER_REFRESH_RATE;
      String[] split = dateFormat.format(new Date(time)).split(":");
      timeStringProperty.set(
          TIMER_PREFIX
              + split[TIMER_MINUTES]
              + ":"
              + split[TIMER_SECONDS]
              + ":"
              + (split[TIMER_MILLIS].length() == 1
                  ? "0" + split[TIMER_MILLIS]
                  : split[TIMER_MILLIS].substring(0, 2)));
    }

    public long getTime() {
      return time;
    }

    public SimpleStringProperty timeStringProperty() {
      return timeStringProperty;
    }
  }
}
