package owlngo.gui.playfield;

/**
 * This class is used to start the welcome window as a gradle task including the JavaFX libraries.
 */
public class GameScreenMain {
  // TODO: Copy this class to your window package and adjust it as explained.

  /*
   * This is a template for the main class needed to start a JavaFX window from a gradle task.
   *
   * Example: Let's say your class extending Application is named 'GameScreen' and it has the
   * following main method:
   *
   * public class GameScreen extends Application {
   *
   *   public static void main(String[] args) {
   *     launch(args);
   *   }
   *
   *   // ... snip ...
   * }
   *
   * Then, the main method of the properly named class 'GameScreenMain' should look like this:
   *
   * public class GameScreenMain {
   *
   *   public static void main(String[] args) {
   *     GameScreen.main(args);
   *   }
   * }
   *
   * Now you can adjust the task in build.gradle to use this class 'GameScreenMain' to run as
   * a gradle task (see build.gradle for the task template)
   *
   */

  public static void main(String[] args) {
    GameScreen.main(args);
  }
}
