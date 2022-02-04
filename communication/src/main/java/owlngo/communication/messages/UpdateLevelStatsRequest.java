package owlngo.communication.messages;

/** Sends a stats update to the Server for the current level. */
public class UpdateLevelStatsRequest implements Message {

  @SuppressWarnings("unused")
  private static final String messageType = "UpdateLevelStatsRequest";

  private final String levelName;
  private final boolean hasWon;
  private final String time;
  private final String username;

  /**
   * Creates the level stats update.
   *
   * @param levelName the level's name
   * @param hasWon shows if the player has won the current attempt
   * @param time String representation of the accomplished time
   * @param username user with the new time
   */
  public UpdateLevelStatsRequest(String levelName, boolean hasWon, String time, String username) {
    this.levelName = levelName;
    this.hasWon = hasWon;
    this.time = time;
    this.username = username;
  }

  /**
   * Returns the level name from the level to save on the server.
   *
   * @return the level name.
   */
  public String getLevelName() {
    return levelName;
  }

  /**
   * Returns if the player has won the level.
   *
   * @return <code>true</code> if the player has won; else <code>false</code>
   */
  public boolean getHasWon() {
    return hasWon;
  }

  /**
   * Returns the current completion time.
   *
   * @return the time the finish has been arrived.
   */
  public String getTime() {
    return time;
  }

  /**
   * Returns the latest attempt username.
   *
   * @return username of the last attempt.
   */
  public String getUsername() {
    return username;
  }
}
