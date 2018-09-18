package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class PacmanSuperPacgumCollisionManagerTest {

  private static final int SUPER_PACGUM_CODE = 40;
  private PacMan pacman;
  private Level level;
  private PacmanSuperPacgumCollisionManager pacmanSuperPacgumCollisionManager;

  @Before
  public void setUp() {
    this.pacman = new PacMan();
    this.level = new Level();
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
    pacmanSuperPacgumCollisionManager = new PacmanSuperPacgumCollisionManager(pacman, level);
  }
}
