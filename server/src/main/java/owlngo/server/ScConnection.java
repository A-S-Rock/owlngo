package owlngo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

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
    // PrintWriter pcOutput = null; // Currently unused outputsteam to the client
    BufferedReader pcInput; // = null;
    try {
      pcInput =
          new BufferedReader(
              new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

      while (true) {
        String pcIn = pcInput.readLine();
        if (pcIn == null) {
          break;
        }
        System.err.println("[Client]" + pcIn);
      }
    } catch (IOException e) {
      System.err.println("Couldn't read from client!");
    }
  }
}
