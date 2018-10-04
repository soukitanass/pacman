/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class PacmanGhostsCollisionManagerTest {
  private Level level;
  private Ghost ghost;
  private IGameModel model;
  private PacmanGhostCollisionManager pacmanGhostCollisionManager;


  @Before
  public void setUp() {
    this.level = new Level();
    this.ghost = new Ghost();
    this.model = new GameModel();
  }

  @Test
  public void pacmanGhostCollisionManagerLivesTest() {
    initializeGhostsList();
    int actualValue;
    ghost.setPosition(new Position(0, 0));
    level.getGhosts().add(ghost);
    level.getPacMan().setPosition(new Position(0, 0));
    pacmanGhostCollisionManager.update();
    actualValue = 2;
    assertEquals(actualValue, model.getLives());

    ghost.setPosition(new Position(0, 1));
    level.getGhosts().add(ghost);
    level.getPacMan().setPosition(new Position(0, 1));
    pacmanGhostCollisionManager.update();
    actualValue = 1;
    assertEquals(actualValue, model.getLives());

    ghost.setPosition(new Position(1, 0));
    level.getGhosts().add(ghost);
    level.getPacMan().setPosition(new Position(1, 0));
    pacmanGhostCollisionManager.update();
    actualValue = 0;
    assertEquals(actualValue, model.getLives());

  }


  private void initializeGhostsList() {
    List<Ghost> ghosts = new ArrayList<Ghost>();
    IGameModel model = new GameModel();
    String levelsPath = "Levels.json";
    model.loadLevels(levelsPath);
    level.setGhost(ghosts);
    level.setPacMan(new PacMan(new Position(0, 0)));
    ghost.setPosition(new Position(0, 0));
    level.getGhosts().add(ghost);
    pacmanGhostCollisionManager =
        new PacmanGhostCollisionManager(level, model.getCurrentLevel(), model);
  }
}
