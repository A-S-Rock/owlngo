package owlngo.server;

import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;
import owlngo.communication.Connection;
import owlngo.communication.messages.LoadLevelNamesRequest;
import owlngo.communication.messages.Message;

/**
 * This class handles the communication with (this server-side and) a connected client. Adjusted
 * from Task 3's equally named class.
 */
public class PlayerConnection implements Closeable {

  private final String username;
  private Connection connection;

  public PlayerConnection(String username, Connection connection) {
    this.username = username;
    this.connection = connection;
  }

  public void reactToClient() throws IOException {
    Message message = connection.read();

    if (message instanceof LoadLevelNamesRequest) {
      // TODO: Implement the level names
      System.out.println("I received the load level request.");
    } else {
      throw new AssertionError("Invalid communication.");
    }
  }

  void send(Message message) {
    if (connection != null) {
      connection.write(message);
    }
  }

  public String getUsername() {
    return username;
  }

  @Override
  public void close() {
    connection.close();
    connection = null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PlayerConnection that = (PlayerConnection) o;
    return Objects.equals(username, that.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username);
  }
}
