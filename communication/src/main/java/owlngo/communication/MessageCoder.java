package owlngo.communication;

import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory;
import java.io.IOException;
import owlngo.communication.messages.ConnectedNotification;
import owlngo.communication.messages.ConnectionRequest;
import owlngo.communication.messages.LevelNamesNotification;
import owlngo.communication.messages.LevelSavedNotification;
import owlngo.communication.messages.LoadLevelNamesRequest;
import owlngo.communication.messages.LoadLevelRequest;
import owlngo.communication.messages.Message;
import owlngo.communication.messages.SaveLevelRequest;
import owlngo.communication.messages.SendLevelNotification;

/** Serves as a JSON encoder and decoder to all {@link Message} - implementing classes. */
public final class MessageCoder {

  private static final Moshi moshi =
      new Moshi.Builder()
          .add(
              PolymorphicJsonAdapterFactory.of(Message.class, "messageType")
                  .withSubtype(ConnectionRequest.class, "ConnectionRequest")
                  .withSubtype(LoadLevelNamesRequest.class, "LoadLevelNamesRequest")
                  .withSubtype(LoadLevelRequest.class, "LoadLevelRequest")
                  .withSubtype(SaveLevelRequest.class, "SaveLevelRequest")
                  .withSubtype(ConnectedNotification.class, "ConnectedNotification")
                  .withSubtype(LevelNamesNotification.class, "LevelNamesNotification")
                  .withSubtype(SendLevelNotification.class, "SendLevelNotification")
                  .withSubtype(LevelSavedNotification.class, "LevelSavedNotification"))
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
   * @throws IOException if an error happens while parsing
   */
  public static Message decodeFromJson(String messageJson) throws IOException {
    return moshi.adapter(Message.class).fromJson(messageJson);
  }
}
