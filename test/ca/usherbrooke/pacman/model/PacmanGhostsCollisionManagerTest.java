package ca.usherbrooke.pacman.model;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class PacmanGhostsCollisionManagerTest {
  private Level level;
  private Ghost ghost;
  private PacmanGhostCollisionManager pacmanGhostCollisionManager;


  @Before
  public void setUp() {
    this.level = new Level();
    this.ghost = new Ghost();
  }

  @Test
  public void pacmanGhostCollisionManagerLivesTest() {
    initializeGhostsList();

    ghost.setPosition(new Position(0, 0));
    level.getGhosts().add(ghost);
    level.getPacMan().setPosition(new Position(0, 0));
    pacmanGhostCollisionManager.update();
    assertEquals(Integer.valueOf(2), level.getLives());
    
    ghost.setPosition(new Position(0, 1));
    level.getGhosts().add(ghost);
    level.getPacMan().setPosition(new Position(0, 1));
    pacmanGhostCollisionManager.update();
    assertEquals(Integer.valueOf(1), level.getLives());

    ghost.setPosition(new Position(1, 0));
    level.getGhosts().add(ghost);
    level.getPacMan().setPosition(new Position(1, 0));
    pacmanGhostCollisionManager.update();
    assertEquals(Integer.valueOf(0), level.getLives());

  }


  private void initializeGhostsList() {
    List<Ghost> ghosts = new ArrayList<Ghost>();
    level.setGhost(ghosts);
    level.setPacMan(new PacMan(new Position(0, 0)));
    ghost.setPosition(new Position(0, 0));
    level.getGhosts().add(ghost);
    pacmanGhostCollisionManager = new PacmanGhostCollisionManager(level);
  }
}