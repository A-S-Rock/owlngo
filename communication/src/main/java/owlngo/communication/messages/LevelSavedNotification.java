package owlngo.communication.messages;

/** Confirms that the level has been successfully saved on server side. */
public final class LevelSavedNotification implements Message {
  @SuppressWarnings("unused")
  private static final String messageType = "LevelSavedNotification";

  private final String levelName;

  public LevelSavedNotification(String levelName) {
    this.levelName = levelName;
  }

  public String getLevelName() {
    return levelName;
  }
}
