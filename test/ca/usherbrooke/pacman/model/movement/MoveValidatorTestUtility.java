package ca.usherbrooke.pacman.model.movement;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.exceptions.InvalidDirectionException;
import ca.usherbrooke.pacman.model.movements.IMoveValidator;
import ca.usherbrooke.pacman.model.movements.MoveRequest;
import ca.usherbrooke.pacman.model.position.Position;

public class MoveValidatorTestUtility {
  static public void assertCanMoveInAnyDirection(IMoveValidator moveValidator,
      Position sourcePosition) throws InvalidDirectionException {
    assertTrue(moveValidator.isValid(new MoveRequest(sourcePosition, Direction.LEFT)));
    assertTrue(moveValidator.isValid(new MoveRequest(sourcePosition, Direction.RIGHT)));
    assertTrue(moveValidator.isValid(new MoveRequest(sourcePosition, Direction.UP)));
    assertTrue(moveValidator.isValid(new MoveRequest(sourcePosition, Direction.DOWN)));
  }

  static public void assertCanFaceAnyDirection(IMoveValidator moveValidator,
      Position sourcePosition) throws InvalidDirectionException {
    assertTrue(
        moveValidator.isDesiredDirectionValid(new MoveRequest(sourcePosition, Direction.LEFT)));
    assertTrue(
        moveValidator.isDesiredDirectionValid(new MoveRequest(sourcePosition, Direction.RIGHT)));
    assertTrue(
        moveValidator.isDesiredDirectionValid(new MoveRequest(sourcePosition, Direction.UP)));
    assertTrue(
        moveValidator.isDesiredDirectionValid(new MoveRequest(sourcePosition, Direction.DOWN)));
  }

  static public void assertCannotMoveInAnyDirection(IMoveValidator moveValidator,
      Position sourcePosition) throws InvalidDirectionException {
    assertFalse(moveValidator.isValid(new MoveRequest(sourcePosition, Direction.LEFT)));
    assertFalse(moveValidator.isValid(new MoveRequest(sourcePosition, Direction.RIGHT)));
    assertFalse(moveValidator.isValid(new MoveRequest(sourcePosition, Direction.UP)));
    assertFalse(moveValidator.isValid(new MoveRequest(sourcePosition, Direction.DOWN)));
  }

  static public void assertCannotFaceAnyDirection(IMoveValidator moveValidator,
      Position sourcePosition) throws InvalidDirectionException {
    assertFalse(
        moveValidator.isDesiredDirectionValid(new MoveRequest(sourcePosition, Direction.LEFT)));
    assertFalse(
        moveValidator.isDesiredDirectionValid(new MoveRequest(sourcePosition, Direction.RIGHT)));
    assertFalse(
        moveValidator.isDesiredDirectionValid(new MoveRequest(sourcePosition, Direction.UP)));
    assertFalse(
        moveValidator.isDesiredDirectionValid(new MoveRequest(sourcePosition, Direction.DOWN)));
  }
}
