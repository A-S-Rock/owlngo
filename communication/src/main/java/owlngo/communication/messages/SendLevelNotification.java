package owlngo.communication.messages;

import owlngo.game.level.Level;

/** Returns a level to the client from the server. */
public final class SendLevelNotification implements Message {
  @SuppressWarnings("unused")
  private static final String messageType = "SendLevelNotification";

  private final String levelName;
  private final Level level;

  public SendLevelNotification(String levelName, Level level) {
    this.levelName = levelName;
    this.level = level.copyOf();
  }

  public String getLevelName() {
    return levelName;
  }

  public Level getLevel() {
    return level.copyOf();
  }
}
