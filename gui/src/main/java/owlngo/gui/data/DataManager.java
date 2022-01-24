package owlngo.gui.data;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import owlngo.game.level.Level;

/** Makes data transfer between different JavaFX windows possible with the use as a singleton. */
@SuppressFBWarnings({"MS_EXPOSE_REP", "EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public class DataManager {

  private static DataManager instance;
  private Level levelContent;

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

  public Level getLevelContent() {
    return levelContent;
  }

  public void setLevelContent(Level levelContent) {
    this.levelContent = levelContent;
  }
}
