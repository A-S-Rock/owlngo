package owlngo.communication.messages;

/** Confirms that the level has been successfully saved on server side. */
public final class LevelSavedNotification implements Message {
  @SuppressWarnings("unused")
  private static final String messageType = "LevelSavedNotification";

  private final String levelName;

  /**
   * Creates a notification from the server that a level has been saved.
   *
   * @param levelName the name of the level which has been saved.
   */
  public LevelSavedNotification(String levelName) {
    this.levelName = levelName;
  }

  /**
   * Returns the level name that has been saved on the server.
   *
   * @return the level name.
   */
  public String getLevelName() {
    return levelName;
  }
}
