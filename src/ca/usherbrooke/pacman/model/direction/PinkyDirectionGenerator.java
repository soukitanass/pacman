/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.direction;

import ca.usherbrooke.pacman.model.objects.Ghost;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.objects.PacMan;
import ca.usherbrooke.pacman.model.position.Position;

public class PinkyDirectionGenerator implements IDirectionGenerator {
  private IDirectionGenerator randomDirectionGenerator;
  private Direction directionToPacman;
  private PacMan pacman;
  private Ghost ghost;
  private Level level;

  public PinkyDirectionGenerator(IDirectionGenerator randomDirectionGenerator, Ghost ghost,
      Level level) {
    this.randomDirectionGenerator = randomDirectionGenerator;
    this.pacman = level.getPacMan();
    this.level = level;
    this.ghost = ghost;
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
      System.out.println("Direction Lock: " + directionToPacman);
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
        if (isDownPositionValid(ghostPosition)) {
          System.out.println("Down");
          return Direction.DOWN;
        } else if (isUpPositionValid(ghostPosition)) {
          direction = Direction.UP;
          System.out.println("Up");
        }
      } else if ((directionToPacman == Direction.UP && !isUpPositionValid(ghostPosition))
          || (directionToPacman == Direction.DOWN && !isDownPositionValid(ghostPosition))) {
        if (isRightPositionValid(ghostPosition)) {
          direction = Direction.RIGHT;
          System.out.println("Right");
        } else if (isLeftPositionValid(ghostPosition)) {
          direction = Direction.LEFT;
          System.out.println("Left");
        }
      } else {
        return directionToPacman;
      }
    }
    return direction;
  }

  private Direction getPacmanDirectionIfInLineOfSight() {
    return level.getDirectionIfInLineOfSight(ghost.getPosition(), pacman.getPosition(), true);
  }

  private boolean isLeftPositionValid(Position position) {
    return isPositionValid(new Position(position.getX() - 1, position.getY()));
  }

  private boolean isRightPositionValid(Position position) {
    return isPositionValid(new Position(position.getX() + 1, position.getY()));
  }

  private boolean isUpPositionValid(Position position) {
    return isPositionValid(new Position(position.getX(), position.getY() - 1));
  }

  private boolean isDownPositionValid(Position position) {
    return isPositionValid(new Position(position.getX(), position.getY() + 1));
  }

  private boolean isAtTheLimitOfTheMap(Position position, Direction direction) {
    return (position.getY() == 1 && direction == Direction.UP)
        || (position.getY() == level.getHeight() - 2 && direction == Direction.DOWN)
        || (position.getX() == 1 && direction == Direction.LEFT)
        || (position.getX() == level.getWidth() - 2 && direction == Direction.RIGHT);
  }

  private boolean isPositionValid(Position position) {
    return !level.isWall(position) && !level.isTunnel(position) && !level.isGhostGate(position);
  }
}
