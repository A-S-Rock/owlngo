package owlngo.communication.savefiles;

import owlngo.communication.utils.LevelJson;
import owlngo.game.level.Level;

/** Used for the persistent saving of Level files including some stats. */
public final class LevelSavefile implements Savefile {
  @SuppressWarnings("unused")
  private static final String savefileType = "LevelSavefile";

  private final String levelName;
  private final String author;
  private final LevelJson levelJson;

  /**
   * Creates a new level savefile.
   *
   * @param levelName the level name given by the user
   * @param author the creator player's name
   * @param level the level layout
   */
  public LevelSavefile(String levelName, String author, Level level) {
    this.levelName = levelName;
    this.author = author;
    levelJson = new LevelJson(level);
  }

  /**
   * Returns the name the level has been given by its author.
   *
   * @return the level's name.
   */
  public String getLevelName() {
    return levelName;
  }

  /**
   * Returns the author of the created level.
   *
   * @return the player name who created the level.
   */
  public String getAuthor() {
    return author;
  }

  /**
   * Returns the level saved on the server in this file.
   *
   * @return the level
   */
  public Level getLevel() {
    return levelJson.createLevel();
  }
}
