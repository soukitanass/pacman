/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.direction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import ca.usherbrooke.pacman.model.exceptions.InvalidDirectionException;
import ca.usherbrooke.pacman.model.movements.GhostMoveValidator;
import ca.usherbrooke.pacman.model.movements.MoveRequest;
import ca.usherbrooke.pacman.model.objects.Ghost;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.objects.PacMan;
import ca.usherbrooke.pacman.model.position.Position;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class PinkyDirectionGenerator implements IDirectionGenerator {
  private int leftLimit;
  private int rightLimit;
  private int bottomLimit;
  private int topLimit;

  private final GhostMoveValidator moveValidator;
  private final IDirectionGenerator randomDirectionGenerator;
  private final Ghost ghost;
  private final Level level;

  private Direction directionToPacman;
  private Direction lastDirection;

  public PinkyDirectionGenerator(IDirectionGenerator randomDirectionGenerator, Ghost ghost,
      Level level) {
    this.randomDirectionGenerator = randomDirectionGenerator;
    this.level = level;
    this.ghost = ghost;
    setLimitWhereGhostCanGoOnTheMap();
    moveValidator = new GhostMoveValidator(level, level.getPacMan());
  }

  @Override
  public Direction get() {
    Direction direction = getDirectionToPacman();
    if (direction == null) {
      return randomDirectionGenerator.get();
    }
    return direction;
  }

  @Override
  public Direction getOverridenDirection() {
    Direction direction = getPacmanDirectionIfInLineOfSight();
    if (direction != null) {
      directionToPacman = direction;
    }
    return getDirectionToPacman();
  }

  private Direction getDirectionToPacman() {
    if (directionToPacman == null) {
      return null;
    }

    Position ghostPosition = ghost.getPosition();
    if (isAtTheLimitOfTheMap(ghostPosition, directionToPacman)
        || level.isGhostRoom(ghostPosition)) {
      directionToPacman = null;
      return null;
    }

    if ((directionToPacman == Direction.LEFT && !isLeftPositionValid(ghostPosition))
        || (directionToPacman == Direction.RIGHT && !isRightPositionValid(ghostPosition))) {
      if (lastDirection != null && isDirectionValid(ghostPosition, lastDirection)) {
        return lastDirection;
      } else if (isDownPositionValid(ghostPosition)) {
        lastDirection = Direction.DOWN;
        return lastDirection;
      } else if (isUpPositionValid(ghostPosition)) {
        lastDirection = Direction.UP;
        return lastDirection;
      }
    }

    if ((directionToPacman == Direction.UP && !isUpPositionValid(ghostPosition))
        || (directionToPacman == Direction.DOWN && !isDownPositionValid(ghostPosition))) {
      if (lastDirection != null && isDirectionValid(ghostPosition, lastDirection)) {
        return lastDirection;
      } else if (isRightPositionValid(ghostPosition)) {
        lastDirection = Direction.RIGHT;
        return lastDirection;
      } else if (isLeftPositionValid(ghostPosition)) {
        lastDirection = Direction.LEFT;
        return lastDirection;
      }
    }

    return directionToPacman;
  }

  private Direction getPacmanDirectionIfInLineOfSight() {
    final PacMan pacman = level.getPacMan();
    return level.getDirectionIfInLineOfSight(ghost.getPosition(), pacman.getPosition(), true);
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

  private boolean isDownPositionValid(Position position) {
    return isDirectionValid(position, Direction.DOWN);
  }

  private boolean isAtTheLimitOfTheMap(Position position, Direction direction) {
    return (position.getY() == topLimit && direction == Direction.UP)
        || (position.getY() == bottomLimit && direction == Direction.DOWN)
        || (position.getX() == leftLimit && direction == Direction.LEFT)
        || (position.getX() == rightLimit && direction == Direction.RIGHT);
  }

  private void setLimitWhereGhostCanGoOnTheMap() {
    List<Position> validPosition = new ArrayList<>();
    List<List<Integer>> map = level.getMap();
    for (int i = 0; i < map.size(); i++) {
      List<Integer> row = map.get(i);
      for (int j = 0; j < row.size(); j++) {
        int code = row.get(j);
        if (code == 39 || code == 40 || code == 0) {
          validPosition.add(new Position(j, i));
        }
      }
    }

    leftLimit = validPosition.stream().min(Comparator.comparing(Position::getX))
        .orElseThrow(NoSuchElementException::new).getX();
    rightLimit = validPosition.stream().max(Comparator.comparing(Position::getX))
        .orElseThrow(NoSuchElementException::new).getX();
    topLimit = validPosition.stream().min(Comparator.comparing(Position::getY))
        .orElseThrow(NoSuchElementException::new).getY();
    bottomLimit = validPosition.stream().max(Comparator.comparing(Position::getY))
        .orElseThrow(NoSuchElementException::new).getY();
  }
}
