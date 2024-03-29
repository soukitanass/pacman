/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.collision;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.game.Game;
import ca.usherbrooke.pacman.model.GameModel;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.objects.PacMan;
import ca.usherbrooke.pacman.model.position.Position;

public class PacmanPacgumCollisionManagerTest {

  private static final String LEVEL_PATH = "Level.json";
  private static final int PACGUM_CODE = 39;
  private PacMan pacman;
  private Level level;
  private IGameModel model;
  private PacmanPacgumCollisionManager pacmanPacgumCollisionManager;

  @Before
  public void setUp() {
    this.pacman = new PacMan();
    this.level = new Level();
    this.model = new GameModel(Game.loadLevel(LEVEL_PATH));
  }

  @Test
  public void pacgumIsEatenOnlyWhenPacmanIsOnIt() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(PACGUM_CODE, PACGUM_CODE));
    initializePacmanPacgumCollisionManager(map);

    pacman.setPosition(new Position(0, 0));
    assertTrue(level.isPacgum(new Position(0, 0)));
    assertTrue(level.isPacgum(new Position(1, 0)));
    pacmanPacgumCollisionManager.update();
    assertEquals(Integer.valueOf(10), model.getScore());
    assertFalse(level.isPacgum(new Position(0, 0)));
    assertTrue(level.isPacgum(new Position(1, 0)));
  }

  private void initializePacmanPacgumCollisionManager(List<List<Integer>> map) {
    level.setMap(map);
    level.setPacMan(pacman);
    pacmanPacgumCollisionManager = new PacmanPacgumCollisionManager(level, model);
  }
}
