package owlngo.editor;

import javafx.scene.input.KeyCode;

/** This class stores the last key pressed. */
public final class StoreLastKey {
  private static KeyCode keyCode;

  public static void setSpaceAsDefault() {
    keyCode = KeyCode.getKeyCode(" ");
  }

  public static KeyCode getLastKeyPressed() {
    if (keyCode != null) {
      System.out.println(keyCode.getChar());
    }
    return keyCode;
  }

  public static void setLastKeyPressed(KeyCode character) {
    keyCode = character;
  }

  public static String getLastKeyPressedAsString() {
    if (keyCode != null) {
      return keyCode.getChar();
    } else {
      return "";
    }
  }
}
