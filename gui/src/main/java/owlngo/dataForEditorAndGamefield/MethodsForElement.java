package owlngo.dataForEditorAndGamefield;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import owlngo.dataForEditorAndGamefield.ElementInPlayfield;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

// In this class static methods are included hold constant relations.
// String entered  ---- ElementInPlayfield
//   e.g.   S  means    Start
//
// ElementInPlayfield  ---- BackgroundFill
//   e.g.   ElementInPlayfield.OWL  to   The graphic that is stored at "C:\\ingo40x40.png"
//
public class MethodsForElement {

  // Constant values for window
  public final static int numberOfPanesInRowAnColumn = 30;

  static final Map<String, ElementInPlayfield> STRING_ELEMENT_IN_PLAYFIELD_MAP;

  static {
    STRING_ELEMENT_IN_PLAYFIELD_MAP = new HashMap<>();
    STRING_ELEMENT_IN_PLAYFIELD_MAP.put("S", ElementInPlayfield.START);
    STRING_ELEMENT_IN_PLAYFIELD_MAP.put("E", ElementInPlayfield.END);
    STRING_ELEMENT_IN_PLAYFIELD_MAP.put("F", ElementInPlayfield.GROUND_NO_LAWN);
    STRING_ELEMENT_IN_PLAYFIELD_MAP.put("G", ElementInPlayfield.GROUND_NO_LAWN);
    STRING_ELEMENT_IN_PLAYFIELD_MAP.put("O", ElementInPlayfield.OWL);
  }

  public static boolean validKey(String inputKey){
    return STRING_ELEMENT_IN_PLAYFIELD_MAP.containsKey(inputKey);
  }

  public static ElementInPlayfield getElementInPlayfield(String inputKey) {
    // return ElementInPlayfield.NO_ELEMENT;
    return (STRING_ELEMENT_IN_PLAYFIELD_MAP.get(inputKey));
  }

  static final Map<ElementInPlayfield, BackgroundFill> elementInPlayfieldBackgroundFillMap =
      new HashMap<>();

  static {
    elementInPlayfieldBackgroundFillMap.put(
        ElementInPlayfield.NO_ELEMENT,
        new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY));
    // background with owl--------------------------------------------------------------

    elementInPlayfieldBackgroundFillMap.put(
        ElementInPlayfield.OWL, new BackgroundFill(
            getImagePatternFromFile("C:\\ingo40x40.png"),CornerRadii.EMPTY,Insets.EMPTY));
    // ---------------------------------------------------------------------------------
    elementInPlayfieldBackgroundFillMap.put(
        ElementInPlayfield.GROUND_NO_LAWN,new BackgroundFill(
            getImagePatternFromFile("C:\\soil.png"), CornerRadii.EMPTY, Insets.EMPTY));
    // ----START-----------------------------------------------------------------------------
    elementInPlayfieldBackgroundFillMap.put(
        ElementInPlayfield.START, new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY));
    // ----END------------------------------------------------------------------------------
    elementInPlayfieldBackgroundFillMap.put(
        ElementInPlayfield.END, new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY));
  }

  public static BackgroundFill getBackgroundFill(ElementInPlayfield elementInPlayfield) {
    return elementInPlayfieldBackgroundFillMap.get(elementInPlayfield);
  }

  private static ImagePattern getImagePatternFromFile(String pathFilename){
    Image image= null;
    try {
      image = new Image(new FileInputStream(pathFilename));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    // ImagePattern imagePattern=new ImagePattern(image);
    return new ImagePattern(image);
  }

}
