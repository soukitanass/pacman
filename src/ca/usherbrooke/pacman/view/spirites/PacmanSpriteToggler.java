/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.view.spirites;

import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.view.states.PacManState;

public class PacmanSpriteToggler {
  private int period;
  private int updatesCounter = 0;
  private PacManState currentPacmanState = PacManState.STATE1;
  private IGameModel model;

  public PacmanSpriteToggler(int spriteTogglePeriod, IGameModel model) {
    this.period = spriteTogglePeriod;
    this.model = model;
  }

  public void update() {
    ++updatesCounter;
    if (updatesCounter != period) {
      return;
    }
    updatesCounter = 0;

    if (model.isPacmanDead()) {
      toggleDeadPacManSprite();
    } else {
      togglePacManSprite();
    }
  }

  private void toggleDeadPacManSprite() {
    switch (currentPacmanState) {
      case STATE6:
        currentPacmanState = PacManState.STATE7;
        break;
      case STATE7:
        currentPacmanState = PacManState.STATE8;
        break;
      case STATE8:
        currentPacmanState = PacManState.STATE9;
        break;
      case STATE9:
        currentPacmanState = PacManState.STATE10;
        break;
      case STATE10:
        currentPacmanState = PacManState.STATE11;
        break;
      case STATE11:
        currentPacmanState = PacManState.STATE12;
        break;
      case STATE12:
        currentPacmanState = PacManState.STATE13;
        break;
      case STATE13:
        currentPacmanState = PacManState.STATE14;
        break;
      case STATE14:
        currentPacmanState = PacManState.STATE15;
        break;
      case STATE15:
        currentPacmanState = PacManState.STATE16;
        break;
      case STATE16:
        model.setIsPacmanDead(false);
        break;
      default:
        currentPacmanState = PacManState.STATE6;
        break;
    }
  }

  private void togglePacManSprite() {
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
        currentPacmanState = PacManState.STATE1;
        break;
    }
  }

  public PacManState getPacmanState() {
    return currentPacmanState;
  }

}
