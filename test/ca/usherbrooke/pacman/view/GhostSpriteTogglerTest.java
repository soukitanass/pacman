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
import ca.usherbrooke.pacman.view.spirite.GhostSpriteToggler;
import ca.usherbrooke.pacman.view.states.GhostState;

public class GhostSpriteTogglerTest {

  @Test
  public void toggles() {
    final int period = 2;
    GhostSpriteToggler toggler = new GhostSpriteToggler(period);

    assertEquals(GhostState.STATE1, toggler.getGhostState());
    toggler.update();
    assertEquals(GhostState.STATE1, toggler.getGhostState());
    toggler.update();
    assertEquals(GhostState.STATE2, toggler.getGhostState());
    toggler.update();
    assertEquals(GhostState.STATE2, toggler.getGhostState());
    toggler.update();
    assertEquals(GhostState.STATE1, toggler.getGhostState());
    toggler.update();
    assertEquals(GhostState.STATE1, toggler.getGhostState());
  }

}
