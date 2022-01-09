package owlngo.game;

/** This class is just for testing without the GUI. */
public class Main {

  // TODO: Check moves
  // TODO: Write JUnit tests

  /** Main method. */
  public static void main(String[] args) {

    System.out.println("Initialize game.");
    OwlnGo game = new OwlnGo();

    System.out.println("This text is for debugging purpose. Now some moves are tested.");
    game.moveRight();
    System.out.println("After a right move has been made.");
    game.moveLeft();
    System.out.println("After a left move has been made.");
    game.moveJump(false);
    System.out.println("After a jump without falling has been done.");
    game.moveFall();
    System.out.println("After making him fall manually.");
    game.moveJump(true);
    System.out.println("After a jump with falling has been done.");
  }
}
