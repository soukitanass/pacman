/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.model.collision.PacmanSuperPacgumCollisionManager;
import ca.usherbrooke.pacman.model.direction.Position;

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
    this.model = new GameModel();
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

  private void initializeSuperPacgumCollisionManager(List<List<Integer>> map) {
    level.setMap(map);
    level.setPacMan(pacman);
    pacmanSuperPacgumCollisionManager = new PacmanSuperPacgumCollisionManager(level, model);
  }
}
