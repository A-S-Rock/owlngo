package owlngo.communication;

import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory;
import java.io.IOException;
import owlngo.communication.messages.Message;
import owlngo.communication.savefiles.LevelSavefile;
import owlngo.communication.savefiles.Savefile;

/** Serves as a utility class to parse data from the game into a compact JSON format. */
public final class SavefileCoder {

  private static final Moshi moshi =
      new Moshi.Builder()
          .add(
              PolymorphicJsonAdapterFactory.of(Savefile.class, "savefileType")
                  .withSubtype(LevelSavefile.class, "LevelSavefile"))
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
