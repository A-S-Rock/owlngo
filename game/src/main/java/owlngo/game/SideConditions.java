package owlngo.game;

/** Represents sideConditons for the game. */
public class SideConditions {

  private int endurance;
  private boolean inFlightMode;
  private boolean activeMovement;

  SideConditions(int endurance, boolean inFlightMode, boolean activeMovement) {
    this.endurance = endurance;
    this.inFlightMode = inFlightMode;
    this.activeMovement = activeMovement;
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

  public boolean isInFlightMode() {
    return inFlightMode;
  }

  public void setInFlightMode() {
    this.inFlightMode = !inFlightMode;
  }

  public boolean isActiveMovement() {
    return activeMovement;
  }

  public void setActiveMovement() {
    this.activeMovement = !activeMovement;
  }
}
