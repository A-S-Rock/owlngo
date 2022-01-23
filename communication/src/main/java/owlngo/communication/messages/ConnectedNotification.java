package owlngo.communication.messages;

/**
 * Confirms that the client has successfully established a connection to the server for file
 * transfer.
 */
public final class ConnectedNotification implements Message {
  @SuppressWarnings("unused")
  private static final String messageType = "ConnectedNotification";

  private final String playerName;

  public ConnectedNotification(String playerName) {
    this.playerName = playerName;
  }

  public String getPlayerName() {
    return playerName;
  }
}
