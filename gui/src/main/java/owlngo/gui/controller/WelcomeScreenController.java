package owlngo.gui.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import owlngo.communication.Connection;
import owlngo.communication.messages.ConnectedNotification;
import owlngo.communication.messages.ConnectionRequest;
import owlngo.communication.messages.GetLevelStatsRequest;
import owlngo.communication.messages.LevelInfosNotification;
import owlngo.communication.messages.LevelSavedNotification;
import owlngo.communication.messages.LevelStatsNotification;
import owlngo.communication.messages.LoadLevelInfosRequest;
import owlngo.communication.messages.Message;
import owlngo.communication.messages.SendLevelNotification;
import owlngo.game.level.Level;
import owlngo.gui.data.CommunicationManager;
import owlngo.gui.data.DataManager;

/** Contoller class for WelcomeScreen.fxml. */
@SuppressFBWarnings("DM_EXIT")
public class WelcomeScreenController {

  private static boolean isConnected;
  private final CommunicationManager communicationManager;
  private final Connection connection;
  private final DataManager dataManager;
  @FXML Button startRandomGameButton;
  @FXML Button loadEditorButton;
  @FXML Button loadLevelButton;
  @FXML Button exitGameButton;
  @FXML Button highscoreButton;
  @FXML Pane imagePane;

  /** Initializes the controller to use the socket given by the client. */
  public WelcomeScreenController() {
    communicationManager = CommunicationManager.getInstance();
    connection = communicationManager.getConnection();
    dataManager = DataManager.getInstance();
    if (!isConnected) {
      new Thread(
              () -> {
                try {
                  start();
                } catch (IOException e) {
                  System.err.println("Failed to connect to the server!");
                  connection.close();
                  System.exit(0);
                }
              })
          .start();
    }
  }

  @SuppressWarnings("InfiniteLoopStatement")
  void start() throws IOException {
    connectToServer(connection);
    while (true) {
      reactToServer(connection);
    }
  }

  private void reactToServer(Connection connection) throws IOException {
    Message message = connection.read();
    if (message instanceof final ConnectedNotification connectedNotification) {
      handleConnectedNotification(connectedNotification);
    } else if (message instanceof final LevelInfosNotification infosNotification) {
      handleLevelInfosNotification(infosNotification);
    } else if (message instanceof final SendLevelNotification levelNotification) {
      handleSendLevelNotification(levelNotification);
    } else if (message instanceof final LevelSavedNotification levelSavedNotification) {
      handleLevelSavedNotification(levelSavedNotification);
    } else if (message instanceof final LevelStatsNotification levelStatsNotification) {
      handleLevelStatsNotification(levelStatsNotification);
    } else {
      throw new AssertionError("Unknown message type!");
    }
  }

  private void handleConnectedNotification(ConnectedNotification message) {
    final String sentUsername = message.getPlayerName();
    assert communicationManager.getUsername().equals(sentUsername);
    System.out.println(
        "[SERVER] " + sentUsername + " - you're successfully connected to the game server!");
    isConnected = true;
  }

  private void handleLevelInfosNotification(LevelInfosNotification message) {
    final List<List<String>> receivedLevelNames = message.getLevelInfos();
    dataManager.setLevelNamesContent(receivedLevelNames);
  }

  private void handleSendLevelNotification(SendLevelNotification message) {
    final Level level = message.getLevel();
    dataManager.setLevelContent(level);
  }

  private void handleLevelSavedNotification(LevelSavedNotification message) {
    final String levelName = message.getLevelName();

    Platform.runLater(() -> createSaveSuccessAlert(levelName));
  }

  private void handleLevelStatsNotification(LevelStatsNotification message) {
    final List<List<String>> receivedLevelStats = message.getLevelStats();
    dataManager.setLevelStatsContent(receivedLevelStats);
  }

  private void createSaveSuccessAlert(String levelName) {
    final ButtonType okayButtonType = new ButtonType("Okay!");
    final Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.getButtonTypes().clear();
    alert.getButtonTypes().add(okayButtonType);
    alert.setTitle("Success: Level save");
    alert.setHeaderText("Level save successful!");
    alert.setContentText("Your level \"" + levelName + "\" has been saved on the server!");
    alert.setGraphic(null);
    alert.showAndWait();
  }

  private void connectToServer(Connection connection) {
    final String username = communicationManager.getUsername();
    Message connectionMessage = new ConnectionRequest(username);
    connection.write(connectionMessage);
  }

  @FXML
  void initialize() {
    startRandomGameButton.setOnAction(
        new EventHandler<>() {
          @Override
          public void handle(ActionEvent event) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GameViewScreen.fxml"));
            ControllerUtils.createScene(event, fxmlLoader);
          }
        });

    loadEditorButton.setOnAction(
        new EventHandler<>() {
          @Override
          public void handle(ActionEvent event) {
            communicationManager.setConnection(connection);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EditorScreen.fxml"));
            ControllerUtils.createScene(event, fxmlLoader);
          }
        });

    loadLevelButton.setOnAction(
        new EventHandler<>() {
          @Override
          public void handle(ActionEvent event) {
            final String username = communicationManager.getUsername();
            connection.write(new LoadLevelInfosRequest(username));
            try {
              Thread.sleep(200); // wait a bit to let the server send its files
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LoadLevelScreen.fxml"));
            communicationManager.setConnection(connection);
            ControllerUtils.createScene(event, fxmlLoader);
          }
        });

    highscoreButton.setOnAction(
        new EventHandler<>() {
          @Override
          public void handle(ActionEvent event) {
            final String username = communicationManager.getUsername();
            connection.write(new GetLevelStatsRequest(username));
            try {
              Thread.sleep(200); // wait a bit to let the server send its files
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HighscoreScreen.fxml"));
            ControllerUtils.createScene(event, fxmlLoader);
          }
        });

    exitGameButton.setOnAction(event -> System.exit(0));

    imagePane.getChildren().add(ControllerUtils.createLogoPane("standard"));
    Platform.runLater(
        () -> imagePane.getScene().getWindow().setOnCloseRequest(event -> connection.close()));
  }
}
