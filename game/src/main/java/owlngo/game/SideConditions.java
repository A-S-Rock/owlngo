package owlngo.game;

public class SideConditions {

  private int endurance;

  SideConditions(int endurance) {
    this.endurance = endurance;
  }

  public int getEndurance() {
    return endurance;
  }

  public void increaseEndurance() {
    endurance++;
  }

  public void decreaseEndurance() {
    endurance--;
  }

}
