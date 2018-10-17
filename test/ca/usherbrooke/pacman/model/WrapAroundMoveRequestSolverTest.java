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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.model.exceptions.InvalidDirectionException;

public class WrapAroundMoveRequestSolverTest {

  private WrapAroundMoveRequestSolver moveRequestSolver;

  @Before
  public void setUp() {
    moveRequestSolver = new WrapAroundMoveRequestSolver(getMockLevelTwoByTwoEmpty());
  }

  @Test
  public void getTargetPositionNoWrapAround() throws InvalidDirectionException {
    IMoveRequest moveLeft = new MoveRequest(new Position(1, 0), Direction.LEFT);
    IMoveRequest moveRight = new MoveRequest(new Position(0, 0), Direction.RIGHT);
    IMoveRequest moveUp = new MoveRequest(new Position(0, 1), Direction.UP);
    IMoveRequest moveDown = new MoveRequest(new Position(0, 0), Direction.DOWN);
    assertEquals(new Position(0, 0), moveRequestSolver.getTargetPosition(moveLeft));
    assertEquals(new Position(1, 0), moveRequestSolver.getTargetPosition(moveRight));
    assertEquals(new Position(0, 0), moveRequestSolver.getTargetPosition(moveUp));
    assertEquals(new Position(0, 1), moveRequestSolver.getTargetPosition(moveDown));
  }

  @Test
  public void getTargetPositionWithWrapAround() throws InvalidDirectionException {
    IMoveRequest moveLeft = new MoveRequest(new Position(0, 0), Direction.LEFT);
    IMoveRequest moveRight = new MoveRequest(new Position(1, 0), Direction.RIGHT);
    IMoveRequest moveUp = new MoveRequest(new Position(0, 0), Direction.UP);
    IMoveRequest moveDown = new MoveRequest(new Position(0, 1), Direction.DOWN);
    assertEquals(new Position(1, 0), moveRequestSolver.getTargetPosition(moveLeft));
    assertEquals(new Position(0, 0), moveRequestSolver.getTargetPosition(moveRight));
    assertEquals(new Position(0, 1), moveRequestSolver.getTargetPosition(moveUp));
    assertEquals(new Position(0, 0), moveRequestSolver.getTargetPosition(moveDown));
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

}
