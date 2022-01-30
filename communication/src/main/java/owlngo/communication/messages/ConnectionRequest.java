package owlngo.communication.messages;

/** Serves as a test message to the server. Also provides the username to the server if needed. */
public final class ConnectionRequest implements Message {

  @SuppressWarnings("unused")
  private static final String messageType = "ConnectionRequest";

  private final String playerName;

  /**
   * Creates a connection request with the given player name.
   *
   * @param playerName the player name.
   */
  public ConnectionRequest(String playerName) {
    this.playerName = playerName;
  }

  /**
   * Returns the player's name.
   *
   * @return the player name.
   */
  public String getPlayerName() {
    return playerName;
  }
}
