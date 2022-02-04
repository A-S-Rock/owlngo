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

  public void setLevelName(String value) {
    levelNameProperty().set(value);
  }

  public String getLevelName() {
    return levelNameProperty().get();
  }

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

  // Number of tries.

  private IntegerProperty tries;

  public void setTries(int value) {
    triesProperty().set(value);
  }

  public int getTries() {
    return triesProperty().get();
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

  // Number of completions.

  private IntegerProperty completions;

  public void setCompletions(int value) {
    completionsProperty().set(value);
  }

  public int getCompletions() {
    return completionsProperty().get();
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

  // String representation of best time.

  private StringProperty bestTime;

  public void setBestTime(String value) {
    bestTimeProperty().set(value);
  }

  public String getBestTime() {
    return bestTimeProperty().get();
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

  // User with best time.

  private StringProperty byUser;

  public void setByUser(String value) {
    byUserProperty().set(value);
  }

  public String getByUser() {
    return byUserProperty().get();
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
