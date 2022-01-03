package owlngo.gui.editor;

import javafx.scene.input.KeyCode;

/** This class stores the last key pressed. */
public final class StoreLastKey {
  private static KeyCode keyCode;

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
