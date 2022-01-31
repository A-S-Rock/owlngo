package owlngo.server;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import owlngo.communication.Connection;
import owlngo.communication.messages.ConnectedNotification;
import owlngo.communication.messages.ConnectionRequest;
import owlngo.communication.messages.Message;

/**
 * This class handles all the clients connected to the server. Adjusted from the equally named class
 * in Task 3.
 */
public class ConnectionManager implements AutoCloseable {

  private Map<String, PlayerConnection> connections = new HashMap<>();
  private final SavefileManager manager = new SavefileManager();

  void createNewPlayerConnection(Socket connectionSocket) throws IOException {
    Connection connection =
        new Connection(connectionSocket.getOutputStream(), connectionSocket.getInputStream());
    PlayerConnection player = handleConnectionRequest(connection);
    connections.put(player.getUsername(), player);
    System.out.println("Connection successful, create notification.");
    player.send(new ConnectedNotification(player.getUsername()));
    new Thread(() -> listenForInput(player)).start();
  }

  private void listenForInput(PlayerConnection player) {
    try {
      while (true) {
        player.reactToClient();
      }
    } catch (IOException e) {
      System.err.println("Error on " + player.getUsername() + ", disconnecting: " + e.getMessage());
      if (connections != null) {
        connections.remove(player.getUsername());
      }
    }
  }

  private PlayerConnection handleConnectionRequest(Connection connection) throws IOException {
    // Await initial connection request to react to.
    Message connectionMessage = connection.read();
    if (!(connectionMessage instanceof ConnectionRequest)) {
      connection.close();
      throw new AssertionError("Unknown message type!");
    }
    System.out.println("Received a connection request!");
    String username = ((ConnectionRequest) connectionMessage).getPlayerName();
    return new PlayerConnection(username, connection, manager);
  }

  @Override
  public void close() {
    for (PlayerConnection player : connections.values()) {
      player.close();
    }
    connections = null; // makes this object unusable
  }
}
