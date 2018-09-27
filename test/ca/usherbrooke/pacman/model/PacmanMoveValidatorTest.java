package ca.usherbrooke.pacman.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;
import ca.usherbrooke.pacman.model.exceptions.InvalidDirectionException;

public class PacmanMoveValidatorTest {

  @Test
  public void movesToEmptySpacesAreValid() throws InvalidDirectionException {
    Level mockLevel = getMockLevelTwoByTwoEmpty();
    IMoveValidator moveValidator = new MoveValidator(mockLevel);
    IMoveRequest moveLeft = new MoveRequest(new Position(1, 0), Direction.LEFT);
    IMoveRequest moveRight = new MoveRequest(new Position(0, 0), Direction.RIGHT);
    IMoveRequest moveUp = new MoveRequest(new Position(0, 1), Direction.UP);
    IMoveRequest moveDown = new MoveRequest(new Position(0, 0), Direction.DOWN);
    assertTrue(moveValidator.isValid(moveLeft));
    assertTrue(moveValidator.isValid(moveRight));
    assertTrue(moveValidator.isValid(moveUp));
    assertTrue(moveValidator.isValid(moveDown));
  }

  @Test
  public void movesIntoAWallAreInvalid() throws InvalidDirectionException {
    Level mockLevel = getMockLevelTwoByTwoWalls();
    IMoveValidator moveValidator = new MoveValidator(mockLevel);
    IMoveRequest moveLeft = new MoveRequest(new Position(1, 0), Direction.LEFT);
    IMoveRequest moveRight = new MoveRequest(new Position(0, 0), Direction.RIGHT);
    IMoveRequest moveUp = new MoveRequest(new Position(0, 1), Direction.UP);
    IMoveRequest moveDown = new MoveRequest(new Position(0, 0), Direction.DOWN);
    assertFalse(moveValidator.isValid(moveLeft));
    assertFalse(moveValidator.isValid(moveRight));
    assertFalse(moveValidator.isValid(moveUp));
    assertFalse(moveValidator.isValid(moveDown));
  }

  @Test
  public void movesIntoLevelEdgesAreValidIfEmptyOnTheOtherSide() throws InvalidDirectionException {
    Level mockLevel = getMockLevelSingleEmpty();
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

  @Test
  public void movesIntoLevelEdgesAreInvalidIfWallIsOnTheOtherSide()
      throws InvalidDirectionException {
    Level mockLevel = getMockLevelSingleWall();
    IMoveValidator moveValidator = new MoveValidator(mockLevel);
    IMoveRequest moveLeft = new MoveRequest(new Position(0, 0), Direction.LEFT);
    IMoveRequest moveRight = new MoveRequest(new Position(0, 0), Direction.RIGHT);
    IMoveRequest moveUp = new MoveRequest(new Position(0, 0), Direction.UP);
    IMoveRequest moveDown = new MoveRequest(new Position(0, 0), Direction.DOWN);
    assertFalse(moveValidator.isValid(moveLeft));
    assertFalse(moveValidator.isValid(moveRight));
    assertFalse(moveValidator.isValid(moveUp));
    assertFalse(moveValidator.isValid(moveDown));
  }

  @Test
  public void turnAroundIsInvalidIfPacmanIsInTunnel() throws InvalidDirectionException {
    Level mockLevel = getMockLevelSingleTunnel();
    IMoveValidator moveValidator = new MoveValidator(mockLevel);
    IMoveRequest moveLeft = new MoveRequest(new Position(0, 0), Direction.LEFT);
    assertFalse(moveValidator.isDesiredDirectionValid(moveLeft));
  }

  @Test
  public void turnAroundIsValidIfPacmanIsNotInTunnel() throws InvalidDirectionException {
    Level mockLevel = getMockLevelSingleEmpty();
    IMoveValidator moveValidator = new MoveValidator(mockLevel);
    IMoveRequest moveLeft = new MoveRequest(new Position(0, 0), Direction.LEFT);
    assertTrue(moveValidator.isDesiredDirectionValid(moveLeft));
  }

  @Test
  public void getTargetPositionNoWrapAround() throws InvalidDirectionException {
    Level mockLevel = getMockLevelTwoByTwoEmpty();
    IMoveValidator moveValidator = new MoveValidator(mockLevel);
    IMoveRequest moveLeft = new MoveRequest(new Position(1, 0), Direction.LEFT);
    IMoveRequest moveRight = new MoveRequest(new Position(0, 0), Direction.RIGHT);
    IMoveRequest moveUp = new MoveRequest(new Position(0, 1), Direction.UP);
    IMoveRequest moveDown = new MoveRequest(new Position(0, 0), Direction.DOWN);
    assertEquals(new Position(0, 0), moveValidator.getTargetPosition(moveLeft));
    assertEquals(new Position(1, 0), moveValidator.getTargetPosition(moveRight));
    assertEquals(new Position(0, 0), moveValidator.getTargetPosition(moveUp));
    assertEquals(new Position(0, 1), moveValidator.getTargetPosition(moveDown));
  }

  @Test
  public void getTargetPositionWithWrapAround() throws InvalidDirectionException {
    Level mockLevel = getMockLevelTwoByTwoEmpty();
    IMoveValidator moveValidator = new MoveValidator(mockLevel);
    IMoveRequest moveLeft = new MoveRequest(new Position(0, 0), Direction.LEFT);
    IMoveRequest moveRight = new MoveRequest(new Position(1, 0), Direction.RIGHT);
    IMoveRequest moveUp = new MoveRequest(new Position(0, 0), Direction.UP);
    IMoveRequest moveDown = new MoveRequest(new Position(0, 1), Direction.DOWN);
    assertEquals(new Position(1, 0), moveValidator.getTargetPosition(moveLeft));
    assertEquals(new Position(0, 0), moveValidator.getTargetPosition(moveRight));
    assertEquals(new Position(0, 1), moveValidator.getTargetPosition(moveUp));
    assertEquals(new Position(0, 0), moveValidator.getTargetPosition(moveDown));
  }

  // E = Empty
  // W = Wall
  //
  // -----
  // | W |
  // -----
  private Level getMockLevelSingleWall() {
    Level mockLevel = mock(Level.class);
    when(mockLevel.getWidth()).thenReturn(1);
    when(mockLevel.getHeight()).thenReturn(1);
    when(mockLevel.isWall(new Position(0, 0))).thenReturn(true);
    return mockLevel;
  }

  // E = Empty
  // W = Wall
  //
  // -----
  // | E |
  // -----
  private Level getMockLevelSingleEmpty() {
    Level mockLevel = mock(Level.class);
    when(mockLevel.getWidth()).thenReturn(1);
    when(mockLevel.getHeight()).thenReturn(1);
    when(mockLevel.isWall(new Position(0, 0))).thenReturn(false);
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
    when(mockLevel.getWidth()).thenReturn(2);
    when(mockLevel.getHeight()).thenReturn(2);
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
    when(mockLevel.getWidth()).thenReturn(2);
    when(mockLevel.getHeight()).thenReturn(2);
    when(mockLevel.isWall(new Position(0, 0))).thenReturn(true);
    when(mockLevel.isWall(new Position(1, 0))).thenReturn(true);
    when(mockLevel.isWall(new Position(0, 1))).thenReturn(true);
    when(mockLevel.isWall(new Position(1, 1))).thenReturn(true);
    return mockLevel;
  }

  // T = Tunnel
  //
  // -----
  // | T |
  // -----
  private Level getMockLevelSingleTunnel() {
    Level mockLevel = mock(Level.class);
    when(mockLevel.getWidth()).thenReturn(1);
    when(mockLevel.getHeight()).thenReturn(1);
    when(mockLevel.isWall(new Position(0, 0))).thenReturn(false);
    when(mockLevel.isTunnel(new Position(0, 0))).thenReturn(true);
    return mockLevel;
  }
}
