package owlngo.gui.data;

import java.util.Map;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/** This class serves as an utility class for elements in the playfield. */
public class MethodsForElement {
  /*
   * This class static methods are included to hold constant relations. String entered ----
   * ElementInPlayfield e.g. S means Start
   *
   * <p>ElementInPlayfield ---- BackgroundFill e.g. ElementInPlayfield.OWL to The graphic that is
   * stored at resources/pictures/ingo40x40.png
   */

  // Constant values for window
  private static final String OWL_PATH = "/pictures/ingo40x40.png";
  private static final String GROUND_PATH = "/pictures/soil.png";
  public static final int size = 30;

  static final Map<String, ElementsInPlayfield.ElementInPlayfield> STRING_ELEMENT_IN_PLAYFIELD_MAP =
      Map.ofEntries(
          Map.entry("S", ElementsInPlayfield.ElementInPlayfield.START),
          Map.entry("E", ElementsInPlayfield.ElementInPlayfield.END),
          Map.entry("F", ElementsInPlayfield.ElementInPlayfield.GROUND_NO_LAWN),
          Map.entry("G", ElementsInPlayfield.ElementInPlayfield.GROUND_NO_LAWN),
          Map.entry("O", ElementsInPlayfield.ElementInPlayfield.OWL));

  static final Map<ElementsInPlayfield.ElementInPlayfield, BackgroundFill>
      ELEMENT_IN_PLAYFIELD_BACKGROUND_FILL_MAP =
          Map.ofEntries(
              Map.entry(
                  ElementsInPlayfield.ElementInPlayfield.NO_ELEMENT,
                  new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)),
              Map.entry(
                  ElementsInPlayfield.ElementInPlayfield.OWL,
                  new BackgroundFill(
                      getImagePatternFromFile(OWL_PATH), CornerRadii.EMPTY, Insets.EMPTY)),
              Map.entry(
                  ElementsInPlayfield.ElementInPlayfield.GROUND_NO_LAWN,
                  new BackgroundFill(
                      getImagePatternFromFile(GROUND_PATH), CornerRadii.EMPTY, Insets.EMPTY)),
              Map.entry(
                  ElementsInPlayfield.ElementInPlayfield.START,
                  new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)),
              Map.entry(
                  ElementsInPlayfield.ElementInPlayfield.END,
                  new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

  /**
   * Returns true if an element with this key is defined.
   *
   * @param inputKey key entered
   */
  public static boolean validKey(String inputKey) {
    return STRING_ELEMENT_IN_PLAYFIELD_MAP.containsKey(inputKey);
  }

  /**
   * Returns an elementInPlayfield (this variable distinguishes between the different elements that
   * are possible at one position of the playfield).
   *
   * @param inputKey key(letter) assosiated with the elementInPlayfield.
   */
  public static ElementsInPlayfield.ElementInPlayfield getElementInPlayfield(String inputKey) {
    return (STRING_ELEMENT_IN_PLAYFIELD_MAP.get(inputKey));
  }

  /**
   * Returns an BackgroundFill for a given elementInPlayfield.
   *
   * @param elementInPlayfield distinguishes between the different elements that are possible at one
   *     position of the playfield
   */
  public static BackgroundFill getBackgroundFill(
      ElementsInPlayfield.ElementInPlayfield elementInPlayfield) {
    return ELEMENT_IN_PLAYFIELD_BACKGROUND_FILL_MAP.get(elementInPlayfield);
  }

  /**
   * Returns an ImagePattern for a valid pathFilename of a png file.
   *
   * @param pathFilename the pathFilename where the file is
   */
  private static ImagePattern getImagePatternFromFile(String pathFilename) {
    Image image = new Image(pathFilename);
    return new ImagePattern(image);
  }
}