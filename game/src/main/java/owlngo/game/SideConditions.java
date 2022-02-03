package owlngo.game;

/** Represents sideConditons for the game. */
public class SideConditions {

  private int endurance;

  SideConditions(int endurance) {
    this.endurance = endurance;
  }

  public int getEndurance() {
    return endurance;
  }

  public void resetEndurance(int originalEndurance) {
    endurance = originalEndurance;
  }

  public void decreaseEndurance() {
    endurance--;
  }
}
