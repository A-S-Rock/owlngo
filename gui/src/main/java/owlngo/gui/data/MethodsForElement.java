package owlngo.gui.data;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.util.Map;

/**
 * In this class static methods are included to hold constant relations.
 * String entered ---- ElementInPlayfield
 *         S                   Start
 *         E                   END
 *         ....
 *
 * ElementInPlayfield ------------------------ BackgroundFill
 * ElementInPlayfield.OWL -------------------- to the graphic that is stored
 *                                             at resources\C:\\ingo40x40.png"
 * ElementInPlayfield.END----------------------Color RED
 */
public class MethodsForElement {
  // Constant values for window
  public static final int numberOfPanesInRowAnColumn = 30;

  static final Map<String, ElementsInPlayfield.ElementInPlayfield> STRING_ELEMENT_IN_PLAYFIELD_MAP =
      Map.ofEntries(
          Map.entry("S", ElementsInPlayfield.ElementInPlayfield.START),
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
                  ElementsInPlayfield.ElementInPlayfield.OWL,
                  new BackgroundFill(
                      getImagePatternFromFile("/pictures/ingo40x40.png"),
                      CornerRadii.EMPTY,
                      Insets.EMPTY)),
              Map.entry(
                  ElementsInPlayfield.ElementInPlayfield.GROUND_NO_LAWN,
                  new BackgroundFill(
                      getImagePatternFromFile("/pictures/soil.png"), CornerRadii.EMPTY, Insets.EMPTY)),
              Map.entry(
                  ElementsInPlayfield.ElementInPlayfield.START,
                  new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)),
              Map.entry(
                  ElementsInPlayfield.ElementInPlayfield.END,
                  new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

  /**
   * Return true if the key @param inputKey is a key where an element is defined in
   * STRING_ELEMENT_IN_PLAYFIELD_MAP
   *
   * @param inputKey key entered
   */
  public static boolean validKey(String inputKey) {
    return STRING_ELEMENT_IN_PLAYFIELD_MAP.containsKey(inputKey);
  }
  /**
   * Returns an the elementInPlayfield (this variable distinguishes between the different
   * elements that are possible at one position of the playfield)
   * @param inputKey key(letter) assosiated with the elementInPlayfield.
   */
  public static ElementsInPlayfield.ElementInPlayfield getElementInPlayfield(String inputKey) {
    // return ElementInPlayfield.NO_ELEMENT;
    return (STRING_ELEMENT_IN_PLAYFIELD_MAP.get(inputKey));
  }

  /**
   * Returns an BackgroundFill for a given elementInPlayfield
   * @param elementInPlayfield this variable distinguishes between the
   *                           different elements that are possible at one
   *                           position of the playfield
   */

  public static BackgroundFill getBackgroundFill(
      ElementsInPlayfield.ElementInPlayfield elementInPlayfield) {
    return elementInPlayfieldBackgroundFillMap.get(elementInPlayfield);
  }

  /**
   * Returns an ImagePattern for a valid pathFilename of a of a png-file
   * @param pathFilename the pathFilename where the file is
   */
  private static ImagePattern getImagePatternFromFile(String pathFilename)  {


    Image image=new Image(pathFilename);
    return new ImagePattern(image);
  }
}
