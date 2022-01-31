package owlngo.communication.messages;

import java.util.List;

/** Delivers all level's names to the client for level choice. */
public final class LevelNamesNotification implements Message {
  @SuppressWarnings("unused")
  private static final String messageType = "LevelNamesNotification";

  private final List<List<String>> levelNames;

  /**
   * Creates a notification from the server with the given level names.
   *
   * @param levelNames list of level names on the server
   */
  public LevelNamesNotification(List<List<String>> levelNames) {
    this.levelNames = List.copyOf(levelNames);
  }

  /**
   * Returns the saved level names on the server.
   *
   * @return list of level names.
   */
  public List<List<String>> getLevelNames() {
    return List.copyOf(levelNames);
  }
}
