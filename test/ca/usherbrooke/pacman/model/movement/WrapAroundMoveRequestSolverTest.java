/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.movement;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.exceptions.InvalidDirectionException;
import ca.usherbrooke.pacman.model.movements.IMoveRequest;
import ca.usherbrooke.pacman.model.movements.MoveRequest;
import ca.usherbrooke.pacman.model.movements.WrapAroundMoveRequestSolver;
import ca.usherbrooke.pacman.model.objects.MockLevelFactory;
import ca.usherbrooke.pacman.model.position.Position;

public class WrapAroundMoveRequestSolverTest {

  private WrapAroundMoveRequestSolver moveRequestSolver;

  @Before
  public void setUp() {
    moveRequestSolver =
        new WrapAroundMoveRequestSolver(MockLevelFactory.getMockLevelTwoByTwoEmpty());
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

}
