package owlngo.communication;

import java.util.List;
import owlngo.communication.messages.ConnectedNotification;
import owlngo.communication.messages.ConnectionRequest;
import owlngo.communication.messages.LevelNamesNotification;
import owlngo.communication.messages.LevelSavedNotification;
import owlngo.communication.messages.LoadLevelNamesRequest;
import owlngo.communication.messages.LoadLevelRequest;
import owlngo.communication.messages.Message;
import owlngo.communication.messages.SaveLevelRequest;
import owlngo.communication.messages.SendLevelNotification;
import owlngo.communication.savefiles.LevelSavefile;
import owlngo.communication.savefiles.Savefile;
import owlngo.communication.utils.MessageCoder;
import owlngo.communication.utils.SavefileCoder;
import owlngo.game.OwlnGo;
import owlngo.game.level.Level;

/** Mock tests for fast testing. Later on, JUnit should be used for this. */
public final class TestMain {

  private static final OwlnGo GAME = new OwlnGo(Level.createDemoLevel(10, 10));
  private static final List<List<String>> LEVEL_NAMES =
      List.of(List.of("DEMO1", "void"), List.of("DEMO2", "VOID"), List.of("DEMO3", "VOID"));

  /**
   * Testing JSON encoding.
   *
   * @param args unused
   */
  public static void main(String[] args) {
    final Level testLevel = GAME.getGameState().getLevel();

    System.out.println("ENCODING");
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
    final LoadLevelRequest loadLevelRequest = new LoadLevelRequest("PLAYER", "dummyLevel");
    final String loadLevelRequestJson = MessageCoder.encodeToJson(loadLevelRequest);
    System.out.println(loadLevelRequestJson);

    System.out.println("SaveLevelRequest:");
    final SaveLevelRequest saveLevelRequest =
        new SaveLevelRequest("PLAYER", "DEFAULT_LEVEL", testLevel);
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
        new SendLevelNotification("DEMO1", testLevel);
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

    System.out.println("DECODING");
    System.out.println("Testing client messages.\n");

    System.out.println("ConnectionRequest:");
    System.out.println(connectionRequestJson);
    final Message connectionRequestMessage = MessageCoder.decodeFromJson(connectionRequestJson);
    final ConnectionRequest parsedConnectionRequest = (ConnectionRequest) connectionRequestMessage;
    System.out.println(parsedConnectionRequest.getPlayerName());

    System.out.println("LoadLevelNamesRequest:");
    System.out.println(loadLevelNamesRequestJson);
    final Message loadLevelNamesRequestMessage =
        MessageCoder.decodeFromJson(loadLevelNamesRequestJson);
    final LoadLevelNamesRequest parsedLoadLevelNamesRequest =
        (LoadLevelNamesRequest) loadLevelNamesRequestMessage;
    System.out.println(parsedLoadLevelNamesRequest.getPlayerName());

    System.out.println("LoadLevelRequest:");
    System.out.println(loadLevelRequestJson);
    final Message loadLevelRequestMessage = MessageCoder.decodeFromJson(loadLevelRequestJson);
    final LoadLevelRequest parsedLoadLevelRequest = (LoadLevelRequest) loadLevelRequestMessage;
    System.out.println(parsedLoadLevelRequest.getPlayerName());
    final String loadLevelName = parsedLoadLevelRequest.getLevelName();
    System.out.println(loadLevelName); // for debugging reasons

    System.out.println("SaveLevelRequest:");
    System.out.println(saveLevelRequestJson);
    final Message saveLevelRequestMessage = MessageCoder.decodeFromJson(saveLevelRequestJson);
    final SaveLevelRequest parsedSaveLevelRequest = (SaveLevelRequest) saveLevelRequestMessage;
    System.out.println(parsedSaveLevelRequest.getLevelName());
    System.out.println(parsedSaveLevelRequest.getAuthor());
    final Level saveLevel = parsedSaveLevelRequest.getLevel();
    System.out.println(saveLevel); // for debugging reasons

    System.out.println("\nTesting server messages.\n");

    System.out.println("ConnectedNotification:");
    System.out.println(connectedNotificationJson);
    final Message connectedNotificationMessage =
        MessageCoder.decodeFromJson(connectedNotificationJson);
    final ConnectedNotification parsedConnectedNotification =
        (ConnectedNotification) connectedNotificationMessage;
    System.out.println(parsedConnectedNotification.getPlayerName());

    System.out.println("LevelNamesNotification:");
    System.out.println(levelNamesNotificationJson);
    final Message levelNamesNotificationMessage =
        MessageCoder.decodeFromJson(levelNamesNotificationJson);
    final LevelNamesNotification parsedLevelNamesNotification =
        (LevelNamesNotification) levelNamesNotificationMessage;
    final List<List<String>> levelNames = parsedLevelNamesNotification.getLevelNames();
    System.out.println(levelNames); // for debugging reasons

    System.out.println("SendLevelNotification:");
    System.out.println(sendLevelNotificationJson);
    final Message sendLevelNotificationMessage =
        MessageCoder.decodeFromJson(sendLevelNotificationJson);
    final SendLevelNotification parsedSendLevelNotification =
        (SendLevelNotification) sendLevelNotificationMessage;
    System.out.println(parsedSendLevelNotification.getLevelName());
    final Level sendLevel = parsedSendLevelNotification.getLevel();
    System.out.println(sendLevel); // for debugging reasons

    System.out.println("LevelSavedNotification:");
    System.out.println(levelSavedNotificationJson);
    final Message levelSavedNotificationMessage =
        MessageCoder.decodeFromJson(levelSavedNotificationJson);
    final LevelSavedNotification parsedLevelSavedNotification =
        (LevelSavedNotification) levelSavedNotificationMessage;
    System.out.println(parsedLevelSavedNotification.getLevelName());

    System.out.println("\nTesting savefiles.\n");

    System.out.println(levelSavefileJson);
    final Savefile decodedLevelSavefile = SavefileCoder.decodeFromJson(levelSavefileJson);
    final LevelSavefile parsedLevelSavefile = (LevelSavefile) decodedLevelSavefile;
    System.out.println(parsedLevelSavefile.getAuthor());
    System.out.println(parsedLevelSavefile.getLevelName());
    final Level savedLevel = parsedLevelSavefile.getLevel();
    System.out.println(savedLevel); // for debugging reasons
  }
}
