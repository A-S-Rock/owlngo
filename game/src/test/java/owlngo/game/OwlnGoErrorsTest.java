package owlngo.game;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import owlngo.game.level.Level;

/** Tests for error calls from {@link OwlnGo}. Adjusted from Task 2 "Beesweeper". */
public class OwlnGoErrorsTest {

  // first game constructor with numRows and numColumns

  @Test
  public void testNegativeRows() {
    try {
      new OwlnGo(-1, 10);
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Expected: test successful
    }
  }

  @Test
  public void testNoRows() {
    try {
      new OwlnGo(0, 10);
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Expected: test successful
    }
  }

  @Test
  public void testIfRowsLowerThanOrEqualThree() {
    try {
      new OwlnGo(3, 10);
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Expected: test successful
    }
  }

  @Test
  public void testNegativeColumns() {
    try {
      new OwlnGo(10, -1);
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Expected: test successful
    }
  }

  @Test
  public void testNoColumns() {
    try {
      new OwlnGo(10, 0);
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Expected: test successful
    }
  }

  @Test
  public void testIfColumnsLowerThanOrEqualThree() {
    try {
      new OwlnGo(10, 3);
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Expected: test successful
    }
  }

  // second game constructor with level as parameter

  @Test
  public void testLevelConstructorNegativeRows() {
    try {
      new OwlnGo(new Level(-1, 10));
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Expected: test successful
    }
  }

  @Test
  public void testLevelConstructorNoRows() {
    try {
      new OwlnGo(new Level(0, 10));
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Expected: test successful
    }
  }

  @Test
  public void testLevelConstructorIfRowsLowerThanOrEqualThree() {
    try {
      new OwlnGo(new Level(3, 10));
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Expected: test successful
    }
  }

  @Test
  public void testLevelConstructorNegativeColumns() {
    try {
      new OwlnGo(new Level(10, -1));
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Expected: test successful
    }
  }

  @Test
  public void testLevelContstructorColumns() {
    try {
      new OwlnGo(new Level(10, 0));
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Expected: test successful
    }
  }

  @Test
  public void testLevelContstructorIfColumnsLowerThanOrEqualThree() {
    try {
      new OwlnGo(new Level(10, 3));
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // Expected: test successful
    }
  }
}
