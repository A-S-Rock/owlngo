package owlngo.communication.messages;

/** Asks for all level's names to choose from when selecting a game. */
public final class LoadLevelNamesRequest implements Message {
  @SuppressWarnings("unused")
  private static final String messageType = "LoadLevelNamesRequest";

  private final String playerName;

  /**
   * Creates a request to the server to send all the level's names saved on the server.
   *
   * @param playerName the request's player name
   */
  public LoadLevelNamesRequest(String playerName) {
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
