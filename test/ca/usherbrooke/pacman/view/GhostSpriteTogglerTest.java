package ca.usherbrooke.pacman.view;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class GhostSpriteTogglerTest {

  @Test
  public void toggles() {
    final int period = 2;
    GhostSpriteToggler toggler = new GhostSpriteToggler(period);

    assertEquals(GhostState.STATE1, toggler.getGhostState());
    toggler.update();
    assertEquals(GhostState.STATE2, toggler.getGhostState());
    toggler.update();
    assertEquals(GhostState.STATE1, toggler.getGhostState());
    toggler.update();
    assertEquals(GhostState.STATE2, toggler.getGhostState());
    toggler.update();
    assertEquals(GhostState.STATE1, toggler.getGhostState());
  }

}
