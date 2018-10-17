/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.view.spirites;

import ca.usherbrooke.pacman.view.states.GhostState;

public class GhostSpriteToggler {
  private int period;
  private int updatesCounter = 0;
  private GhostState currentGhostState = GhostState.STATE1;

  public GhostSpriteToggler(int spriteTogglePeriod) {
    this.period = spriteTogglePeriod;
  }

  public void update() {
    ++updatesCounter;
    if (updatesCounter != period) {
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
