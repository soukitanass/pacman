/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.view;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PacmanSpriteTogglerTest {

  @Test
  public void toggles() {
    final int period = 2;
    PacmanSpriteToggler toggler = new PacmanSpriteToggler(period);

    assertEquals(PacManState.STATE1, toggler.getPacmanState());
    toggler.update();
    assertEquals(PacManState.STATE1, toggler.getPacmanState());
    toggler.update();
    assertEquals(PacManState.STATE2, toggler.getPacmanState());
    toggler.update();
    assertEquals(PacManState.STATE2, toggler.getPacmanState());
    toggler.update();
    assertEquals(PacManState.STATE3, toggler.getPacmanState());
    toggler.update();
    assertEquals(PacManState.STATE3, toggler.getPacmanState());
    toggler.update();
    assertEquals(PacManState.STATE4, toggler.getPacmanState());
    toggler.update();
    assertEquals(PacManState.STATE4, toggler.getPacmanState());
    toggler.update();
    assertEquals(PacManState.STATE5, toggler.getPacmanState());
    toggler.update();
    assertEquals(PacManState.STATE5, toggler.getPacmanState());
    toggler.update();
    assertEquals(PacManState.STATE1, toggler.getPacmanState());
    toggler.update();
    assertEquals(PacManState.STATE1, toggler.getPacmanState());
  }

}
