package owlngo.communication.messages;

import owlngo.communication.utils.LevelJson;
import owlngo.game.level.Level;

/** Requests a level layout to be saved on the server. */
public final class SaveLevelRequest implements Message {
  @SuppressWarnings("unused")
  private static final String messageType = "SaveLevelRequest";

  private final String author;
  private final String levelName;
  private final LevelJson levelJson;

  /**
   * Constructs a save request message.
   *
   * @param author the level creator's name
   * @param levelName the name given to the level
   * @param level the level
   */
  public SaveLevelRequest(String author, String levelName, Level level) {
    this.author = author;
    this.levelName = levelName;
    levelJson = new LevelJson(level);
  }

  public String getAuthor() {
    return author;
  }

  public String getLevelName() {
    return levelName;
  }

  public Level getLevel() {
    return levelJson.createLevel();
  }
}
