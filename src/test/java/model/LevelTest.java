package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class LevelTest {

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
  public void isWallFromCode1To41() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(-1, 0, 1, 2, 39, 40, 41, 42, 43, 44));
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
}
