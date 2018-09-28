package ca.usherbrooke.pacman.model;

import ca.usherbrooke.pacman.model.exceptions.InvalidDirectionException;

public class PacmanMoveValidator implements IMoveValidator {

  private Level level;

  public PacmanMoveValidator(Level level) {
    this.level = level;
  }

  @Override
  public boolean isValid(IMoveRequest moveRequest) throws InvalidDirectionException {
    final Position targetPosition = getTargetPosition(moveRequest);
    return !level.isWall(targetPosition) && !level.isGhostGate(targetPosition);
  }

  @Override
  public Position getTargetPosition(IMoveRequest moveRequest) throws InvalidDirectionException {
    return new WrapAroundMoveRequestSolver(level).getTargetPosition(moveRequest);
  }

  @Override
  public boolean isDesiredDirectionValid(IMoveRequest moveRequest)
      throws InvalidDirectionException {
    final Position currentPosition = moveRequest.getPosition();
    final Position targetPosition = getTargetPosition(moveRequest);
    return !level.isWall(targetPosition) && !level.isGhostGate(targetPosition)
        && !level.isTunnel(currentPosition);
  }

}
