package owlngo.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import javafx.application.Application;
import owlngo.communication.messages.ConnectedNotification;
import owlngo.communication.messages.ConnectionRequest;
import owlngo.communication.messages.Message;
import owlngo.communication.utils.MessageCoder;
import owlngo.gui.playfield.WelcomeScreen;

/** Class handles incoming and outgoing messages to and from the server. */
public class CommunicationManager {

  private final Socket socket;
  private String username;

  CommunicationManager(String username, Socket socket) {
    this.username = username;
    this.socket = socket;
  }

  /**
   * Main method of the thread that handles different types of messages to its corresponding
   * server-class.
   */
  void handleCommunication() throws IOException {
    // starting GUI
    Application.launch(WelcomeScreen.class, new String[] {});

    PrintWriter clientOutput =
        new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
    BufferedReader clientInput =
        new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
    clientOutput.println(MessageCoder.encodeToJson(new ConnectionRequest(username)));

    while (true) {
      try {
        String clientIn = clientInput.readLine();
        if (clientIn == null) {
          break;
        }
        Message decodedString = MessageCoder.decodeFromJson(clientIn);

        if (decodedString instanceof ConnectedNotification) {
          System.out.println(
              "Server signaled a positive conntection from "
                  + ((ConnectedNotification) decodedString).getPlayerName());
        }
      } catch (IOException e) {
        throw new AssertionError("Exception during Server-Client-Communication");
      }
    }
  }
}
