package owlngo.gui.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class is used to get a data model for the highscore table view. See <a
 * href="https://openjfx.io/javadoc/17/javafx.controls/javafx/scene/control/TableView.html">JavaFX
 * doc for TableView</a>.
 */
public class LevelStatsRecord {

  // Level name.

  private StringProperty levelName;
  private IntegerProperty tries;
  private IntegerProperty completions;
  private StringProperty bestTime;

  // Number of tries.
  private StringProperty byUser;

  /**
   * Sets up a level stats record to show in a table view.
   *
   * @param levelName the level name
   * @param tries the number of tries on the level
   * @param completions the number of successful tries on the level
   * @param bestTime the best time
   * @param byUser the user with the best time
   */
  public LevelStatsRecord(
      String levelName, int tries, int completions, String bestTime, String byUser) {
    setLevelName(levelName);
    setTries(tries);
    setCompletions(completions);
    setBestTime(bestTime);
    setByUser(byUser);
  }

  public String getLevelName() {
    return levelNameProperty().get();
  }

  public void setLevelName(String value) {
    levelNameProperty().set(value);
  }

  // Number of completions.

  /**
   * Returns the level name property of the table record.
   *
   * @return level name property
   */
  public StringProperty levelNameProperty() {
    if (levelName == null) {
      levelName = new SimpleStringProperty(this, "levelName");
    }
    return levelName;
  }

  public int getTries() {
    return triesProperty().get();
  }

  public void setTries(int value) {
    triesProperty().set(value);
  }

  /**
   * Returns the number of tries property of the table record.
   *
   * @return tries property
   */
  public IntegerProperty triesProperty() {
    if (tries == null) {
      tries = new SimpleIntegerProperty(this, "tries");
    }
    return tries;
  }

  // String representation of best time.

  public int getCompletions() {
    return completionsProperty().get();
  }

  public void setCompletions(int value) {
    completionsProperty().set(value);
  }

  /**
   * Returns the number of completions property of the table record.
   *
   * @return completions property
   */
  public IntegerProperty completionsProperty() {
    if (completions == null) {
      completions = new SimpleIntegerProperty(this, "completions");
    }
    return completions;
  }

  public String getBestTime() {
    return bestTimeProperty().get();
  }

  // User with best time.

  public void setBestTime(String value) {
    bestTimeProperty().set(value);
  }

  /**
   * Returns the best time property of the table record.
   *
   * @return best time property
   */
  public StringProperty bestTimeProperty() {
    if (bestTime == null) {
      bestTime = new SimpleStringProperty(this, "bestTime");
    }
    return bestTime;
  }

  public String getByUser() {
    return byUserProperty().get();
  }

  public void setByUser(String value) {
    byUserProperty().set(value);
  }

  /**
   * Returns the best time's user property of the table record.
   *
   * @return best time's user
   */
  public StringProperty byUserProperty() {
    if (byUser == null) {
      byUser = new SimpleStringProperty(this, "byUser");
    }
    return byUser;
  }
}
