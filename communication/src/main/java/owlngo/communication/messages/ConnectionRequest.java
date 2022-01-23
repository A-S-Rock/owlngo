package owlngo.communication.messages;

/** Serves as a test message to the server. Also provides the username to the server if needed. */
public final class ConnectionRequest implements Message {

  private static final String messageType = "ConnectionRequest";
  private final String playerName;

  public ConnectionRequest(String playerName) {
    this.playerName = playerName;
  }

  /** Returns the player's name. */
  public String getPlayerName() {
    return playerName;
  }
}
