package owlngo.communication.messages;

import owlngo.communication.utils.LevelJson;
import owlngo.game.level.Level;

/** Calls for a concrete level layout from the server to play on. */
public final class LoadLevelRequest implements Message {
  @SuppressWarnings("unused")
  private static final String messageType = "LoadLevelRequest";

  private final String playerName;
  private final LevelJson levelJson;

  public LoadLevelRequest(String playerName, Level level) {
    this.playerName = playerName;
    levelJson = new LevelJson(level);
  }

  public String getPlayerName() {
    return playerName;
  }

  public Level getLevel() {
    return levelJson.createLevel();
  }
}
