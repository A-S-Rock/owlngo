package owlngo.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import owlngo.communication.savefiles.LevelSavefile;
import owlngo.communication.savefiles.LevelStatsSavefile;
import owlngo.communication.utils.SavefileCoder;
import owlngo.game.level.Coordinate;
import owlngo.game.level.Level;

/** Manages creating and loading savefiles for levels, highscores and more. */
public class SavefileManager {

  private static final int TRIES_NEW_LEVEL = 0;
  private static final int COMPLETIONS_NEW_LEVEL = 0;
  private static final String BEST_TIME_NEW_LEVEL = "59:59:99";

  private static final Path SAVEFILE_LEVEL_PATH = Paths.get("src/main/resources/savefiles/level");
  private static final Path SAVEFILE_STATS_PATH = Paths.get("src/main/resources/savefiles/stats");

  private static final String LEVEL_SAVEFILE_APPENDIX = ".txt";
  private static final String STATS_SAVEFILE_APPENDIX = "_stats.txt";

  private static final int LEVEL_SAVEFILE_APPENDIX_LENGTH = LEVEL_SAVEFILE_APPENDIX.length(); // = 4
  private static final int STATS_SAVEFILE_APPENDIX_LENGTH =
      STATS_SAVEFILE_APPENDIX.length(); // = 10

  private final Map<String, LevelSavefile> savedLevels = new HashMap<>();
  private final Map<String, LevelStatsSavefile> savedStats = new HashMap<>();

  /**
   * Creates the savefile manager. Checks if directories for savefiles are present and creates them
   * if not. Afterwards, if not already present, three dummy level files are created for initial
   * play including their stats. If these dummy savefiles are present at initiation, they get
   * overwritten by the defoult ones (if tampered with).
   */
  public SavefileManager() {
    instantiateDirectories();
    createDummySavefiles();
    updateSavedLevels();
    updateSavedStats();
  }

  /* Private helper methods */

  /** Setting up directories for saves. */
  private void instantiateDirectories() {
    try {
      Files.createDirectories(SAVEFILE_LEVEL_PATH);
      Files.createDirectories(SAVEFILE_STATS_PATH);
    } catch (IOException e) {
      System.err.println("Couldn't create savefile directories!");
      e.printStackTrace();
    }
  }

  /** Create three new dummy levels including their stats. */
  private void createDummySavefiles() {
    final String dummyLevelAuthor = "VOID";

    final String levelNameDummyLevel1 = "dummy1";
    final Level dummyLevel1 = Level.createDemoLevel(10, 10);

    final String levelNameDummyLevel2 = "dummy2";
    Level dummyLevel2 = Level.createDemoLevel(15, 15);
    for (int column = 1; column <= 9; column++) {
      if (column % 3 == 0) {
        final Coordinate coordinate = Coordinate.of(13, column);
        dummyLevel2 = dummyLevel2.withGroundAt(coordinate);
      }
    }
    dummyLevel2 = dummyLevel2.withFoodAt(Coordinate.of(13, 5));

    final String levelNameDummyLevel3 = "dummy3";
    Level dummyLevel3 = Level.createDemoLevel(30, 30);
    for (int column = 6; column < dummyLevel3.getNumColumns(); column++) {
      if (column % 4 != 0 && column != 29) {
        final Coordinate coordinate = Coordinate.of(28, column);
        dummyLevel3 = dummyLevel3.withGroundAt(coordinate);
      }
      if (column % 5 == 0) {
        final Coordinate coordinate = Coordinate.of(27, column);
        dummyLevel3 = dummyLevel3.withGroundAt(coordinate);
      }
    }
    dummyLevel3 = dummyLevel3.withFireAt(Coordinate.of(28, 5));

    writeLevelSavefile(levelNameDummyLevel1, dummyLevelAuthor, dummyLevel1);
    createNewStatsForLevel(levelNameDummyLevel1, dummyLevelAuthor);

    writeLevelSavefile(levelNameDummyLevel2, dummyLevelAuthor, dummyLevel2);
    createNewStatsForLevel(levelNameDummyLevel2, dummyLevelAuthor);

    writeLevelSavefile(levelNameDummyLevel3, dummyLevelAuthor, dummyLevel3);
    createNewStatsForLevel(levelNameDummyLevel3, dummyLevelAuthor);
  }

  /**
   * Creates a level stat with initial values for tries, completions and best times:<br>
   * <br>
   * <code>tries = 0</code>,<br>
   * <code>completions = 0</code> and<br>
   * <code>best time = "59:59:99"</code>.
   *
   * @param levelName level name for the new level stat
   * @param username username of the stat creator
   */
  public void createNewStatsForLevel(String levelName, String username) {
    writeLevelStatsSavefile(
        levelName, TRIES_NEW_LEVEL, COMPLETIONS_NEW_LEVEL, BEST_TIME_NEW_LEVEL, username);
  }

  // Updating methods for level saves.

  private void updateSavedLevels() {
    final File levelDir = new File(SAVEFILE_LEVEL_PATH.toString());
    File[] directoryListing = levelDir.listFiles();
    if (directoryListing != null) {
      for (File child : directoryListing) {
        final String fileName = child.getName();
        final String levelName =
            fileName.substring(0, fileName.length() - LEVEL_SAVEFILE_APPENDIX_LENGTH);
        try {
          updateLevelSavefile(levelName);
        } catch (IOException e) {
          throw new AssertionError("This should not happen! " + e.getMessage());
        }
      }
    }
  }

  private void updateLevelSavefile(String levelName) throws IOException {
    final String filename = "/savefiles/level/" + levelName + LEVEL_SAVEFILE_APPENDIX;

    try (InputStream inputStream =
        Objects.requireNonNull(getClass().getResourceAsStream(filename))) {
      final String savefileJson = readFromInputStream(inputStream);
      final LevelSavefile savefile = (LevelSavefile) SavefileCoder.decodeFromJson(savefileJson);
      savedLevels.put(levelName, savefile);
    }
  }

  // Updating methods for stat saves.

  private void updateSavedStats() {
    final File levelDir = new File(SAVEFILE_STATS_PATH.toString());
    File[] directoryListing = levelDir.listFiles();
    if (directoryListing != null) {
      for (File child : directoryListing) {
        final String fileName = child.getName();
        final String levelName =
            fileName.substring(0, fileName.length() - STATS_SAVEFILE_APPENDIX_LENGTH);
        try {
          updateLevelStatsSavefile(levelName);
        } catch (IOException e) {
          throw new AssertionError("This should not happen! " + e.getMessage());
        }
      }
    }
  }

  private void updateLevelStatsSavefile(String levelName) throws IOException {
    final String filename = "/savefiles/stats/" + levelName + STATS_SAVEFILE_APPENDIX;

    try (InputStream inputStream =
        Objects.requireNonNull(getClass().getResourceAsStream(filename))) {
      final String savefileJson = readFromInputStream(inputStream);
      final LevelStatsSavefile savefile =
          (LevelStatsSavefile) SavefileCoder.decodeFromJson(savefileJson);
      savedStats.put(levelName, savefile);
    }
  }

  private String readFromInputStream(InputStream inputStream) throws IOException {
    StringBuilder result = new StringBuilder();
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
      String line;
      while ((line = reader.readLine()) != null) {
        result.append(line).append(System.lineSeparator());
      }
    }
    return result.toString();
  }

  /* Public methods for savefiles */

  /**
   * Writes a level savefile with the given level name as file name. Also sets the author.
   *
   * @param levelName level name of the level layout
   * @param author creator player's name
   * @param level the level layout
   */
  public void writeLevelSavefile(String levelName, String author, Level level) {
    final LevelSavefile savefile = new LevelSavefile(levelName, author, level);
    final String savefileJson = SavefileCoder.encodeToJson(savefile);

    final String fileName = levelName + LEVEL_SAVEFILE_APPENDIX;
    final String directoryName = SAVEFILE_LEVEL_PATH.toString();

    final File file = new File(directoryName + File.separator + fileName);
    try {
      BufferedWriter writer =
          new BufferedWriter(new FileWriter(file.getAbsoluteFile(), StandardCharsets.UTF_8));
      writer.write(savefileJson);

      if (!savedLevels.containsKey(levelName)) {
        createNewStatsForLevel(levelName, author);
      }

      savedLevels.put(levelName, savefile);
      writer.close();
    } catch (IOException e) {
      System.err.println("Couldn't create file " + fileName + "!");
      e.printStackTrace();
    }
  }

  /**
   * Writes a level stats savefile with the given level name as file name. Also sets the author.
   *
   * @param levelName level name of the level layout
   * @param tries number of tries
   * @param completions number of successful tries
   * @param bestTime current best time
   * @param byUser user who made the current best time
   */
  public void writeLevelStatsSavefile(
      String levelName, int tries, int completions, String bestTime, String byUser) {
    final LevelStatsSavefile statsSavefile =
        new LevelStatsSavefile(levelName, tries, completions, bestTime, byUser);
    final String statsSavefileJson = SavefileCoder.encodeToJson(statsSavefile);

    final String fileName = levelName + STATS_SAVEFILE_APPENDIX;
    final String directoryName = SAVEFILE_STATS_PATH.toString();

    final File file = new File(directoryName + File.separator + fileName);
    try {
      BufferedWriter writer =
          new BufferedWriter(new FileWriter(file.getAbsoluteFile(), StandardCharsets.UTF_8));
      writer.write(statsSavefileJson);
      savedStats.put(levelName, statsSavefile);
      writer.close();
    } catch (IOException e) {
      System.err.println("Couldn't create file " + fileName + "!");
      e.printStackTrace();
    }
  }

  public Map<String, LevelSavefile> getSavedLevels() {
    return new HashMap<>(savedLevels);
  }

  public Map<String, LevelStatsSavefile> getSavedStats() {
    return new HashMap<>(savedStats);
  }

  /**
   * Loads the level of the current saved levels in the server.
   *
   * @param levelName the level to be loaded by name
   * @return the requested level
   */
  public Level loadLevelSavefile(String levelName) {
    return savedLevels.get(levelName).getLevel();
  }
}
