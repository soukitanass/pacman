package ca.usherbrooke.pacman.model.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.position.Position;

public class LevelGetDirectionIfInLineOfSightTest {

  private Level emptyThreeByThreeLevel = MockLevelFactory.getMockLevelThreeByThreeEmpty();
  private Level singleWallSurroundedByEmptiness =
      MockLevelFactory.getMockLevelSingleWallSurroundedByEmptiness();

  @Test
  public void whenPositionsAreIdenticalThenReturnNull() {
    assertNull(
        emptyThreeByThreeLevel.getDirectionIfInLineOfSight(new Position(0, 0), new Position(0, 0)));
    assertNull(
        emptyThreeByThreeLevel.getDirectionIfInLineOfSight(new Position(1, 0), new Position(1, 0)));
    assertNull(
        emptyThreeByThreeLevel.getDirectionIfInLineOfSight(new Position(2, 0), new Position(2, 0)));
    assertNull(
        emptyThreeByThreeLevel.getDirectionIfInLineOfSight(new Position(0, 1), new Position(0, 1)));
    assertNull(
        emptyThreeByThreeLevel.getDirectionIfInLineOfSight(new Position(1, 1), new Position(1, 1)));
    assertNull(
        emptyThreeByThreeLevel.getDirectionIfInLineOfSight(new Position(2, 1), new Position(2, 1)));
    assertNull(
        emptyThreeByThreeLevel.getDirectionIfInLineOfSight(new Position(0, 2), new Position(0, 2)));
    assertNull(
        emptyThreeByThreeLevel.getDirectionIfInLineOfSight(new Position(1, 2), new Position(1, 2)));
    assertNull(
        emptyThreeByThreeLevel.getDirectionIfInLineOfSight(new Position(2, 2), new Position(2, 2)));
  }

  @Test
  public void whenPositionsOfDifferentBothXAndYReturnNull() {
    assertNull(
        emptyThreeByThreeLevel.getDirectionIfInLineOfSight(new Position(1, 1), new Position(0, 0)));
    assertNull(
        emptyThreeByThreeLevel.getDirectionIfInLineOfSight(new Position(1, 1), new Position(2, 0)));
    assertNull(
        emptyThreeByThreeLevel.getDirectionIfInLineOfSight(new Position(1, 1), new Position(0, 2)));
    assertNull(
        emptyThreeByThreeLevel.getDirectionIfInLineOfSight(new Position(1, 1), new Position(2, 2)));
  }

  @Test
  public void whenNoWallsInLineOfSightThenReturnDirection() {
    assertEquals(Direction.UP,
        emptyThreeByThreeLevel.getDirectionIfInLineOfSight(new Position(1, 2), new Position(1, 0)));
    assertEquals(Direction.DOWN,
        emptyThreeByThreeLevel.getDirectionIfInLineOfSight(new Position(1, 0), new Position(1, 2)));
    assertEquals(Direction.LEFT,
        emptyThreeByThreeLevel.getDirectionIfInLineOfSight(new Position(2, 1), new Position(0, 1)));
    assertEquals(Direction.RIGHT,
        emptyThreeByThreeLevel.getDirectionIfInLineOfSight(new Position(0, 1), new Position(2, 1)));
  }

  @Test
  public void whenWallsInLineOfSightThenReturnNull() {
    assertNull(singleWallSurroundedByEmptiness.getDirectionIfInLineOfSight(new Position(1, 2),
        new Position(1, 0)));
    assertNull(singleWallSurroundedByEmptiness.getDirectionIfInLineOfSight(new Position(1, 0),
        new Position(1, 2)));
    assertNull(singleWallSurroundedByEmptiness.getDirectionIfInLineOfSight(new Position(2, 1),
        new Position(0, 1)));
    assertNull(singleWallSurroundedByEmptiness.getDirectionIfInLineOfSight(new Position(0, 1),
        new Position(2, 1)));
  }

  @Test
  public void whenPositionsOfDifferentBothXAndYThenGetPositionsInbetweenReturnReturnNull() {
    Set<Position> positions = Level.getPositionsInbetween(new Position(0, 0), new Position(4, 5));
    assertNull(positions);
  }

  @Test
  public void whenPositionsAreIdenticalThenGetPositionsInbetweenReturnEmpty() {
    assertEquals(0, Level.getPositionsInbetween(new Position(42, 42), new Position(42, 42)).size());
  }

  @Test
  public void whenPositionsAreOfDistanceOfOneThenGetPositionsInbetweenReturnEmpty() {
    assertEquals(0, Level.getPositionsInbetween(new Position(1, 1), new Position(0, 1)).size());
    assertEquals(0, Level.getPositionsInbetween(new Position(1, 1), new Position(1, 0)).size());
    assertEquals(0, Level.getPositionsInbetween(new Position(1, 1), new Position(2, 1)).size());
    assertEquals(0, Level.getPositionsInbetween(new Position(1, 1), new Position(1, 2)).size());
  }

  @Test
  public void whenPositionsOfSameYThenGetPositionsInbetweenReturnInbetweenHorizontalPositions() {
    Set<Position> expectedPositions = new HashSet<>();
    expectedPositions.add(new Position(1, 0));
    expectedPositions.add(new Position(2, 0));
    expectedPositions.add(new Position(3, 0));
    expectedPositions.add(new Position(4, 0));
    Set<Position> positions = Level.getPositionsInbetween(new Position(0, 0), new Position(5, 0));
    assertEquals(expectedPositions, positions);
  }

  @Test
  public void whenPositionsOfSameXThenGetPositionsInbetweenReturnInbetweenVerticalPositions() {
    Set<Position> expectedPositions = new HashSet<>();
    expectedPositions.add(new Position(0, 1));
    expectedPositions.add(new Position(0, 2));
    expectedPositions.add(new Position(0, 3));
    expectedPositions.add(new Position(0, 4));
    Set<Position> positions = Level.getPositionsInbetween(new Position(0, 0), new Position(0, 5));
    assertEquals(expectedPositions, positions);
  }

}
