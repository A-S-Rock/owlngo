package owlngo.gui.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/** This class is used to get a data model for the load level table view. */
public final class LoadLevelRecord {
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

  public LoadLevelRecord(String firstName, String lastName) {
    setLevelName(firstName);
    setAuthor(lastName);
  }
}
