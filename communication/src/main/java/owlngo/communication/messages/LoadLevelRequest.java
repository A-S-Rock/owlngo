package owlngo.communication.messages;

/** Calls for a concrete level layout from the server to play on. */
public final class LoadLevelRequest implements Message {
  @SuppressWarnings("unused")
  private static final String messageType = "LoadLevelRequest";

  private final String playerName;
  private final String levelName;

  /**
   * Creates a request to load a level from the server.
   *
   * @param playerName the player's name.
   * @param levelName the level to load
   */
  public LoadLevelRequest(String playerName, String levelName) {
    this.playerName = playerName;
    this.levelName = levelName;
  }

  /**
   * Returns the player's name from which the request has been made.
   *
   * @return the player's name.
   */
  public String getPlayerName() {
    return playerName;
  }

  /**
   * Returns the level to be loaded from the server.
   *
   * @return the level
   */
  public String getLevelName() {
    return levelName;
  }
}
