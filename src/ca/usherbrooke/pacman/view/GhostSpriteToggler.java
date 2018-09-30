package ca.usherbrooke.pacman.view;

public class GhostSpriteToggler {
  private int period;
  private int updatesCounter = 0;
  private GhostState currentGhostState = GhostState.STATE1;

  public GhostSpriteToggler(int spriteTogglePeriod) {
    this.period = spriteTogglePeriod;
  }

  public void update() {
    ++updatesCounter;
    if (period != updatesCounter + 1) {
      return;
    }
    updatesCounter = 0;
    toggleSprite();
  }

  private void toggleSprite() {
    switch (currentGhostState) {
      case STATE1:
        currentGhostState = GhostState.STATE2;
        break;
      case STATE2:
        currentGhostState = GhostState.STATE1;
        break;
      default:
        break;
    }
  }

  public GhostState getGhostState() {
    return currentGhostState;
  }


}
