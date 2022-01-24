package owlngo.communication.messages;

import java.util.List;

/** Delivers all level's names to the client for level choice. */
public final class LevelNamesNotification implements Message {
  @SuppressWarnings("unused")
  private static final String messageType = "LevelNamesNotification";

  private final List<String> levelNames;

  public LevelNamesNotification(List<String> levelNames) {
    this.levelNames = List.copyOf(levelNames);
  }

  public List<String> getLevelNames() {
    return List.copyOf(levelNames);
  }
}
