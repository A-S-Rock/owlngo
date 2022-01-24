package owlngo.communication.messages;

import owlngo.communication.utils.LevelJson;
import owlngo.game.level.Level;

/** Returns a level to the client from the server. */
public final class SendLevelNotification implements Message {
  @SuppressWarnings("unused")
  private static final String messageType = "SendLevelNotification";

  private final String levelName;
  private final LevelJson levelJson;

  public SendLevelNotification(String levelName, Level level) {
    this.levelName = levelName;
    levelJson = new LevelJson(level);
  }

  public String getLevelName() {
    return levelName;
  }

  public Level getLevel() {
    return levelJson.createLevel();
  }
}
