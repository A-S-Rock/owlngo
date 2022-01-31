package owlngo.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** Manages creating and loading savefiles for levels, highscores and more. */
public class SavefileManager {

  private static final String RESOURCES_PATH = "server/src/main/resources";
  private static final String RESOURCES_ABSOLUTE_PATH = new File(RESOURCES_PATH).getPath();

  private static final Path SAVEFILE_LEVEL_PATH =
      Paths.get(RESOURCES_ABSOLUTE_PATH + "/savefiles/level");
  private static final Path SAVEFILE_HIGHSCORE_PATH =
      Paths.get(RESOURCES_ABSOLUTE_PATH + "/savefiles/highscores");

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

  public static void main(String[] args) {
    new SavefileManager();
  }
}
