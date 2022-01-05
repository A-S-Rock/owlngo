package owlngo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/** This class handles the communication with (this server-side and) a connected client. */
public class ScConnection implements Runnable {

  private final Socket socket;
  private String playerName;

  ScConnection(Socket socket) {
    this.socket = socket;
  }

  /**
   * Main method of the thread that handles incoming and outgoing "server" messages to its
   * corresponding client.
   */
  public void run() {
    PrintWriter pcOutput = null;
    BufferedReader pcInput = null;
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
      e.getCause().getCause().printStackTrace();
    }
  }
}
