/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.game.Game;
import ca.usherbrooke.pacman.model.position.Position;

public class LevelTest {
  private static final String LEVEL_PATH = "ThreeByOneLevelWithPacmanAndTwoGhosts.json";
  private static final int EMPTY_CODE = 0;
  private static final int PACGUM_CODE = 39;
  private static final int SUPER_PACGUM_CODE = 40;
  private Level level;

  @Before
  public void setUp() {
    level = new Level();
  }

  @Test
  public void setMapWithCorrectSize() {
    List<List<Integer>> map =
        Arrays.asList(Arrays.asList(0, 1), Arrays.asList(2, 0), Arrays.asList(4, 5));
    level.setMap(map);
    assertEquals(Integer.valueOf(2), level.getWidth());
    assertEquals(Integer.valueOf(3), level.getHeight());
  }

  @Test
  public void isWall() {
    List<List<Integer>> map =
        Arrays.asList(Arrays.asList(0, 1), Arrays.asList(2, 0), Arrays.asList(4, 5));
    level.setMap(map);
    assertFalse(level.isWall(new Position(0, 0)));
    assertTrue(level.isWall(new Position(1, 0)));
    assertTrue(level.isWall(new Position(0, 1)));
    assertFalse(level.isWall(new Position(1, 1)));
    assertTrue(level.isWall(new Position(0, 2)));
    assertTrue(level.isWall(new Position(1, 2)));
  }

  @Test
  public void ghostGatesArentWalls() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(37));
    level.setMap(map);
    assertFalse(level.isWall(new Position(0, 0)));
  }

  @Test
  public void isWallFromCode1To36() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(-1, 0, 1, 2, 34, 35, 36, 37, 38, 39));
    level.setMap(map);

    assertFalse(level.isWall(new Position(0, 0)));
    assertFalse(level.isWall(new Position(1, 0)));
    assertTrue(level.isWall(new Position(2, 0)));
    assertTrue(level.isWall(new Position(3, 0)));
    assertTrue(level.isWall(new Position(4, 0)));
    assertTrue(level.isWall(new Position(5, 0)));
    assertTrue(level.isWall(new Position(6, 0)));
    assertFalse(level.isWall(new Position(7, 0)));
    assertFalse(level.isWall(new Position(8, 0)));
    assertFalse(level.isWall(new Position(9, 0)));
  }

  @Test
  public void isGhostGateOnlyForCode37() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(-1, 0, 1, 2, 34, 35, 36, 37, 38, 39));
    level.setMap(map);

    assertFalse(level.isGhostGate(new Position(0, 0)));
    assertFalse(level.isGhostGate(new Position(1, 0)));
    assertFalse(level.isGhostGate(new Position(2, 0)));
    assertFalse(level.isGhostGate(new Position(3, 0)));
    assertFalse(level.isGhostGate(new Position(4, 0)));
    assertFalse(level.isGhostGate(new Position(5, 0)));
    assertFalse(level.isGhostGate(new Position(6, 0)));
    assertTrue(level.isGhostGate(new Position(7, 0)));
    assertFalse(level.isGhostGate(new Position(8, 0)));
    assertFalse(level.isGhostGate(new Position(9, 0)));
  }

  @Test
  public void isGhostRoomOnlyForCode38() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(-1, 0, 1, 2, 35, 36, 37, 38, 39, 40));
    level.setMap(map);

    assertFalse(level.isGhostRoom(new Position(0, 0)));
    assertFalse(level.isGhostRoom(new Position(1, 0)));
    assertFalse(level.isGhostRoom(new Position(2, 0)));
    assertFalse(level.isGhostRoom(new Position(3, 0)));
    assertFalse(level.isGhostRoom(new Position(4, 0)));
    assertFalse(level.isGhostRoom(new Position(5, 0)));
    assertFalse(level.isGhostRoom(new Position(6, 0)));
    assertTrue(level.isGhostRoom(new Position(7, 0)));
    assertFalse(level.isGhostRoom(new Position(8, 0)));
    assertFalse(level.isGhostRoom(new Position(9, 0)));
  }

  @Test
  public void isCompleted() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(EMPTY_CODE, EMPTY_CODE),
        Arrays.asList(EMPTY_CODE, EMPTY_CODE), Arrays.asList(EMPTY_CODE, EMPTY_CODE));
    level.setMap(map);

    assertTrue(level.isCompleted());
  }

  @Test
  public void isIncompletedIfPacgumsAreRemaining() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(PACGUM_CODE, EMPTY_CODE),
        Arrays.asList(EMPTY_CODE, PACGUM_CODE), Arrays.asList(EMPTY_CODE, EMPTY_CODE));
    level.setMap(map);

    assertFalse(level.isCompleted());
  }

  @Test
  public void isIncompletedIfSuperPacgumsAreRemaining() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(SUPER_PACGUM_CODE, EMPTY_CODE),
        Arrays.asList(EMPTY_CODE, SUPER_PACGUM_CODE), Arrays.asList(EMPTY_CODE, EMPTY_CODE));
    level.setMap(map);

    assertFalse(level.isCompleted());
  }

  @Test
  public void isTunnel() {
    // Assign
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0, 325));
    level.setMap(map);

    // Act
    boolean isTunnel = level.isTunnel(new Position(1, 0));

    // Assert
    assertTrue(isTunnel);
  }

  @Test
  public void isNotTunnel() {
    // Assign
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0, 325));
    level.setMap(map);

    // Act
    boolean isTunnel = level.isTunnel(new Position(0, 0));

    // Assert
    assertFalse(isTunnel);
  }

  @Test
  public void copyConstructorCopiesGhosts() {
    Level level = Game.loadLevel(LEVEL_PATH);
    Level levelCopy = new Level(level);
    assertEquals(2, level.getGhosts().size());
    assertEquals(2, level.getGhosts().size());
    levelCopy.getGhosts().remove(0);
    assertEquals(2, level.getGhosts().size());
    assertEquals(1, levelCopy.getGhosts().size());
  }

  @Test
  public void equivalentLevelsAreEqual() {
    Level levelA = Game.loadLevel(LEVEL_PATH);
    Level levelB = Game.loadLevel(LEVEL_PATH);
    assertEquals(levelA, levelB);
  }
}
