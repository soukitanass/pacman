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
import ca.usherbrooke.pacman.model.objects.Ghost;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.objects.PacMan;
import ca.usherbrooke.pacman.model.position.Position;

public class PinkyDirectionGenerator implements IDirectionGenerator {
  private int leftLimit;
  private int rightLimit;
  private int bottomLimit;
  private int topLimit;

  private IDirectionGenerator randomDirectionGenerator;
  private Direction directionToPacman;
  private Direction lastDirection;
  private Ghost ghost;
  private Level level;

  public PinkyDirectionGenerator(IDirectionGenerator randomDirectionGenerator, Ghost ghost,
      Level level) {
    this.randomDirectionGenerator = randomDirectionGenerator;
    this.level = level;
    this.ghost = ghost;
    getLimitOfTheMap();
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
    if (directionToPacman != null) {
      direction = getDirectionToPacman();
    }
    return direction;
  }

  private Direction getDirectionToPacman() {
    Direction direction = null;
    if (directionToPacman != null) {
      final Position ghostPosition = ghost.getPosition();
      if (isAtTheLimitOfTheMap(ghostPosition, directionToPacman)
          || level.isGhostRoom(ghostPosition)) {
        directionToPacman = null;
      } else if ((directionToPacman == Direction.LEFT && !isLeftPositionValid(ghostPosition))
          || (directionToPacman == Direction.RIGHT && !isRightPositionValid(ghostPosition))) {
        if (isDirectionValid(lastDirection, ghostPosition)) {
          direction = lastDirection;
        } else if (isDownPositionValid(ghostPosition)) {
          direction = Direction.DOWN;
        } else if (isUpPositionValid(ghostPosition)) {
          direction = Direction.UP;
        }
      } else if ((directionToPacman == Direction.UP && !isUpPositionValid(ghostPosition))
          || (directionToPacman == Direction.DOWN && !isDownPositionValid(ghostPosition))) {
        if (isDirectionValid(lastDirection, ghostPosition)) {
          direction = lastDirection;
        } else if (isRightPositionValid(ghostPosition)) {
          direction = Direction.RIGHT;
        } else if (isLeftPositionValid(ghostPosition)) {
          direction = Direction.LEFT;
        }
      } else {
        direction = directionToPacman;
      }
    }
    return lastDirection = direction;
  }

  private Direction getPacmanDirectionIfInLineOfSight() {
    final PacMan pacman = level.getPacMan();
    return level.getDirectionIfInLineOfSight(ghost.getPosition(), pacman.getPosition(), true);
  }

  private boolean isDirectionValid(Direction direction, Position position) {
    return (direction == Direction.LEFT && isLeftPositionValid(position)
        || (direction == Direction.RIGHT && isRightPositionValid(position))
        || (direction == Direction.UP && isUpPositionValid(position)))
        || (direction == Direction.DOWN && isDownPositionValid(position));
  }

  private boolean isLeftPositionValid(Position position) {
    return position.getX() - 1 >= 0
        && isPositionValid(new Position(position.getX() - 1, position.getY()));
  }

  private boolean isRightPositionValid(Position position) {
    return position.getX() + 1 < level.getWidth()
        && isPositionValid(new Position(position.getX() + 1, position.getY()));
  }

  private boolean isUpPositionValid(Position position) {
    return position.getY() - 1 >= 0
        && isPositionValid(new Position(position.getX(), position.getY() - 1));
  }

  private boolean isDownPositionValid(Position position) {
    return position.getY() + 1 < level.getHeight()
        && isPositionValid(new Position(position.getX(), position.getY() + 1));
  }

  private boolean isAtTheLimitOfTheMap(Position position, Direction direction) {
    return (position.getY() == topLimit && direction == Direction.UP)
        || (position.getY() == bottomLimit && direction == Direction.DOWN)
        || (position.getX() == leftLimit && direction == Direction.LEFT)
        || (position.getX() == rightLimit && direction == Direction.RIGHT);
  }

  private boolean isPositionValid(Position position) {
    return !level.isWall(position) && !level.isTunnel(position) && !level.isGhostGate(position);
  }

  private void getLimitOfTheMap() {
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
