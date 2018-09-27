package ca.usherbrooke.pacman.model;

import ca.usherbrooke.pacman.model.exceptions.InvalidDirectionException;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class MovementManager {
  private static final String INVALID_DIRECTION_MSG = "Invalid direction. ";

  private final IGameObject gameObject;
  private IMoveValidator moveValidator;

  public MovementManager(IGameObject gameObject, IMoveValidator moveValidator) {
    this.gameObject = gameObject;
    this.moveValidator = moveValidator;
  }

  public void updatePosition() {
    MoveRequest desiredMoveRequest =
        new MoveRequest(gameObject.getPosition(), gameObject.getDesiredDirection());
    try {
      if (moveValidator.isDesiredDirectionValid(desiredMoveRequest)
          && moveValidator.isValid(desiredMoveRequest)) {
        gameObject.setPosition(moveValidator.getTargetPosition(desiredMoveRequest));
        return;
      }
    } catch (InvalidDirectionException exception) {
      WarningDialog.display(INVALID_DIRECTION_MSG, exception);
    }

    MoveRequest fallbackMoveRequest =
        new MoveRequest(gameObject.getPosition(), gameObject.getDirection());
    try {
      if (moveValidator.isValid(fallbackMoveRequest)) {
        gameObject.setPosition(moveValidator.getTargetPosition(fallbackMoveRequest));
      }
    } catch (InvalidDirectionException exception) {
      WarningDialog.display(INVALID_DIRECTION_MSG, exception);
    }

    setDirection(gameObject.getDesiredDirection());
  }

  public void setDirection(Direction direction) {
    gameObject.setDesiredDirection(direction);
    MoveRequest moveRequest = new MoveRequest(gameObject.getPosition(), direction);
    try {
      if (!moveValidator.isDesiredDirectionValid(moveRequest)) {
        return;
      }
    } catch (InvalidDirectionException exception) {
      WarningDialog.display(INVALID_DIRECTION_MSG, exception);
    }
    gameObject.setDirection(direction);
  }
}
