package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MoveValidatorTest {

  @Before
  public void setUp() {}

  @Test
  public void movesToEmptySpacesAreValid() {
    Level mockLevel = getMockLevelTwoByTwoEmpty();
    IMoveValidator moveValidator = new MoveValidator(mockLevel);
    IMoveRequest moveLeft = new MoveRequest(new Position(1, 0), Direction.LEFT);
    IMoveRequest moveRight = new MoveRequest(new Position(0, 0), Direction.RIGHT);
    IMoveRequest moveUp = new MoveRequest(new Position(0, 1), Direction.UP);
    IMoveRequest moveDown = new MoveRequest(new Position(0, 1), Direction.DOWN);
    assertTrue(moveValidator.isValid(moveLeft));
    assertTrue(moveValidator.isValid(moveRight));
    assertTrue(moveValidator.isValid(moveUp));
    assertTrue(moveValidator.isValid(moveDown));
  }

  @Test
  public void movesIntoAWallAreInvalid() {
    Level mockLevel = getMockLevelTwoByTwoWalls();
    IMoveValidator moveValidator = new MoveValidator(mockLevel);
    IMoveRequest moveLeft = new MoveRequest(new Position(1, 0), Direction.LEFT);
    IMoveRequest moveRight = new MoveRequest(new Position(0, 0), Direction.RIGHT);
    IMoveRequest moveUp = new MoveRequest(new Position(0, 1), Direction.UP);
    IMoveRequest moveDown = new MoveRequest(new Position(0, 1), Direction.DOWN);
    assertFalse(moveValidator.isValid(moveLeft));
    assertFalse(moveValidator.isValid(moveRight));
    assertFalse(moveValidator.isValid(moveUp));
    assertFalse(moveValidator.isValid(moveDown));
  }

  @Test
  public void movesIntoLevelEdgesAreValid() {
    Level mockLevel = getMockLevelSingleWall();
    IMoveValidator moveValidator = new MoveValidator(mockLevel);
    IMoveRequest moveLeft = new MoveRequest(new Position(0, 0), Direction.LEFT);
    IMoveRequest moveRight = new MoveRequest(new Position(0, 0), Direction.RIGHT);
    IMoveRequest moveUp = new MoveRequest(new Position(0, 0), Direction.UP);
    IMoveRequest moveDown = new MoveRequest(new Position(0, 0), Direction.DOWN);
    assertTrue(moveValidator.isValid(moveLeft));
    assertTrue(moveValidator.isValid(moveRight));
    assertTrue(moveValidator.isValid(moveUp));
    assertTrue(moveValidator.isValid(moveDown));
  }

  // E = Empty
  // W = Wall
  //
  // -----
  // | W |
  // -----
  private Level getMockLevelSingleWall() {
    Level mockLevel = mock(Level.class);
    when(mockLevel.isWall(new Position(-1, 0))).thenReturn(false);
    when(mockLevel.isWall(new Position(1, 0))).thenReturn(false);
    when(mockLevel.isWall(new Position(0, -1))).thenReturn(false);
    when(mockLevel.isWall(new Position(1, 1))).thenReturn(false);
    return mockLevel;
  }

  // E = Empty
  // W = Wall
  //
  // E | E
  // ------
  // E | E
  private Level getMockLevelTwoByTwoEmpty() {
    Level mockLevel = mock(Level.class);
    when(mockLevel.isWall(new Position(0, 0))).thenReturn(false);
    when(mockLevel.isWall(new Position(1, 0))).thenReturn(false);
    when(mockLevel.isWall(new Position(0, 1))).thenReturn(false);
    when(mockLevel.isWall(new Position(1, 1))).thenReturn(false);
    return mockLevel;
  }

  // E = Empty
  // W = Wall
  //
  // W | W
  // ------
  // W | W
  private Level getMockLevelTwoByTwoWalls() {
    Level mockLevel = mock(Level.class);
    when(mockLevel.isWall(new Position(0, 0))).thenReturn(true);
    when(mockLevel.isWall(new Position(1, 0))).thenReturn(true);
    when(mockLevel.isWall(new Position(0, 1))).thenReturn(true);
    when(mockLevel.isWall(new Position(1, 1))).thenReturn(true);
    return mockLevel;
  }
}
