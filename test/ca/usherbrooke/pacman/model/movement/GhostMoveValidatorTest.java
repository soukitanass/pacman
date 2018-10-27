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
import org.junit.Test;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.exceptions.InvalidDirectionException;
import ca.usherbrooke.pacman.model.movements.GhostMoveValidator;
import ca.usherbrooke.pacman.model.movements.IMoveRequest;
import ca.usherbrooke.pacman.model.movements.IMoveValidator;
import ca.usherbrooke.pacman.model.movements.MoveRequest;
import ca.usherbrooke.pacman.model.objects.IHasInvincibilityStatus;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.objects.MockLevelFactory;
import ca.usherbrooke.pacman.model.position.Position;

public class GhostMoveValidatorTest {

  private static final IHasInvincibilityStatus HAS_INVINCIBILITY_STATUS_ALWAYS_FALSE = () -> {
    return false;
  };

  private static final IHasInvincibilityStatus HAS_INVINCIBILITY_STATUS_ALWAYS_TRUE = () -> {
    return true;
  };

  @Test
  public void movesToEmptySpacesAreValid() throws InvalidDirectionException {
    Level mockLevel = MockLevelFactory.getMockLevelThreeByThreeEmpty();
    IMoveValidator moveValidator =
        new GhostMoveValidator(mockLevel, HAS_INVINCIBILITY_STATUS_ALWAYS_FALSE);
    MoveValidatorTestUtility.assertCanMoveInAnyDirection(moveValidator, new Position(1, 1));
    MoveValidatorTestUtility.assertCanFaceAnyDirection(moveValidator, new Position(1, 1));
  }

  @Test
  public void movesIntoAWallAreInvalid() throws InvalidDirectionException {
    Level mockLevel = MockLevelFactory.getMockLevelThreeByThreeWalls();
    IMoveValidator moveValidator =
        new GhostMoveValidator(mockLevel, HAS_INVINCIBILITY_STATUS_ALWAYS_FALSE);
    MoveValidatorTestUtility.assertCannotMoveInAnyDirection(moveValidator, new Position(1, 1));
    MoveValidatorTestUtility.assertCannotFaceAnyDirection(moveValidator, new Position(1, 1));
  }

  @Test
  public void movesIntoATunnelAreInvalid() throws InvalidDirectionException {
    Level mockLevel = MockLevelFactory.getMockLevelThreeByThreeTunnels();
    IMoveValidator moveValidator =
        new GhostMoveValidator(mockLevel, HAS_INVINCIBILITY_STATUS_ALWAYS_FALSE);
    MoveValidatorTestUtility.assertCannotMoveInAnyDirection(moveValidator, new Position(1, 1));
    MoveValidatorTestUtility.assertCannotFaceAnyDirection(moveValidator, new Position(1, 1));
  }

  @Test
  public void movesFromGhostRoomToGhostGatesAreValidWhenNotDuringInvincibilityMode()
      throws InvalidDirectionException {
    Level mockLevel = MockLevelFactory.getMockLevelSingleGhostRoomSurroundedByGhostGates();
    IMoveValidator moveValidator =
        new GhostMoveValidator(mockLevel, HAS_INVINCIBILITY_STATUS_ALWAYS_FALSE);
    MoveValidatorTestUtility.assertCanMoveInAnyDirection(moveValidator, new Position(1, 1));
    MoveValidatorTestUtility.assertCanFaceAnyDirection(moveValidator, new Position(1, 1));
  }

  @Test
  public void movesFromGhostRoomToGhostGatesAreInvalidDuringInvincibilityMode()
      throws InvalidDirectionException {
    Level mockLevel = MockLevelFactory.getMockLevelSingleGhostRoomSurroundedByGhostGates();
    IMoveValidator moveValidator =
        new GhostMoveValidator(mockLevel, HAS_INVINCIBILITY_STATUS_ALWAYS_TRUE);
    MoveValidatorTestUtility.assertCannotMoveInAnyDirection(moveValidator, new Position(1, 1));
    MoveValidatorTestUtility.assertCannotFaceAnyDirection(moveValidator, new Position(1, 1));
  }

  @Test
  public void movesFromGhostGateToGhostRoomsAreInvalid() throws InvalidDirectionException {
    Level mockLevel = MockLevelFactory.getMockLevelSingleGhostGateSurroundedByGhostRooms();
    IMoveValidator moveValidator =
        new GhostMoveValidator(mockLevel, HAS_INVINCIBILITY_STATUS_ALWAYS_FALSE);
    MoveValidatorTestUtility.assertCannotMoveInAnyDirection(moveValidator, new Position(1, 1));
    MoveValidatorTestUtility.assertCannotFaceAnyDirection(moveValidator, new Position(1, 1));
  }

  @Test
  public void movesFromGhostGateToEmptyAreValid() throws InvalidDirectionException {
    Level mockLevel = MockLevelFactory.getMockLevelSingleGhostGateSurroundedByEmptiness();
    IMoveValidator moveValidator =
        new GhostMoveValidator(mockLevel, HAS_INVINCIBILITY_STATUS_ALWAYS_FALSE);
    MoveValidatorTestUtility.assertCanMoveInAnyDirection(moveValidator, new Position(1, 1));
    MoveValidatorTestUtility.assertCanFaceAnyDirection(moveValidator, new Position(1, 1));
  }

  @Test
  public void movesFromEmptyToGhostGateAreInvalid() throws InvalidDirectionException {
    Level mockLevel = MockLevelFactory.getMockLevelSingleEmptySurroundedByGhostGates();
    IMoveValidator moveValidator =
        new GhostMoveValidator(mockLevel, HAS_INVINCIBILITY_STATUS_ALWAYS_FALSE);
    MoveValidatorTestUtility.assertCannotMoveInAnyDirection(moveValidator, new Position(1, 1));
    MoveValidatorTestUtility.assertCannotFaceAnyDirection(moveValidator, new Position(1, 1));
  }

  @Test
  public void getTargetPositionNoWrapAround() throws InvalidDirectionException {
    Level mockLevel = MockLevelFactory.getMockLevelTwoByTwoEmpty();
    IMoveValidator moveValidator =
        new GhostMoveValidator(mockLevel, HAS_INVINCIBILITY_STATUS_ALWAYS_FALSE);
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
    Level mockLevel = MockLevelFactory.getMockLevelTwoByTwoEmpty();
    IMoveValidator moveValidator =
        new GhostMoveValidator(mockLevel, HAS_INVINCIBILITY_STATUS_ALWAYS_FALSE);
    IMoveRequest moveLeft = new MoveRequest(new Position(0, 0), Direction.LEFT);
    IMoveRequest moveRight = new MoveRequest(new Position(1, 0), Direction.RIGHT);
    IMoveRequest moveUp = new MoveRequest(new Position(0, 0), Direction.UP);
    IMoveRequest moveDown = new MoveRequest(new Position(0, 1), Direction.DOWN);
    assertEquals(new Position(1, 0), moveValidator.getTargetPosition(moveLeft));
    assertEquals(new Position(0, 0), moveValidator.getTargetPosition(moveRight));
    assertEquals(new Position(0, 1), moveValidator.getTargetPosition(moveUp));
    assertEquals(new Position(0, 0), moveValidator.getTargetPosition(moveDown));
  }

}
