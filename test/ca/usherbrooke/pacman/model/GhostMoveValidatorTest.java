package ca.usherbrooke.pacman.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;
import ca.usherbrooke.pacman.model.exceptions.InvalidDirectionException;

public class GhostMoveValidatorTest {

  @Test
  public void movesToEmptySpacesAreValid() throws InvalidDirectionException {
    Level mockLevel = getMockLevelTwoByTwoEmpty();
    IMoveValidator moveValidator = new GhostMoveValidator(mockLevel);
    IMoveRequest moveLeft = new MoveRequest(new Position(1, 0), Direction.LEFT);
    IMoveRequest moveRight = new MoveRequest(new Position(0, 0), Direction.RIGHT);
    IMoveRequest moveUp = new MoveRequest(new Position(0, 1), Direction.UP);
    IMoveRequest moveDown = new MoveRequest(new Position(0, 0), Direction.DOWN);
    assertTrue(moveValidator.isValid(moveLeft));
    assertTrue(moveValidator.isValid(moveRight));
    assertTrue(moveValidator.isValid(moveUp));
    assertTrue(moveValidator.isValid(moveDown));
    assertTrue(moveValidator.isDesiredDirectionValid(moveLeft));
    assertTrue(moveValidator.isDesiredDirectionValid(moveRight));
    assertTrue(moveValidator.isDesiredDirectionValid(moveUp));
    assertTrue(moveValidator.isDesiredDirectionValid(moveDown));
  }

  @Test
  public void movesToGhostGatesAreValid() throws InvalidDirectionException {
    Level mockLevel = getMockLevelTwoByTwoGhostGates();
    IMoveValidator moveValidator = new GhostMoveValidator(mockLevel);
    IMoveRequest moveLeft = new MoveRequest(new Position(1, 0), Direction.LEFT);
    IMoveRequest moveRight = new MoveRequest(new Position(0, 0), Direction.RIGHT);
    IMoveRequest moveUp = new MoveRequest(new Position(0, 1), Direction.UP);
    IMoveRequest moveDown = new MoveRequest(new Position(0, 0), Direction.DOWN);
    assertTrue(moveValidator.isValid(moveLeft));
    assertTrue(moveValidator.isValid(moveRight));
    assertTrue(moveValidator.isValid(moveUp));
    assertTrue(moveValidator.isValid(moveDown));
    assertTrue(moveValidator.isDesiredDirectionValid(moveLeft));
    assertTrue(moveValidator.isDesiredDirectionValid(moveRight));
    assertTrue(moveValidator.isDesiredDirectionValid(moveUp));
    assertTrue(moveValidator.isDesiredDirectionValid(moveDown));
  }

  @Test
  public void movesIntoAWallAreInvalid() throws InvalidDirectionException {
    Level mockLevel = getMockLevelTwoByTwoWalls();
    IMoveValidator moveValidator = new GhostMoveValidator(mockLevel);
    IMoveRequest moveLeft = new MoveRequest(new Position(1, 0), Direction.LEFT);
    IMoveRequest moveRight = new MoveRequest(new Position(0, 0), Direction.RIGHT);
    IMoveRequest moveUp = new MoveRequest(new Position(0, 1), Direction.UP);
    IMoveRequest moveDown = new MoveRequest(new Position(0, 0), Direction.DOWN);
    assertFalse(moveValidator.isValid(moveLeft));
    assertFalse(moveValidator.isValid(moveRight));
    assertFalse(moveValidator.isValid(moveUp));
    assertFalse(moveValidator.isValid(moveDown));
    assertFalse(moveValidator.isDesiredDirectionValid(moveLeft));
    assertFalse(moveValidator.isDesiredDirectionValid(moveRight));
    assertFalse(moveValidator.isDesiredDirectionValid(moveUp));
    assertFalse(moveValidator.isDesiredDirectionValid(moveDown));
  }

  @Test
  public void movesIntoATunnelAreInvalid() throws InvalidDirectionException {
    Level mockLevel = getMockLevelTwoByTwoTunnels();
    IMoveValidator moveValidator = new GhostMoveValidator(mockLevel);
    IMoveRequest moveLeft = new MoveRequest(new Position(1, 0), Direction.LEFT);
    IMoveRequest moveRight = new MoveRequest(new Position(0, 0), Direction.RIGHT);
    IMoveRequest moveUp = new MoveRequest(new Position(0, 1), Direction.UP);
    IMoveRequest moveDown = new MoveRequest(new Position(0, 0), Direction.DOWN);
    assertFalse(moveValidator.isValid(moveLeft));
    assertFalse(moveValidator.isValid(moveRight));
    assertFalse(moveValidator.isValid(moveUp));
    assertFalse(moveValidator.isValid(moveDown));
    assertFalse(moveValidator.isDesiredDirectionValid(moveLeft));
    assertFalse(moveValidator.isDesiredDirectionValid(moveRight));
    assertFalse(moveValidator.isDesiredDirectionValid(moveUp));
    assertFalse(moveValidator.isDesiredDirectionValid(moveDown));
  }

  @Test
  public void getTargetPositionNoWrapAround() throws InvalidDirectionException {
    Level mockLevel = getMockLevelTwoByTwoEmpty();
    IMoveValidator moveValidator = new GhostMoveValidator(mockLevel);
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
    IMoveValidator moveValidator = new GhostMoveValidator(mockLevel);
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

  // G = Ghost gate
  //
  // G | G
  // -----
  // G | G
  private Level getMockLevelTwoByTwoGhostGates() {
    Level mockLevel = mock(Level.class);
    when(mockLevel.getWidth()).thenReturn(2);
    when(mockLevel.getHeight()).thenReturn(2);
    when(mockLevel.isGhostGate(new Position(0, 0))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(1, 0))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(0, 1))).thenReturn(true);
    when(mockLevel.isGhostGate(new Position(1, 1))).thenReturn(true);
    return mockLevel;
  }

  // T = Tunnel
  //
  // T | T
  // -----
  // T | T
  private Level getMockLevelTwoByTwoTunnels() {
    Level mockLevel = mock(Level.class);
    when(mockLevel.getWidth()).thenReturn(2);
    when(mockLevel.getHeight()).thenReturn(2);
    when(mockLevel.isTunnel(new Position(0, 0))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(1, 0))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(0, 1))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(1, 1))).thenReturn(true);
    return mockLevel;
  }
}
