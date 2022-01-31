package owlngo.gui.controller;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import owlngo.communication.Connection;
import owlngo.communication.messages.ConnectedNotification;
import owlngo.communication.messages.ConnectionRequest;
import owlngo.communication.messages.LevelNamesNotification;
import owlngo.communication.messages.LoadLevelNamesRequest;
import owlngo.communication.messages.Message;
import owlngo.gui.data.CommunicationManager;
import owlngo.gui.data.DataManager;

/** Contoller class for WelcomeScreen.fxml. */
public class WelcomeScreenController {

  @FXML Button startRandomGameButton;
  @FXML Button loadEditorButton;
  @FXML Button loadLevelButton;
  @FXML Button exitGameButton;
  @FXML Button highscoreButton;
  @FXML Pane imagePane;

  private static boolean isConnected;

  private final String username;
  private final Connection connection;

  /** Initializes the controller to use the socket given by the client. */
  public WelcomeScreenController() throws IOException {
    username = CommunicationManager.getInstance().getUsername();
    final Socket socket = CommunicationManager.getInstance().getSocket();
    this.connection = new Connection(socket.getOutputStream(), socket.getInputStream());
    if (!isConnected) {
      new Thread(
              () -> {
                try {
                  start();
                } catch (IOException e) {
                  System.err.println("Failed to connect to the server!");
                  connection.close();
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
    if (message instanceof ConnectedNotification) {
      final String sentUsername = ((ConnectedNotification) message).getPlayerName();
      assert username.equals(sentUsername);
      System.out.println(
          "[SERVER] " + sentUsername + " - you're successfully connected to the game server!");
      isConnected = true;
    } else if (message instanceof LevelNamesNotification) {
      final List<List<String>> receivedLevelNames =
          ((LevelNamesNotification) message).getLevelNames();
      System.out.println(
          "[CLIENT] Level names successfully loaded - "
              + Arrays.toString(receivedLevelNames.toArray()));
      DataManager.getInstance().setLevelNamesContent(receivedLevelNames);
    } else {
      throw new AssertionError("Unknown message type!");
    }
  }

  private void connectToServer(Connection connection) {
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EditorWindow.fxml"));
            ControllerUtils.createScene(event, fxmlLoader);
          }
        });

    loadLevelButton.setOnAction(
        new EventHandler<>() {
          @Override
          public void handle(ActionEvent event) {
            connection.write(new LoadLevelNamesRequest(username));
            try {
              Thread.sleep(1000); // wait a bit to let the server send its files
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LoadLevelScreen.fxml"));
            ControllerUtils.createScene(event, fxmlLoader);
          }
        });

    highscoreButton.setOnAction(
        new EventHandler<>() {
          @Override
          public void handle(ActionEvent event) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HighscoreScreen.fxml"));
            ControllerUtils.createScene(event, fxmlLoader);
          }
        });

    exitGameButton.setOnAction(event -> System.exit(0));

    imagePane.getChildren().add(ControllerUtils.createLogoPane("standard"));
  }
}
