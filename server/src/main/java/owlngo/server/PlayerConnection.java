package owlngo.server;

import java.io.Closeable;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import owlngo.communication.Connection;
import owlngo.communication.messages.GetLevelStatsRequest;
import owlngo.communication.messages.LevelInfosNotification;
import owlngo.communication.messages.LevelSavedNotification;
import owlngo.communication.messages.LevelStatsNotification;
import owlngo.communication.messages.LoadLevelInfosRequest;
import owlngo.communication.messages.LoadLevelRequest;
import owlngo.communication.messages.Message;
import owlngo.communication.messages.SaveLevelRequest;
import owlngo.communication.messages.SendLevelNotification;
import owlngo.communication.messages.UpdateLevelStatsRequest;
import owlngo.communication.savefiles.LevelSavefile;
import owlngo.communication.savefiles.LevelStatsSavefile;
import owlngo.game.level.Level;

/**
 * This class handles the communication with (this server-side and) a connected client. Adjusted
 * from Task 3's equally named class.
 */
@edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP2")
public class PlayerConnection implements Closeable {

  private final String username;
  private final SavefileManager manager;
  private Connection connection;

  /**
   * Creates a player connection to the client with a managementlink to the savefiles.
   *
   * @param username the username of the client
   * @param connection the connection over which communication takes place
   * @param manager savefile manager for stored data on the server
   */
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

    if (message instanceof LoadLevelInfosRequest) {
      handleLoadLevelInfosRequest();
    } else if (message instanceof final LoadLevelRequest loadRequest) {
      handleLoadLevelRequest(loadRequest);
    } else if (message instanceof final SaveLevelRequest saveRequest) {
      handleSaveLevelRequest(saveRequest);
    } else if (message instanceof GetLevelStatsRequest) {
      handleGetLevelStatsRequest();
    } else if (message instanceof final UpdateLevelStatsRequest updateStatsRequest) {
      handleUpdateLevelStatsRequest(updateStatsRequest);
    } else {
      throw new AssertionError("Invalid communication.");
    }
  }

  private void handleLoadLevelInfosRequest() {
    final Map<String, LevelSavefile> savedLevels = manager.getSavedLevels();
    final List<List<String>> levelInfos = new ArrayList<>();

    for (LevelSavefile savefile : savedLevels.values()) {
      List<String> levelRecord = List.of(savefile.getLevelName(), savefile.getAuthor());
      levelInfos.add(levelRecord);
    }

    final LevelInfosNotification notification = new LevelInfosNotification(levelInfos);
    connection.write(notification);
  }

  private void handleLoadLevelRequest(LoadLevelRequest message) {
    final String levelName = message.getLevelName();
    final Level level = manager.loadLevelSavefile(levelName);
    final SendLevelNotification notification = new SendLevelNotification(levelName, level);

    connection.write(notification);
  }

  private void handleSaveLevelRequest(SaveLevelRequest saveRequest) {
    final String author = saveRequest.getAuthor();
    final String levelName = saveRequest.getLevelName();
    final Level level = saveRequest.getLevel();

    manager.writeLevelSavefile(levelName, author, level);
    connection.write(new LevelSavedNotification(levelName));
  }

  private void handleGetLevelStatsRequest() {
    final Map<String, LevelStatsSavefile> savedStats = manager.getSavedStats();
    final List<List<String>> levelStats = new ArrayList<>();

    for (LevelStatsSavefile statsSavefile : savedStats.values()) {
      final String levelName = statsSavefile.getLevelName();
      final String triesString = String.valueOf(statsSavefile.getTries());
      final String completionsString = String.valueOf(statsSavefile.getCompletions());
      final String bestTime = statsSavefile.getBestTime();
      final String byUser = statsSavefile.getByUser();

      List<String> levelStatsRecord =
          List.of(levelName, triesString, completionsString, bestTime, byUser);
      levelStats.add(levelStatsRecord);
    }

    final LevelStatsNotification notification = new LevelStatsNotification(levelStats);
    connection.write(notification);
  }

  private void handleUpdateLevelStatsRequest(UpdateLevelStatsRequest message) {
    final String levelName = message.getLevelName();

    // Find old data (or generate new if not present)

    LevelStatsSavefile statsSavefile = manager.getSavedStats().get(levelName);
    if (statsSavefile == null) {
      manager.writeLevelStatsSavefile(levelName, 0, 0, "59:59:99", "VOID");
      statsSavefile = manager.getSavedStats().get(levelName);
    }
    int oldTries = statsSavefile.getTries();
    int oldCompletions = statsSavefile.getCompletions();
    String oldBestTime = statsSavefile.getBestTime();
    String oldByUser = statsSavefile.getByUser();

    // Calculate new updated savefile

    final int newTries = ++oldTries; // increment oldTries, because level got attempted

    final boolean newHasWon = message.getHasWon();

    if (newHasWon) {

      final int newCompletions = ++oldCompletions;

      final String newTime = message.getTime();

      final SimpleDateFormat formatter = new SimpleDateFormat("mm:ss:SS");
      try {
        final Date oldTimeDate = formatter.parse(oldBestTime);
        final Date newTimeDate = formatter.parse(newTime);

        final Date newBestTime;
        String newByUser;
        if (newTimeDate.before(oldTimeDate)) {
          newBestTime = newTimeDate;
          newByUser = message.getUsername();
        } else {
          newBestTime = oldTimeDate;
          newByUser = oldByUser;
        }

        final String newBestTimeString = formatter.format(newBestTime);

        manager.writeLevelStatsSavefile(
            levelName, newTries, newCompletions, newBestTimeString, newByUser);
      } catch (ParseException e) {
        System.err.println("Couldn't read time properly! " + oldBestTime);
        e.printStackTrace();
      }
    } else {
      manager.writeLevelStatsSavefile(levelName, newTries, oldCompletions, oldBestTime, oldByUser);
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
