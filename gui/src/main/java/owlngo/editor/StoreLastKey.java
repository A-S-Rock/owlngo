package owlngo.editor;

import javafx.scene.input.KeyCode;

// This class stores the last key pressed
final public class StoreLastKey {
  private static KeyCode keyCode;

  static public void setSpaceAsDefault(){
    keyCode = KeyCode.getKeyCode(" ");
  }

  static public KeyCode getLastKeyPressed(){
    if (keyCode!=null){
       System.out.println(keyCode.getChar());
    }
  return keyCode;
  }

  static public String getLastKeyPressedAsString(){
    if (keyCode!=null){
      // return (keyCode.getChar().toString());
      return (keyCode.getChar());
    }else {
      return "";
    }

  }


  static public void setLastKeyPressed(KeyCode character){
    keyCode =character;
  }


}
