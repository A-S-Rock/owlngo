package owlngo.gui.data;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import owlngo.game.level.objects.ObjectInGame;

import java.util.Map;

import static owlngo.gui.data.ElementsInPlayfield.ElementInPlayfield.START;


/**
 * In this class static methods are included to hold constant relations. String entered ----
 * ElementInPlayfield S Start E END ....
 *
 * <p>ElementInPlayfield ------------------------ BackgroundFill ElementInPlayfield.OWL
 * -------------------- to the graphic that is stored at resources\C:\\ingo40x40.png"
 * ElementInPlayfield.END----------------------Color RED
 */
public class MethodsForElement {
  // 6.1. 14.20
  // Constant values for window
  public static final int SIZE = 30;

  // NONE,            NO_ELEMENT
  // PLAYER,          OWL
  // START,           START
  // FINISH,          END
  // AIR,             AIR
  // GROUND           GROUND_NO_LAWN

  public static final Map<ObjectInGame.ObjectType, ElementsInPlayfield.ElementInPlayfield>
      OBJECT_TYPE_ELEMENT_IN_PLAYFIELD_MAP =
          Map.ofEntries(
              Map.entry(ObjectInGame.ObjectType.NONE, ElementsInPlayfield.ElementInPlayfield.NO_ELEMENT),
              Map.entry(ObjectInGame.ObjectType.PLAYER, ElementsInPlayfield.ElementInPlayfield.OWL),
              Map.entry(ObjectInGame.ObjectType.START, ElementsInPlayfield.ElementInPlayfield.START),
              Map.entry(ObjectInGame.ObjectType.FINISH, ElementsInPlayfield.ElementInPlayfield.END),
              Map.entry(ObjectInGame.ObjectType.AIR, ElementsInPlayfield.ElementInPlayfield.AIR),
              Map.entry(ObjectInGame.ObjectType.GROUND, ElementsInPlayfield.ElementInPlayfield.GROUND_NO_LAWN)
          );

  static final Map<String, ElementsInPlayfield.ElementInPlayfield> STRING_ELEMENT_IN_PLAYFIELD_MAP =
      Map.ofEntries(
          Map.entry("S", START),
          Map.entry("E", ElementsInPlayfield.ElementInPlayfield.END),
          Map.entry("F", ElementsInPlayfield.ElementInPlayfield.GROUND_NO_LAWN),
          Map.entry("G", ElementsInPlayfield.ElementInPlayfield.GROUND_NO_LAWN),
          Map.entry("O", ElementsInPlayfield.ElementInPlayfield.OWL));

  static final Map<ElementsInPlayfield.ElementInPlayfield, BackgroundFill>
      elementInPlayfieldBackgroundFillMap =
          Map.ofEntries(
              Map.entry(
                  ElementsInPlayfield.ElementInPlayfield.NO_ELEMENT,
                  new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)),
              Map.entry(
                  ElementsInPlayfield.ElementInPlayfield.AIR,
                  new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)),
              Map.entry(
                  ElementsInPlayfield.ElementInPlayfield.OWL,
                  new BackgroundFill(
                      getImagePatternFromFile("/images/ingo40x40.png"),
                      CornerRadii.EMPTY,
                      Insets.EMPTY)),
              Map.entry(
                  ElementsInPlayfield.ElementInPlayfield.GROUND_NO_LAWN,
                  new BackgroundFill(
                      getImagePatternFromFile("/images/soil.png"),
                      CornerRadii.EMPTY,
                      Insets.EMPTY)),
              Map.entry(START,
                  new BackgroundFill(
                      getImagePatternFromFile("/images/start.png"),
                      CornerRadii.EMPTY,
                      Insets.EMPTY)),
              Map.entry(
                  ElementsInPlayfield.ElementInPlayfield.END,
                  new BackgroundFill(
                      getImagePatternFromFile("/images/finish.png"),
                      CornerRadii.EMPTY,
                      Insets.EMPTY)));

  /**
   * Return true, if the key @param inputKey is a key where an element is defined in
   * STRING_ELEMENT_IN_PLAYFIELD_MAP.
   *
   * @param inputKey key entered
   */
  public static boolean validKey(String inputKey) {
    return STRING_ELEMENT_IN_PLAYFIELD_MAP.containsKey(inputKey);
  }

  /**
   * Returns the elementInPlayfield (this variable distinguishes between the different elements that
   * are possible at one position of the playfield).
   *
   * @param inputKey key(letter) assosiated with the elementInPlayfield.
   */
  public static ElementsInPlayfield.ElementInPlayfield getElementInPlayfield(String inputKey) {
    // return ElementInPlayfield.NO_ELEMENT;
    return (STRING_ELEMENT_IN_PLAYFIELD_MAP.get(inputKey));
  }

  /**
   * Returns an BackgroundFill for a given elementInPlayfield.
   *
   * @param elementInPlayfield this variable distinguishes between the different elements that are
   *     possible at one position of the playfield
   */
  public static BackgroundFill getBackgroundFill(
      ElementsInPlayfield.ElementInPlayfield elementInPlayfield) {
    return elementInPlayfieldBackgroundFillMap.get(elementInPlayfield);
  }

  /**
   * Returns an ImagePattern for a valid pathFilename of a png-file.
   *
   * @param pathFilename the pathFilename where the file is
   */
  private static ImagePattern getImagePatternFromFile(String pathFilename) {

    Image image = new Image(pathFilename);
    return new ImagePattern(image);
  }
}
