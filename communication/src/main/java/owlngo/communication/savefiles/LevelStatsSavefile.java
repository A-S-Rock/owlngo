package owlngo.communication.savefiles;

/** Saves the level stats persistently on the server. */
public class LevelStatsSavefile implements Savefile {
  @SuppressWarnings("unused")
  private static final String savefileType = "LevelStatsSavefile";

  private final String levelName;
  private final int tries;
  private final int completions;
  private final String bestTime;
  private final String byUser;

  /**
   * Creates a new level stats savefile with the number of tries, successes and best times.
   *
   * @param levelName the level
   * @param tries number of tries
   * @param completions number of successful attempts on the level
   * @param bestTime the current best time
   * @param byUser the username from the best time attempt
   */
  public LevelStatsSavefile(
      String levelName, int tries, int completions, String bestTime, String byUser) {
    this.levelName = levelName;
    this.tries = tries;
    this.completions = completions;
    this.bestTime = bestTime;
    this.byUser = byUser;
  }

  /**
   * Returns the name of the level.
   *
   * @return the level's name.
   */
  public String getLevelName() {
    return levelName;
  }

  /**
   * Returns the number of tries.
   *
   * @return the tries number.
   */
  public int getTries() {
    return tries;
  }

  /**
   * Returns the number of times this level has been completed successfully.
   *
   * @return the completion count
   */
  public int getCompletions() {
    return completions;
  }

  /**
   * Returns the best time in its String representation.
   *
   * @return the best time
   */
  public String getBestTime() {
    return bestTime;
  }

  /**
   * Returns the user with the best time.
   *
   * @return the user's name
   */
  public String getByUser() {
    return byUser;
  }
}
