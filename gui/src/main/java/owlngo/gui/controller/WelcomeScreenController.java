package owlngo.gui.controller;

import java.io.IOException;
import java.net.Socket;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import owlngo.communication.Connection;
import owlngo.communication.messages.ConnectionRequest;
import owlngo.communication.messages.Message;
import owlngo.gui.data.CommunicationManager;

/** Contoller class for WelcomeScreen.fxml. */
public class WelcomeScreenController {

  @FXML Button startRandomGameButton;
  @FXML Button loadEditorButton;
  @FXML Button loadLevelButton;
  @FXML Button exitGameButton;
  @FXML Button highscoreButton;
  @FXML Pane imagePane;

  private final String username;

  /**
   * Initializes the controller to use the socket given by the client.
   *
   * @throws IOException if the establishment of the connection fails.
   */
  public WelcomeScreenController() throws IOException {
    username = CommunicationManager.getInstance().getUsername();
    final Socket socket = CommunicationManager.getInstance().getSocket();
    try (Connection connection = establishConnection(socket)) {
      System.out.println("I will send a connection message now.");
      connectToServer(connection);
    }
  }

  private void connectToServer(Connection connection) {
    Message connectionMessage = new ConnectionRequest(username);
    connection.write(connectionMessage);
  }

  private Connection establishConnection(Socket socket) throws IOException {
    return new Connection(socket.getOutputStream(), socket.getInputStream());
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
