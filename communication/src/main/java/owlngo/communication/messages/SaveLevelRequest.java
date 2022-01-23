package owlngo.communication.messages;

import owlngo.game.level.Level;

/** Requests a level layout to be saved on the server. */
public final class SaveLevelRequest implements Message {
  private static final String messageType = "SaveLevelRequest";
  private final String author;
  private final String levelName;
  private final Level level;

  public SaveLevelRequest(String author, String levelName, Level level) {
    this.author = author;
    this.levelName = levelName;
    this.level = level;
  }

  public String getAuthor() {
    return author;
  }

  public String getLevelName() {
    return levelName;
  }

  public Level getLevel() {
    return level;
  }
}
