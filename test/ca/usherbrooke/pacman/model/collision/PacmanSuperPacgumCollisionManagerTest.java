/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.collision;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.game.ITimeGetter;
import ca.usherbrooke.pacman.game.TimeGetter;
import ca.usherbrooke.pacman.model.GameModel;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.objects.PacMan;
import ca.usherbrooke.pacman.model.position.Position;

public class PacmanSuperPacgumCollisionManagerTest {

  private static final int SUPER_PACGUM_CODE = 40;
  private PacMan pacman;
  private Level level;
  private IGameModel model;
  private PacmanSuperPacgumCollisionManager pacmanSuperPacgumCollisionManager;

  @Before
  public void setUp() {
    this.pacman = new PacMan();
    this.level = new Level();
    this.model = mock(GameModel.class);
    when(model.getPacman()).thenReturn(pacman);
  }

  @Test
  public void superPacgumIsEatenOnlyWhenPacmanIsOnIt() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(SUPER_PACGUM_CODE, SUPER_PACGUM_CODE));
    initializeSuperPacgumCollisionManager(map);

    pacman.setPosition(new Position(0, 0));
    assertTrue(level.isSuperPacgum(new Position(0, 0)));
    assertTrue(level.isSuperPacgum(new Position(1, 0)));
    pacmanSuperPacgumCollisionManager.update();
    assertFalse(level.isSuperPacgum(new Position(0, 0)));
    assertTrue(level.isSuperPacgum(new Position(1, 0)));
  }

  @Test
  public void updateIsPacManInvincible() throws InterruptedException {
    ITimeGetter timeGetter = mock(TimeGetter.class);

    List<List<Integer>> map = Arrays.asList(Arrays.asList(SUPER_PACGUM_CODE, SUPER_PACGUM_CODE));
    initializeSuperPacgumCollisionManager(map);
    pacmanSuperPacgumCollisionManager.setTimeGetter(timeGetter);

    when(timeGetter.getMilliseconds()).thenReturn((long) 0);
    pacman.setPosition(new Position(0, 0));
    pacmanSuperPacgumCollisionManager.update();
    assertTrue(model.getPacman().isInvincible());

    when(timeGetter.getMilliseconds()).thenReturn((long) 1000);
    pacmanSuperPacgumCollisionManager.updateIsPacManInvincible();
    assertTrue(model.getPacman().isInvincible());

    when(timeGetter.getMilliseconds()).thenReturn((long) 8000);
    pacmanSuperPacgumCollisionManager.updateIsPacManInvincible();
    assertFalse(model.getPacman().isInvincible());
  }

  private void initializeSuperPacgumCollisionManager(List<List<Integer>> map) {
    level.setMap(map);
    level.setPacMan(pacman);
    pacmanSuperPacgumCollisionManager = new PacmanSuperPacgumCollisionManager(level, model);
  }
}
