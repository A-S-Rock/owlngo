package owlngo.communication.messages;

import java.util.List;

/** Delivers all level's info (name and author) to the client for level choice. */
public final class LevelInfosNotification implements Message {
  @SuppressWarnings("unused")
  private static final String messageType = "LevelNamesNotification";

  private final List<List<String>> levelInfos;

  /**
   * Creates a notification from the server with the given level info.
   *
   * @param levelInfos all saved level's names and authors.
   */
  public LevelInfosNotification(List<List<String>> levelInfos) {
    this.levelInfos = List.copyOf(levelInfos);
  }

  /**
   * Returns the saved level's infos on the server.
   *
   * @return list of level names.
   */
  public List<List<String>> getLevelInfos() {
    return List.copyOf(levelInfos);
  }
}
