package owlngo.communication.utils;

import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory;
import java.io.IOException;
import owlngo.communication.messages.ConnectedNotification;
import owlngo.communication.messages.ConnectionRequest;
import owlngo.communication.messages.LevelInfosNotification;
import owlngo.communication.messages.LevelSavedNotification;
import owlngo.communication.messages.LoadLevelInfosRequest;
import owlngo.communication.messages.LoadLevelRequest;
import owlngo.communication.messages.Message;
import owlngo.communication.messages.SaveLevelRequest;
import owlngo.communication.messages.SendLevelNotification;
import owlngo.game.level.objects.LevelObject;
import owlngo.game.level.objects.ObjectInGame;
import owlngo.game.level.objects.Player;

/** Serves as a JSON encoder and decoder to all {@link Message} - implementing classes. */
public final class MessageCoder {

  private static final Moshi moshi =
      new Moshi.Builder()
          .add(
              PolymorphicJsonAdapterFactory.of(Message.class, "messageType")
                  .withSubtype(ConnectionRequest.class, "ConnectionRequest")
                  .withSubtype(LoadLevelInfosRequest.class, "LoadLevelInfosRequest")
                  .withSubtype(LoadLevelRequest.class, "LoadLevelRequest")
                  .withSubtype(SaveLevelRequest.class, "SaveLevelRequest")
                  .withSubtype(ConnectedNotification.class, "ConnectedNotification")
                  .withSubtype(LevelInfosNotification.class, "LevelNamesNotification")
                  .withSubtype(SendLevelNotification.class, "SendLevelNotification")
                  .withSubtype(LevelSavedNotification.class, "LevelSavedNotification"))
          .add(
              PolymorphicJsonAdapterFactory.of(ObjectInGame.class, "objectJsonType")
                  .withSubtype(Player.class, "player")
                  .withSubtype(LevelObject.class, "levelObject"))
          .build();

  /**
   * Encodes a given {@link Message} to a JSON String.
   *
   * @param message the given Message
   * @return a JSON String representation of the {@link Message} object
   */
  public static String encodeToJson(Message message) {
    return moshi.adapter(Message.class).toJson(message);
  }

  /**
   * Decondes a given JSON String of a Message object.
   *
   * @param messageJson the JSON representation of a {@link Message} object
   * @return the actual {@link Message} object
   */
  public static Message decodeFromJson(String messageJson) {
    try {
      return moshi.adapter(Message.class).fromJson(messageJson);
    } catch (IOException e) {
      throw new AssertionError("Failed to parse Message JSON.");
    }
  }
}
