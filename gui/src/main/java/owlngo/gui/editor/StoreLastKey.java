package owlngo.gui.editor;

import javafx.scene.input.KeyCode;

/** This class stores the last key pressed. */
public final class StoreLastKey {
  private static KeyCode keyCode;

  /**
   * Stets keyCode to space This is required to get no Null pointer exception when
   * getLastKeyPressed() is called without a key being pressed.
   */
  public static void setSpaceAsDefault() {
    keyCode = KeyCode.getKeyCode(" ");
  }

  /** Returns the last pressed key. */
  public static KeyCode getLastKeyPressed() {
    if (keyCode != null) {
      System.out.println(keyCode.getChar());
    }
    return keyCode;
  }

  /**
   * Sets keyCode to the given character.
   *
   * @param character key pressed
   */
  public static void setLastKeyPressed(KeyCode character) {
    keyCode = character;
  }

  /** Returns the last pressed key as a string. */
  public static String getLastKeyPressedAsString() {
    if (keyCode != null) {
      return keyCode.getChar();
    } else {
      return "";
    }
  }
}
