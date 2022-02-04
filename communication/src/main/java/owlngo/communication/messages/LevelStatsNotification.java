package owlngo.communication.messages;

import java.util.List;

/** Holds the statistic information for a specific level and sends it to the client. */
public class LevelStatsNotification implements Message {

  @SuppressWarnings("unused")
  private static final String messageType = "LevelStatsNotification";

  private final List<List<String>> levelStats;

  /**
   * Creates the level statistics collection for the client to display.
   *
   * @param levelStats level names including the number of tries, completion, best times and the
   *     user with the best time.
   */
  public LevelStatsNotification(List<List<String>> levelStats) {
    this.levelStats = List.copyOf(levelStats);
  }

  /**
   * Returns all the saved level's stats.
   *
   * @return list of level stats for each level.
   */
  public List<List<String>> getLevelStats() {
    return List.copyOf(levelStats);
  }
}
