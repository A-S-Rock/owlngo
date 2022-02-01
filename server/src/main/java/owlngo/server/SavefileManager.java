package owlngo.server;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
import owlngo.communication.savefiles.LevelSavefile;
import owlngo.communication.utils.SavefileCoder;
import owlngo.game.level.Coordinate;
import owlngo.game.level.Level;

/** Manages creating and loading savefiles for levels, highscores and more. */
public class SavefileManager {

  private static final Path SAVEFILE_LEVEL_PATH = Paths.get("src/main/resources/savefiles/level");
  private static final Path SAVEFILE_HIGHSCORE_PATH =
      Paths.get("src/main/resources/savefiles/highscores");

  private static final int SAVEFILE_APPENDIX_LENGTH = 4; // ".txt" length

  private final Map<String, LevelSavefile> savedLevels = new HashMap<>();

  /**
   * Creates the savefile manager. Checks if directories for savefiles are present and creates them
   * if not. Afterwards, if not already present, three dummy level files are created for initial
   * play. If these dummy savefiles are present at initiation, they get overwritten by the defoult
   * ones (if tampered with).
   */
  public SavefileManager() {
    instantiateDirectories();
    createDummySavefiles();
    updateSavedLevels();
  }

  private void instantiateDirectories() {
    try {
      Files.createDirectories(SAVEFILE_LEVEL_PATH);
      Files.createDirectories(SAVEFILE_HIGHSCORE_PATH);
    } catch (IOException e) {
      System.err.println("Couldn't create savefile directories!");
      e.printStackTrace();
    }
  }

  private void createDummySavefiles() {
    final String dummyLevelAuthor = "Void";

    final String levelNameDummyLevel1 = "dummy1";
    final Level dummyLevel1 = Level.createDemoLevel(10, 10);

    final String levelNameDummyLevel2 = "dummy2";
    Level dummyLevel2 = Level.createDemoLevel(15, 15);
    final int dummyLevel2MaxRow = dummyLevel2.getNumRows() - 1;
    final int dummyLevel2SecondRow = dummyLevel2MaxRow - 1;
    for (int column = 1; column <= dummyLevel2MaxRow; column++) {
      if (column % 3 == 0) {
        final Coordinate coordinate = Coordinate.of(dummyLevel2SecondRow, column);
        dummyLevel2 = dummyLevel2.withGroundAt(coordinate);
      }
    }

    final String levelNameDummyLevel3 = "dummy3";
    Level dummyLevel3 = Level.createDemoLevel(30, 30);
    final int fourthRow = 3;
    for (int column = 6; column < dummyLevel3.getNumColumns(); column++) {
      if (column % 4 != 0) {
        final Coordinate coordinate = Coordinate.of(dummyLevel2SecondRow, column);
        dummyLevel3 = dummyLevel3.withGroundAt(coordinate);
      }
      if (column % 5 == 0) {
        final Coordinate coordinate = Coordinate.of(fourthRow, column);
        dummyLevel3 = dummyLevel3.withGroundAt(coordinate);
      }
    }

    writeLevelSavefile(levelNameDummyLevel1, dummyLevelAuthor, dummyLevel1);
    writeLevelSavefile(levelNameDummyLevel2, dummyLevelAuthor, dummyLevel2);
    writeLevelSavefile(levelNameDummyLevel3, dummyLevelAuthor, dummyLevel3);
  }

  private void updateSavedLevels() {
    final File levelDir = new File(SAVEFILE_LEVEL_PATH.toString());
    File[] directoryListing = levelDir.listFiles();
    if (directoryListing != null) {
      for (File child : directoryListing) {
        final String fileName = child.getName();
        final String levelName =
            fileName.substring(0, fileName.length() - SAVEFILE_APPENDIX_LENGTH);
        try {
          loadAndUpdateLevelSavefile(levelName);
        } catch (IOException e) {
          throw new AssertionError("This should not happen! " + e.getMessage());
        }
      }
    }
  }

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

    final String fileName = levelName + ".txt";
    final String directoryName = SAVEFILE_LEVEL_PATH.toString();

    final File file = new File(directoryName + File.separator + fileName);
    try {
      BufferedWriter writer =
          new BufferedWriter(new FileWriter(file.getAbsoluteFile(), StandardCharsets.UTF_8));
      writer.write(savefileJson);
      savedLevels.put(levelName, savefile);
      writer.close();
    } catch (IOException e) {
      System.err.println("Couldn't create file " + fileName + "!");
      e.printStackTrace();
    }
  }

  /**
   * Loads the requested level from storage.
   *
   * @param levelName the requested level's savename.
   * @return loaded level
   * @throws IOException if reading the file failed.
   */
  @SuppressFBWarnings("DM_EXIT")
  public Level loadAndUpdateLevelSavefile(String levelName) throws IOException {
    final String filename = "/savefiles/level/" + levelName + ".txt";

    try (InputStream inputStream = getClass().getResourceAsStream(filename)) {
      if (inputStream == null) {
        System.err.println("Server had to create file directory first. Please restart server!");
        System.exit(0);
      }
      final String savefileJson = readFromInputStream(inputStream);
      final LevelSavefile savefile = (LevelSavefile) SavefileCoder.decodeFromJson(savefileJson);
      savedLevels.put(levelName, savefile);
      return savefile.getLevel();
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

  public Map<String, LevelSavefile> getSavedLevels() {
    return new HashMap<>(savedLevels);
  }
}
