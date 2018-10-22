/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.view.spirites;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.view.states.PacManState;

public class PacmanSpriteTogglerTest {

  private IGameModel mockModel = mock(IGameModel.class);;

  @Test
  public void togglePacManSprite() {
    final int period = 2;
    when(mockModel.isPacmanDead()).thenReturn(false);
    PacmanSpriteToggler toggler = new PacmanSpriteToggler(period, mockModel);

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

  @Test
  public void toggleDeadPacManSprite() {
    final int period = 1;
    when(mockModel.isPacmanDead()).thenReturn(true);
    PacmanSpriteToggler toggler = new PacmanSpriteToggler(period, mockModel);

    toggler.update();
    assertEquals(PacManState.STATE6, toggler.getPacmanState());
    toggler.update();
    assertEquals(PacManState.STATE7, toggler.getPacmanState());
    toggler.update();
    assertEquals(PacManState.STATE8, toggler.getPacmanState());
    toggler.update();
    assertEquals(PacManState.STATE9, toggler.getPacmanState());
    toggler.update();
    assertEquals(PacManState.STATE10, toggler.getPacmanState());
    toggler.update();
    assertEquals(PacManState.STATE11, toggler.getPacmanState());
    toggler.update();
    assertEquals(PacManState.STATE12, toggler.getPacmanState());
    toggler.update();
    assertEquals(PacManState.STATE13, toggler.getPacmanState());
    toggler.update();
    assertEquals(PacManState.STATE14, toggler.getPacmanState());
    toggler.update();
    assertEquals(PacManState.STATE15, toggler.getPacmanState());
    toggler.update();
    assertEquals(PacManState.STATE16, toggler.getPacmanState());
  }

}
