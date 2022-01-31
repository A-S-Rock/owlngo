package owlngo.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import owlngo.communication.messages.SaveLevelRequest;
import owlngo.communication.savefiles.LevelSavefile;
import owlngo.communication.utils.SavefileCoder;
import owlngo.game.level.Level;

/** Manages creating and loading savefiles for levels, highscores and more. */
public class SavefileManager {

  private static final Path SAVEFILE_LEVEL_PATH =
      Paths.get("server/src/main/resources/savefiles/level");
  private static final Path SAVEFILE_HIGHSCORE_PATH =
      Paths.get("server/src/main/resources/savefiles/highscores");

  public SavefileManager() {
    try {
      Files.createDirectories(SAVEFILE_LEVEL_PATH);
      Files.createDirectories(SAVEFILE_HIGHSCORE_PATH);
      System.out.println("Created savefile directories!");
    } catch (IOException e) {
      System.err.println("Couldn't create savefile directories!");
      e.printStackTrace();
    }
  }

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

  public static void main(String[] args) {
    final SavefileManager manager = new SavefileManager();
    final Level level = Level.createDemoLevel(10, 10);
    final SaveLevelRequest saveLevelRequest = new SaveLevelRequest("playerName", "Dummy1", level);

    final String authorName = saveLevelRequest.getAuthor();
    final String levelName = saveLevelRequest.getLevelName();
    final Level sentLevel = saveLevelRequest.getLevel();

    manager.writeLevelSavefile(levelName, authorName, sentLevel);
  }
}
