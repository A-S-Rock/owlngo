package owlngo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import owlngo.communication.messages.ConnectedNotification;
import owlngo.communication.messages.ConnectionRequest;
import owlngo.communication.messages.Message;
import owlngo.communication.utils.MessageCoder;

/** This class handles the communication with (this server-side and) a connected client. */
public class ScConnection implements Runnable {

  private final Socket socket;

  ScConnection(Socket socket) {
    this.socket = socket;
  }

  /**
   * Main method of the thread that handles incoming and outgoing "server" messages to its
   * corresponding client.
   */
  public void run() {
    PrintWriter pcOutput = null;
    BufferedReader pcInput;
    try {
      pcInput =
          new BufferedReader(
              new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

      while (true) {
        String pcIn = pcInput.readLine();
        if (pcIn == null) {
          break;
        }

        Message decodedString = MessageCoder.decodeFromJson(pcIn);

        pcOutput = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);

        // Connection request
        if (decodedString instanceof ConnectionRequest) {
          String playerName = (((ConnectionRequest) decodedString).getPlayerName());
          System.err.println("[Server] Welcome to the Game, " + playerName + "!");
          pcOutput.println(MessageCoder.encodeToJson(new ConnectedNotification(playerName)));
        }
      }
    } catch (IOException e) {
      System.err.println("Couldn't read from client!");
    }
  }
}
