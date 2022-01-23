package owlngo.communication;

import java.util.List;
import owlngo.communication.messages.ConnectedNotification;
import owlngo.communication.messages.ConnectionRequest;
import owlngo.communication.messages.LevelNamesNotification;
import owlngo.communication.messages.LevelSavedNotification;
import owlngo.communication.messages.LoadLevelNamesRequest;
import owlngo.communication.messages.LoadLevelRequest;
import owlngo.communication.messages.SaveLevelRequest;
import owlngo.communication.messages.SendLevelNotification;
import owlngo.communication.savefiles.LevelSavefile;
import owlngo.game.OwlnGo;

/** Mock tests for fast testing. Later on, JUnit should be used for this. */
public final class TestMain {

  private static final OwlnGo GAME = new OwlnGo(10, 10);
  private static final List<String> LEVEL_NAMES = List.of("DEMO1", "DEMO2", "DEMO3");

  /**
   * Testing JSON encoding.
   *
   * @param args unused
   */
  public static void main(String[] args) {
    System.out.println("Testing client messages.\n");

    System.out.println("ConnectionRequest:");
    final ConnectionRequest connectionRequest = new ConnectionRequest("PLAYER");
    final String connectionRequestJson = MessageCoder.encodeToJson(connectionRequest);
    System.out.println(connectionRequestJson);

    System.out.println("LoadLevelNamesRequest:");
    final LoadLevelNamesRequest loadLevelNamesRequest = new LoadLevelNamesRequest("PLAYER");
    final String loadLevelNamesRequestJson = MessageCoder.encodeToJson(loadLevelNamesRequest);
    System.out.println(loadLevelNamesRequestJson);

    System.out.println("LoadLevelRequest:");
    final LoadLevelRequest loadLevelRequest =
        new LoadLevelRequest("PLAYER", GAME.getGameState().getLevel());
    final String loadLevelRequestJson = MessageCoder.encodeToJson(loadLevelRequest);
    System.out.println(loadLevelRequestJson);

    System.out.println("SaveLevelRequest:");
    final SaveLevelRequest saveLevelRequest =
        new SaveLevelRequest("PLAYER", "DEFAULT_LEVEL", GAME.getGameState().getLevel());
    final String saveLevelRequestJson = MessageCoder.encodeToJson(saveLevelRequest);
    System.out.println(saveLevelRequestJson);

    System.out.println("\nTesting server messages.\n");

    System.out.println("ConnectedNotification:");
    final ConnectedNotification connectedNotification = new ConnectedNotification("PLAYER");
    final String connectedNotificationJson = MessageCoder.encodeToJson(connectedNotification);
    System.out.println(connectedNotificationJson);

    System.out.println("LevelNamesNotification:");
    final LevelNamesNotification levelNamesNotification = new LevelNamesNotification(LEVEL_NAMES);
    final String levelNamesNotificationJson = MessageCoder.encodeToJson(levelNamesNotification);
    System.out.println(levelNamesNotificationJson);

    System.out.println("SendLevelNotification:");
    final SendLevelNotification sendLevelNotification =
        new SendLevelNotification("DEMO1", GAME.getGameState().getLevel());
    final String sendLevelNotificationJson = MessageCoder.encodeToJson(sendLevelNotification);
    System.out.println(sendLevelNotificationJson);

    System.out.println("LevelSavedNotification:");
    final LevelSavedNotification levelSavedNotification =
        new LevelSavedNotification("DEFAULT_LEVEL");
    final String levelSavedNotificationJson = MessageCoder.encodeToJson(levelSavedNotification);
    System.out.println(levelSavedNotificationJson);

    System.out.println("\nTesting savefiles.\n");

    final LevelSavefile levelSavefile =
        new LevelSavefile("DEFAULT_LEVEL", "PLAYER", GAME.getGameState().getLevel());
    final String levelSavefileJson = SavefileCoder.encodeToJson(levelSavefile);
    System.out.println(levelSavefileJson);
  }
}
