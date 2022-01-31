package owlngo.communication.messages;

import owlngo.communication.utils.LevelJson;
import owlngo.game.level.Level;

/** Returns a level to the client from the server. */
public final class SendLevelNotification implements Message {
  @SuppressWarnings("unused")
  private static final String messageType = "SendLevelNotification";

  private final String levelName;
  private final LevelJson levelJson;

  /**
   * Creates a notification from the server which sends the level to the client.
   *
   * @param levelName the requested level's name
   * @param level the level in its JSON representation
   */
  public SendLevelNotification(String levelName, Level level) {
    this.levelName = levelName;
    levelJson = new LevelJson(level);
  }

  /**
   * Returns the level name which is sent.
   *
   * @return the level's name.
   */
  public String getLevelName() {
    return levelName;
  }

  /**
   * Returns the level sent by the server.
   *
   * @return the level.
   */
  public Level getLevel() {
    return levelJson.createLevel();
  }
}
