package owlngo.gui.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class is used to get a data model for the load level table view. See <a
 * href="https://openjfx.io/javadoc/17/javafx.controls/javafx/scene/control/TableView.html">JavaFX
 * doc for TableView</a>.
 */
public final class LoadLevelRecord {

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

  // Author.

  private StringProperty author;

  public void setAuthor(String value) {
    authorProperty().set(value);
  }

  public String getAuthor() {
    return authorProperty().get();
  }

  /**
   * Returns the author property of the table record.
   *
   * @return the author property
   */
  public StringProperty authorProperty() {
    if (author == null) {
      author = new SimpleStringProperty(this, "author");
    }
    return author;
  }

  public LoadLevelRecord(String levelName, String author) {
    setLevelName(levelName);
    setAuthor(author);
  }
}
