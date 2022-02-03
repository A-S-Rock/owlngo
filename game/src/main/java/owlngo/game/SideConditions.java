package owlngo.game;

/** Represents sideConditons for the game. */
public class SideConditions {

  private int endurance;
  private boolean inFlightMode;

  SideConditions(int endurance, boolean inFlightMode) {
    this.endurance = endurance;
    this.inFlightMode = inFlightMode;
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

  public boolean isInFlightMode() {
    return inFlightMode;
  }

  public void setInFlightMode() {
    this.inFlightMode = !inFlightMode;
  }
}
