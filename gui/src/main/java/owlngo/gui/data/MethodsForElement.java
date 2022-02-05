package owlngo.gui.data;

import static owlngo.gui.data.ElementsInPlayfield.ElementInPlayfield.START;

import java.util.Map;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import owlngo.game.level.objects.ObjectInGame;
import owlngo.game.level.objects.ObjectInGame.ObjectType;
import owlngo.gui.data.ElementsInPlayfield.ElementInPlayfield;

/** In this class static methods are included to hold constant relations. */
public class MethodsForElement {

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
              Map.entry(ObjectType.NONE, ElementInPlayfield.NO_ELEMENT),
              Map.entry(ObjectType.PLAYER, ElementInPlayfield.OWL),
              Map.entry(ObjectType.START, ElementInPlayfield.START),
              Map.entry(ObjectType.FINISH, ElementInPlayfield.END),
              Map.entry(ObjectType.GROUND, ElementInPlayfield.GROUND_NO_LAWN),
              Map.entry(ObjectType.FOOD, ElementInPlayfield.FOOD),
              Map.entry(ObjectType.AIR, ElementInPlayfield.NO_ELEMENT),
              Map.entry(ObjectInGame.ObjectType.FIRE, ElementInPlayfield.DANGER));

  static final Map<String, ElementInPlayfield> STRING_ELEMENT_IN_PLAYFIELD_MAP =
      Map.ofEntries(
          Map.entry("S", START),
          Map.entry("E", ElementInPlayfield.END),
          Map.entry("F", ElementInPlayfield.FOOD),
          Map.entry("G", ElementInPlayfield.GROUND_NO_LAWN),
          Map.entry("D", ElementInPlayfield.DANGER),
          Map.entry("O", ElementInPlayfield.OWL));

  static final Map<ElementInPlayfield, BackgroundFill> elementInPlayfieldBackgroundFillMap =
      Map.ofEntries(
          Map.entry(
              ElementInPlayfield.NO_ELEMENT,
              new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)),
          Map.entry(
              ElementInPlayfield.OWL,
              new BackgroundFill(
                  getImagePatternFromFile("/images/ingo40x40.png"),
                  CornerRadii.EMPTY,
                  Insets.EMPTY)),
          Map.entry(
              ElementInPlayfield.GROUND_NO_LAWN,
              new BackgroundFill(
                  getImagePatternFromFile("/images/soil.png"), CornerRadii.EMPTY, Insets.EMPTY)),
          Map.entry(
              ElementInPlayfield.START,
              new BackgroundFill(
                  getImagePatternFromFile("/images/start.png"), CornerRadii.EMPTY, Insets.EMPTY)),
          Map.entry(
              ElementInPlayfield.DANGER,
              new BackgroundFill(
                  getImagePatternFromFile("/images/fire_animated.gif"),
                  CornerRadii.EMPTY,
                  Insets.EMPTY)),
          Map.entry(
              ElementInPlayfield.FOOD,
              new BackgroundFill(
                  getImagePatternFromFile("/images/food.png"), CornerRadii.EMPTY, Insets.EMPTY)),
          Map.entry(
              ElementInPlayfield.END,
              new BackgroundFill(
                  getImagePatternFromFile("/images/finish.png"), CornerRadii.EMPTY, Insets.EMPTY)));

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
