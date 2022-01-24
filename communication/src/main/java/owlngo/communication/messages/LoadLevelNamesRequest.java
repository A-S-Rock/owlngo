package owlngo.communication.messages;

/** Asks for all level's names to choose from when selecting a game. */
public final class LoadLevelNamesRequest implements Message {
  @SuppressWarnings("unused")
  private static final String messageType = "LoadLevelNamesRequest";

  private final String playerName;

  public LoadLevelNamesRequest(String playerName) {
    this.playerName = playerName;
  }

  public String getPlayerName() {
    return playerName;
  }
}
