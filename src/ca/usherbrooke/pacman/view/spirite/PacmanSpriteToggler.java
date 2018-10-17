/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.view.spirite;

import ca.usherbrooke.pacman.view.states.PacManState;

public class PacmanSpriteToggler {
  private int period;
  private int updatesCounter = 0;
  private PacManState currentPacmanState = PacManState.STATE1;

  public PacmanSpriteToggler(int spriteTogglePeriod) {
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
    switch (currentPacmanState) {
      case STATE1:
        currentPacmanState = PacManState.STATE2;
        break;
      case STATE2:
        currentPacmanState = PacManState.STATE3;
        break;
      case STATE3:
        currentPacmanState = PacManState.STATE4;
        break;
      case STATE4:
        currentPacmanState = PacManState.STATE5;
        break;
      case STATE5:
        currentPacmanState = PacManState.STATE1;
        break;
      default:
        break;
    }
  }

  public PacManState getPacmanState() {
    return currentPacmanState;
  }


}
