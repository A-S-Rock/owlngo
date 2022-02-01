package owlngo.communication.messages;

/** Asks for all level's info to choose from when selecting a game. */
public final class LoadLevelInfosRequest implements Message {
  @SuppressWarnings("unused")
  private static final String messageType = "LoadLevelInfosRequest";

  private final String playerName;

  /**
   * Creates a request to the server to send all the level's info saved on the server.
   *
   * @param playerName the request's player name
   */
  public LoadLevelInfosRequest(String playerName) {
    this.playerName = playerName;
  }

  /**
   * Returns the player name from which the request has been made.
   *
   * @return the player's name
   */
  public String getPlayerName() {
    return playerName;
  }
}
