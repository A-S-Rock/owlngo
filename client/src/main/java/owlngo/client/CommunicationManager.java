package owlngo.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import javafx.application.Application;
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
  void handleCommunication() {
    // starting GUI
    Application.launch(WelcomeScreen.class, new String[] {});

    try {
      PrintWriter clientOutput =
          new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
      // Input Reader defined, but not yet used
      /* BufferedReader clientInput =
      new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));*/
      clientOutput.println(username + " says hello. What say you? :P");
    } catch (IOException e) {
      System.err.println("IOException while communicating with the Server");
    }
  }
}
