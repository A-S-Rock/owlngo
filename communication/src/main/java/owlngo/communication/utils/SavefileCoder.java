package owlngo.communication.utils;

import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory;
import java.io.IOException;
import owlngo.communication.savefiles.LevelSavefile;
import owlngo.communication.savefiles.Savefile;
import owlngo.game.level.objects.LevelObject;
import owlngo.game.level.objects.ObjectInGame;
import owlngo.game.level.objects.Player;

/** Serves as a utility class to parse data from the game into a compact JSON format. */
public final class SavefileCoder {

  private static final Moshi moshi =
      new Moshi.Builder()
          .add(
              PolymorphicJsonAdapterFactory.of(Savefile.class, "savefileType")
                  .withSubtype(LevelSavefile.class, "LevelSavefile"))
          .add(
              PolymorphicJsonAdapterFactory.of(ObjectInGame.class, "objectJsonType")
                  .withSubtype(Player.class, "player")
                  .withSubtype(LevelObject.class, "levelObject"))
          .build();

  /**
   * Encodes a given {@link Savefile} to a JSON String.
   *
   * @param savefile the given {@link Savefile}
   * @return a JSON String representation of the {@link Savefile} object
   */
  public static String encodeToJson(Savefile savefile) {
    return moshi.adapter(Savefile.class).toJson(savefile);
  }

  /**
   * Decondes a given JSON String of a Message object.
   *
   * @param savefileJson the JSON representation of a {@link Savefile} object
   * @return the actual {@link Savefile} object
   */
  public static Savefile decodeFromJson(String savefileJson) {
    try {
      return moshi.adapter(Savefile.class).fromJson(savefileJson);
    } catch (IOException e) {
      throw new AssertionError("Failed to parse savefile.");
    }
  }
}
