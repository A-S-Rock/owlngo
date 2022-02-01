package owlngo.game;

import owlngo.game.level.Level;

/** This class is just for testing without the GUI. */
public class Main {

  // TODO: Write JUnit tests

  /** Main method. */
  public static void main(String[] args) {

    System.out.println("Initialize game.");
    OwlnGo game = new OwlnGo(Level.createDemoLevel(10, 10));

    System.out.println("This text is for debugging purpose. Now some moves are tested.");
    game.moveBasicLeft();
    System.out.println("After a left move has been made.");
    game.moveBasicRight();
    System.out.println("After a right move has been made.");
    game.moveBasicUp();
    System.out.println("After a jump without falling has been done.");
    game.moveBasicDown();
    System.out.println("After making him fall manually.");
    game.moveBasicUp();
    System.out.println("After a jump with falling has been done.");
  }
}
