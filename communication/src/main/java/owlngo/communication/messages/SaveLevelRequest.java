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

  /**
   * Returns the author's name.
   *
   * @return the author's player name.
   */
  public String getAuthor() {
    return author;
  }

  /**
   * Returns the level name from the level to save on the server.
   *
   * @return the level name.
   */
  public String getLevelName() {
    return levelName;
  }

  /**
   * Returns the level to be saved on the server.
   *
   * @return the level
   */
  public Level getLevel() {
    return levelJson.createLevel();
  }
}
