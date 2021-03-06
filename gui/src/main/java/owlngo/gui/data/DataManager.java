package owlngo.gui.data;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import owlngo.game.level.Level;
import owlngo.gui.gamefield.view.ViewUtils;

/** Makes data transfer between different JavaFX windows possible with the use as a singleton. */
@SuppressFBWarnings({"MS_EXPOSE_REP", "EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public class DataManager {

  private static DataManager instance;
  private Level levelContent;
  private String levelNameContent;
  private List<List<String>> levelNamesContent;
  private List<List<String>> levelStatsContent;
  private SimpleStringProperty timeStringProperty;

  private DataManager() {
    levelNameContent = "DEFAULT";
  }

  /**
   * Instantiates the single instance of the DataManager or returns it if already existant.
   *
   * @return the DataManager's instance
   */
  public static synchronized DataManager getInstance() {
    if (instance == null) {
      instance = new DataManager();
    }
    return instance;
  }

  /**
   * Provides a getter for a level-copy or standard-level.
   *
   * @return a copy of a given level or creates a new demo level
   */
  public Level getLevelContent() {
    return Objects.requireNonNullElseGet(
            levelContent,
            () -> Level.createDemoLevel(ViewUtils.NUM_LEVEL_ROWS, ViewUtils.NUM_LEVEL_COLUMNS))
        .copyOf();
  }

  public void setLevelContent(Level levelContent) {
    this.levelContent = levelContent;
  }

  public String getLevelNameContent() {
    return levelNameContent;
  }

  public void setLevelNameContent(String levelNameContent) {
    this.levelNameContent = levelNameContent;
  }

  public List<List<String>> getLevelNamesContent() {
    return levelNamesContent;
  }

  public void setLevelNamesContent(List<List<String>> levelNamesContent) {
    this.levelNamesContent = levelNamesContent;
  }

  public List<List<String>> getLevelStatsContent() {
    return levelStatsContent;
  }

  public void setLevelStatsContent(List<List<String>> levelStatsContent) {
    this.levelStatsContent = levelStatsContent;
  }

  public SimpleStringProperty getTimeStringProperty() {
    return timeStringProperty;
  }

  public void setTimeStringProperty(SimpleStringProperty timeStringProperty) {
    this.timeStringProperty = timeStringProperty;
  }
}
