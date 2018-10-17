/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.movement;

import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.direction.IGameObject;
import ca.usherbrooke.pacman.model.direction.Position;
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


  public Position getPosition() {
    Position gameObjectPosition = gameObject.getPosition();
    MoveRequest desiredMoveRequest =
        new MoveRequest(gameObjectPosition, gameObject.getDesiredDirection());
    try {
      if (moveValidator.isDesiredDirectionValid(desiredMoveRequest)
          && moveValidator.isValid(desiredMoveRequest)) {
        return moveValidator.getTargetPosition(desiredMoveRequest);
      }
    } catch (InvalidDirectionException exception) {
      WarningDialog.display(INVALID_DIRECTION_MSG, exception);
    }

    MoveRequest fallbackMoveRequest =
        new MoveRequest(gameObjectPosition, gameObject.getDirection());
    try {
      if (moveValidator.isValid(fallbackMoveRequest)) {
        gameObjectPosition = moveValidator.getTargetPosition(fallbackMoveRequest);
      }
    } catch (InvalidDirectionException exception) {
      WarningDialog.display(INVALID_DIRECTION_MSG, exception);
    }

    setDirection(gameObject.getDesiredDirection());
    return gameObjectPosition;
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

  public IGameObject getManagedGameObject() {
    return gameObject;
  }
}
