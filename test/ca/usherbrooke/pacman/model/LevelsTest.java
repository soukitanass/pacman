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
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class LevelsTest {

  private Levels levels = new Levels();

  @Before
  public void setup() {
    ArrayList<Level> levelsList = new ArrayList<>();
    List<List<Integer>> map =
        Arrays.asList(Arrays.asList(0, 1), Arrays.asList(2, 0), Arrays.asList(4, 5));
    Level level1 = new Level();
    Level level2 = new Level();
    Level level3 = new Level();

    level1.setMap(map);
    level2.setMap(map);
    level3.setMap(map);

    levelsList.add(level1);
    levelsList.add(level2);
    levelsList.add(level3);

    levels.setLevels(levelsList);
  }

  @Test
  public void incrementLevel() {
    // Assign
    levels.setCurrentLevel(0);

    // Act
    levels.incrementCurrentLevel();
    int currentLevel = levels.getCurrentLevel();

    // Assert
    assertEquals(1, currentLevel);
  }

  @Test
  public void incrementLevelDoesNotIncrementIfGameIsCompleted() {
    // Assign
    levels.setCurrentLevel(2);

    // Act
    levels.incrementCurrentLevel();
    int currentLevel = levels.getCurrentLevel();
    boolean isGameCompleted = levels.isGameCompleted();

    // Assert
    assertEquals(2, currentLevel);
    assertTrue(isGameCompleted);
  }

}
