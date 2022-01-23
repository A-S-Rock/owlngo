package owlngo.communication.savefiles;

import owlngo.game.level.Level;

/** Used for the persistent saving of Level files including some stats. */
public final class LevelSavefile implements Savefile {
  @SuppressWarnings("unused")
  private static final String savefileType = "LevelSavefile";

  private final String levelName;
  private final String author;
  private final Level level;

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
    this.level = level.copyOf();
  }

  public String getLevelName() {
    return levelName;
  }

  public String getAuthor() {
    return author;
  }

  public Level getLevel() {
    return level.copyOf();
  }
}
