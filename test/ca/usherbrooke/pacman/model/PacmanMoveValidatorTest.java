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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.exceptions.InvalidDirectionException;
import ca.usherbrooke.pacman.model.movements.IMoveRequest;
import ca.usherbrooke.pacman.model.movements.IMoveValidator;
import ca.usherbrooke.pacman.model.movements.MoveRequest;
import ca.usherbrooke.pacman.model.movements.PacmanMoveValidator;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.position.Position;

public class PacmanMoveValidatorTest {

  @Test
  public void movesToEmptySpacesAreValid() throws InvalidDirectionException {
    Level mockLevel = getMockLevelTwoByTwoEmpty();
    IMoveValidator moveValidator = new PacmanMoveValidator(mockLevel);
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
    IMoveValidator moveValidator = new PacmanMoveValidator(mockLevel);
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
  public void movesIntoAGhostGateAreInvalid() throws InvalidDirectionException {
    Level mockLevel = getMockLevelTwoByTwoGhostGates();
    IMoveValidator moveValidator = new PacmanMoveValidator(mockLevel);
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
  public void movesIntoLevelEdgesAreValidIfEmptyOnTheOtherSide() throws InvalidDirectionException {
    Level mockLevel = getMockLevelSingleEmpty();
    IMoveValidator moveValidator = new PacmanMoveValidator(mockLevel);
    IMoveRequest moveLeft = new MoveRequest(new Position(0, 0), Direction.LEFT);
    IMoveRequest moveRight = new MoveRequest(new Position(0, 0), Direction.RIGHT);
    IMoveRequest moveUp = new MoveRequest(new Position(0, 0), Direction.UP);
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
  public void movesIntoLevelEdgesAreInvalidIfWallIsOnTheOtherSide()
      throws InvalidDirectionException {
    Level mockLevel = getMockLevelSingleWall();
    IMoveValidator moveValidator = new PacmanMoveValidator(mockLevel);
    IMoveRequest moveLeft = new MoveRequest(new Position(0, 0), Direction.LEFT);
    IMoveRequest moveRight = new MoveRequest(new Position(0, 0), Direction.RIGHT);
    IMoveRequest moveUp = new MoveRequest(new Position(0, 0), Direction.UP);
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
  public void movesOntoTunnelTilesAreValid() throws InvalidDirectionException {
    Level mockLevel = getMockLevelTwoByTwoTunnel();
    IMoveValidator moveValidator = new PacmanMoveValidator(mockLevel);
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
  public void cannotTurnInTunnel() throws InvalidDirectionException {
    Level mockLevel = getMockLevelSingleTunnelSurroundedByEmptiness();
    IMoveValidator moveValidator = new PacmanMoveValidator(mockLevel);
    IMoveRequest moveLeft = new MoveRequest(new Position(1, 1), Direction.LEFT);
    IMoveRequest moveRight = new MoveRequest(new Position(1, 1), Direction.RIGHT);
    IMoveRequest moveUp = new MoveRequest(new Position(1, 1), Direction.UP);
    IMoveRequest moveDown = new MoveRequest(new Position(1, 1), Direction.DOWN);
    assertTrue(moveValidator.isValid(moveLeft));
    assertTrue(moveValidator.isValid(moveRight));
    assertTrue(moveValidator.isValid(moveUp));
    assertTrue(moveValidator.isValid(moveDown));
    assertFalse(moveValidator.isDesiredDirectionValid(moveLeft));
    assertFalse(moveValidator.isDesiredDirectionValid(moveRight));
    assertFalse(moveValidator.isDesiredDirectionValid(moveUp));
    assertFalse(moveValidator.isDesiredDirectionValid(moveDown));
  }

  @Test
  public void canTurnIfNotInTunnel() throws InvalidDirectionException {
    Level mockLevel = getMockLevelSingleEmptySurroundedByTunnels();
    IMoveValidator moveValidator = new PacmanMoveValidator(mockLevel);
    IMoveRequest moveLeft = new MoveRequest(new Position(1, 1), Direction.LEFT);
    IMoveRequest moveRight = new MoveRequest(new Position(1, 1), Direction.RIGHT);
    IMoveRequest moveUp = new MoveRequest(new Position(1, 1), Direction.UP);
    IMoveRequest moveDown = new MoveRequest(new Position(1, 1), Direction.DOWN);
    assertTrue(moveValidator.isDesiredDirectionValid(moveLeft));
    assertTrue(moveValidator.isDesiredDirectionValid(moveRight));
    assertTrue(moveValidator.isDesiredDirectionValid(moveUp));
    assertTrue(moveValidator.isDesiredDirectionValid(moveDown));
  }

  @Test
  public void getTargetPositionNoWrapAround() throws InvalidDirectionException {
    Level mockLevel = getMockLevelTwoByTwoEmpty();
    IMoveValidator moveValidator = new PacmanMoveValidator(mockLevel);
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
    IMoveValidator moveValidator = new PacmanMoveValidator(mockLevel);
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
  // E = Empty
  //
  // E | E | E
  // ---------
  // E | T | E
  // ---------
  // E | E | E
  private Level getMockLevelSingleTunnelSurroundedByEmptiness() {
    Level mockLevel = mock(Level.class);
    when(mockLevel.getWidth()).thenReturn(3);
    when(mockLevel.getHeight()).thenReturn(3);
    when(mockLevel.isTunnel(new Position(1, 1))).thenReturn(true);
    return mockLevel;
  }

  // T = Tunnel
  // E = Empty
  //
  // T | T | T
  // ---------
  // T | E | T
  // ---------
  // T | T | T
  private Level getMockLevelSingleEmptySurroundedByTunnels() {
    Level mockLevel = mock(Level.class);
    when(mockLevel.getWidth()).thenReturn(3);
    when(mockLevel.getHeight()).thenReturn(3);
    when(mockLevel.isTunnel(new Position(0, 0))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(1, 0))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(2, 0))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(0, 1))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(2, 1))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(0, 2))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(1, 2))).thenReturn(true);
    when(mockLevel.isTunnel(new Position(2, 2))).thenReturn(true);
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
  private Level getMockLevelTwoByTwoTunnel() {
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
