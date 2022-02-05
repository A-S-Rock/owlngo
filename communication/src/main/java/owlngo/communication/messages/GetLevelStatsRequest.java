package owlngo.communication.messages;

/** Requests the update for all the level's stats on the server. */
public class GetLevelStatsRequest implements Message {
  @SuppressWarnings("unused")
  private static final String messageType = "GetLevelStatsRequest";

  private final String playerName;

  /**
   * Creates a request from the client to update the level stats.
   *
   * @param playerName the player name.
   */
  public GetLevelStatsRequest(String playerName) {
    this.playerName = playerName;
  }

  /**
   * Returns the requestor's name.
   *
   * @return the player name.
   */
  public String getPlayerName() {
    return playerName;
  }
}
