package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class PacmanPacgumCollisionManagerTest {

  private static final int PACGUM_CODE = 42;
  private PacMan pacman;
  private Level level;
  private PacmanPacgumCollisionManager pacmanPacgumCollisionManager;

  @Before
  public void setUp() {
    this.pacman = new PacMan();
    this.level = new Level();
  }

  @Test
  public void pacgumIsEatenOnlyWhenPacmanIsOnIt() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(PACGUM_CODE, PACGUM_CODE));
    initializePacmanPacgumCollisionManager(map);

    pacman.setPosition(new Position(0, 0));
    assertTrue(level.isPacgum(new Position(0, 0)));
    assertTrue(level.isPacgum(new Position(1, 0)));
    pacmanPacgumCollisionManager.update();
    assertFalse(level.isPacgum(new Position(0, 0)));
    assertTrue(level.isPacgum(new Position(1, 0)));
  }

  private void initializePacmanPacgumCollisionManager(List<List<Integer>> map) {
    level.setMap(map);
    pacmanPacgumCollisionManager = new PacmanPacgumCollisionManager(pacman, level);
  }
}
