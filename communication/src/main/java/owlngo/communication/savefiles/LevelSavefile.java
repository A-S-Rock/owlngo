package owlngo.communication.savefiles;

import owlngo.game.level.Level;

public final class LevelSavefile implements Savefile {
  @SuppressWarnings("unused")
  private static final String savefileType = "LevelSavefile";

  private final String levelName;
  private final String author;
  private final Level level;

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
