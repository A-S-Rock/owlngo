package owlngo.editor;

import javafx.scene.input.KeyCode;

public class StoreLastKey {
  private static KeyCode keyCode;

  static public void setSpaceAsDefault(){
    keyCode = KeyCode.getKeyCode(" ");
  }

  static public KeyCode getLastKeyPressed(){
    return keyCode;
  }
  static public void setLastKeyPressed(KeyCode character){
    keyCode =character;
  }
}
