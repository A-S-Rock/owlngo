package owlngo.communication.messages;

import owlngo.communication.utils.LevelJson;
import owlngo.game.level.Level;

/** Calls for a concrete level layout from the server to play on. */
public final class LoadLevelRequest implements Message {
  @SuppressWarnings("unused")
  private static final String messageType = "LoadLevelRequest";

  private final String playerName;
  private final LevelJson levelJson;

  /**
   * Creates a request to load a level from the server.
   *
   * @param playerName the player's name.
   * @param level the level to load
   */
  public LoadLevelRequest(String playerName, Level level) {
    this.playerName = playerName;
    levelJson = new LevelJson(level);
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
  public Level getLevel() {
    return levelJson.createLevel();
  }
}
