package owlngo.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import owlngo.communication.savefiles.LevelSavefile;
import owlngo.communication.utils.SavefileCoder;
import owlngo.game.level.Coordinate;
import owlngo.game.level.Level;

/** Manages creating and loading savefiles for levels, highscores and more. */
public class SavefileManager {

  private static final Path SAVEFILE_LEVEL_PATH =
      Paths.get("server/src/main/resources/savefiles/level");
  private static final Path SAVEFILE_HIGHSCORE_PATH =
      Paths.get("server/src/main/resources/savefiles/highscores");

  /**
   * Creates the savefile manager. Checks if directories for savefiles are present and creates them
   * if not. Afterwards, if not already present, three dummy level files are created for initial
   * play. If these dummy savefiles are present at initiation, they get overwritten by the defoult
   * ones (if tampered with).
   */
  public SavefileManager() {
    instantiateDirectories();
    createDummySavefiles();
  }

  private void instantiateDirectories() {
    try {
      Files.createDirectories(SAVEFILE_LEVEL_PATH);
      Files.createDirectories(SAVEFILE_HIGHSCORE_PATH);
      System.out.println("Created savefile directories!");
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
    final int thirdRow = 2;
    for (int column = 1; column < dummyLevel2.getNumColumns(); column++) {
      if (column % 3 == 0) {
        final Coordinate coordinate = Coordinate.of(thirdRow, column);
        dummyLevel2 = dummyLevel2.withGroundAt(coordinate);
      }
    }

    final String levelNameDummyLevel3 = "dummy3";
    Level dummyLevel3 = Level.createDemoLevel(30, 30);
    final int fourthRow = 3;
    for (int column = 6; column < dummyLevel3.getNumColumns(); column++) {
      if (column % 4 != 0) {
        final Coordinate coordinate = Coordinate.of(thirdRow, column);
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
      BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
      writer.write(savefileJson);
      writer.close();
    } catch (IOException e) {
      System.err.println("Couldn't create file " + fileName + "!");
      e.printStackTrace();
    }
  }

  public Level loadLevelSavefile(String levelName) throws IOException {
    final String filename = "/savefiles/level/" + levelName + ".txt";
    final InputStream inputStream = getClass().getResourceAsStream(filename);

    if (inputStream != null) {
      final String savefileJson = readFromInputStream(inputStream);
      final LevelSavefile savefile = (LevelSavefile) SavefileCoder.decodeFromJson(savefileJson);
      return savefile.getLevel();
    } else {
      throw new AssertionError("Couldn't create input stream for reading!");
    }
  }

  private String readFromInputStream(InputStream inputStream) throws IOException {
    StringBuilder result = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      while ((line = reader.readLine()) != null) {
        result.append(line).append(System.lineSeparator());
      }
    }
    return result.toString();
  }
}
