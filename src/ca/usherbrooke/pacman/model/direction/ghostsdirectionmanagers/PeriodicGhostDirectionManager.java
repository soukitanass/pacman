/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.direction.ghostsdirectionmanagers;

import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.direction.IDirectionGenerator;
import ca.usherbrooke.pacman.model.exceptions.InvalidDirectionException;
import ca.usherbrooke.pacman.model.movements.GhostMoveValidator;
import ca.usherbrooke.pacman.model.movements.MoveRequest;
import ca.usherbrooke.pacman.model.objects.IGameObject;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.objects.PacMan;
import ca.usherbrooke.pacman.model.position.Position;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class PeriodicGhostDirectionManager {
  private final GhostMoveValidator moveValidator;
  private IDirectionGenerator directionGenerator;
  private Direction lastDirection = Direction.UP;
  private int period;
  private IGameObject ghost;
  private IGameModel gameModel;
  private int updatesCounter;
  private IDirectionGenerator backupDirectionGenerator;

  public PeriodicGhostDirectionManager(IGameModel gameModel, IDirectionGenerator directionGenerator,
      IGameObject gameObject, int period, IDirectionGenerator backupDirectionGenerator) {
    Level level = gameModel.getCurrentLevel();
    moveValidator = new GhostMoveValidator(level, level.getPacMan());
    this.directionGenerator = directionGenerator;
    this.gameModel = gameModel;
    this.ghost = gameObject;
    this.period = period;
    this.backupDirectionGenerator = backupDirectionGenerator;
  }

  public void update() {
    Direction newGhostDirection = getOverridenDirectionToExitGhostRoom();
    if (newGhostDirection == null) {
      newGhostDirection = getOverridenDirectionToEscapeInvinciblePacman();
    }
    if (newGhostDirection == null) {
      newGhostDirection = directionGenerator.getOverridenDirection();
    }
    ++updatesCounter;
    if (period == updatesCounter) {
      updatesCounter = 0;
      if (newGhostDirection == null) {
        newGhostDirection = directionGenerator.get();
      }
    }
    newGhostDirection = getOverrideDirectionToNeverBeInvalid(newGhostDirection);
    gameModel.setDirection(ghost, newGhostDirection);
  }

  private Direction getOverrideDirectionToNeverBeInvalid(Direction desiredDirection) {
    final Position ghostPosition = ghost.getPosition();
    if (desiredDirection != null && isDirectionValid(ghostPosition, desiredDirection)) {
      return desiredDirection;
    }
    if (isDirectionValid(ghostPosition, ghost.getDesiredDirection())) {
      return ghost.getDesiredDirection();
    }
    while (true) {
      Direction backupDirection = backupDirectionGenerator.get();
      if (isDirectionValid(ghostPosition, backupDirection)) {
        return backupDirection;
      }
    }
  }

  // Return direction for escaping pacman or null if the ghost is not escaping.
  // If null is returned, we should use a fallback method to choose a direction.
  private Direction getOverridenDirectionToEscapeInvinciblePacman() {
    Level level = gameModel.getCurrentLevel();
    PacMan pacman = level.getPacMan();
    if (!pacman.isInvincible()) {
      return null;
    }
    final Position pacmanPosition = pacman.getPosition();
    final Position ghostPosition = ghost.getPosition();
    final Direction escapeFromPacmanDirection =
        level.getDirectionIfInLineOfSight(pacmanPosition, ghostPosition);
    return escapeFromPacmanDirection;
  }

  private Direction getOverridenDirectionToExitGhostRoom() {
    Level level = gameModel.getCurrentLevel();
    if (level.isGhostRoom(ghost.getPosition())) {
      final Position ghostPosition = ghost.getPosition();
      if (isUpPositionValid(ghostPosition)) {
        return lastDirection = Direction.UP;
      } else if (isDirectionValid(ghostPosition, lastDirection)) {
        return lastDirection;
      } else if (isLeftPositionValid(ghostPosition)) {
        return lastDirection = Direction.LEFT;
      } else if (isRightPositionValid(ghostPosition)) {
        return lastDirection = Direction.RIGHT;
      } else {
        return directionGenerator.get();
      }
    }
    return null;
  }

  private boolean isDirectionValid(Position position, Direction direction) {
    final MoveRequest desiredMoveRequest = new MoveRequest(position, direction);
    try {
      return moveValidator.isDesiredDirectionValid(desiredMoveRequest);
    } catch (InvalidDirectionException exception) {
      WarningDialog.display("Invalid Direction ", exception);
    }
    return false;
  }

  private boolean isLeftPositionValid(Position position) {
    return isDirectionValid(position, Direction.LEFT);
  }

  private boolean isRightPositionValid(Position position) {
    return isDirectionValid(position, Direction.RIGHT);
  }

  private boolean isUpPositionValid(Position position) {
    return isDirectionValid(position, Direction.UP);
  }
}
