package owlngo.server;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import owlngo.communication.Connection;
import owlngo.communication.messages.LevelNamesNotification;
import owlngo.communication.messages.LoadLevelNamesRequest;
import owlngo.communication.messages.Message;
import owlngo.communication.savefiles.LevelSavefile;

/**
 * This class handles the communication with (this server-side and) a connected client. Adjusted
 * from Task 3's equally named class.
 */
@edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP2")
public class PlayerConnection implements Closeable {

  private final String username;
  private Connection connection;
  private final SavefileManager manager;

  public PlayerConnection(String username, Connection connection, SavefileManager manager) {
    this.username = username;
    this.connection = connection;
    this.manager = manager;
  }

  /**
   * Reacts to specific messages sent by the client.
   *
   * @throws IOException if the received message is unreadable or other IOExceptions happen
   */
  public void reactToClient() throws IOException {
    Message message = connection.read();

    if (message instanceof LoadLevelNamesRequest) {
      System.out.println("I received the load level request.");

      final Map<String, LevelSavefile> savedLevels = manager.getSavedLevels();
      final List<List<String>> levelNames = new ArrayList<>();

      for (LevelSavefile savefile : savedLevels.values()) {
        List<String> levelRecord = List.of(savefile.getLevelName(), savefile.getAuthor());
        levelNames.add(levelRecord);
      }

      final LevelNamesNotification notification = new LevelNamesNotification(levelNames);
      connection.write(notification);
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
