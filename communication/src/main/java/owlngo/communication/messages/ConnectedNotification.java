package owlngo.communication.messages;

/**
 * Confirms that the client has successfully established a connection to the server for file
 * transfer.
 */
public final class ConnectedNotification implements Message {
  @SuppressWarnings("unused")
  private static final String messageType = "ConnectedNotification";

  private final String playerName;

  /**
   * Creates a connection notification with the given name.
   *
   * @param playerName the player name
   */
  public ConnectedNotification(String playerName) {
    this.playerName = playerName;
  }

  /**
   * Returns the player name of the message sender.
   *
   * @return the player name
   */
  public String getPlayerName() {
    return playerName;
  }
}
