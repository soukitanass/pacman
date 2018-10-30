package ca.usherbrooke.pacman.model.direction;

import ca.usherbrooke.pacman.game.TimeGetter;
import ca.usherbrooke.pacman.model.objects.Ghost;
import ca.usherbrooke.pacman.model.objects.Level;

public class InkyDirectionGenerator extends DirectionGenerator {

  private static final int BEHAVIOR_INTERVAL = 2000;

  private TimeGetter timeGetter;
  private IDirectionGenerator randomDirectionGenerator;
  private long lastUpdateTime;
  private int updateCounter = 0;

  public InkyDirectionGenerator(IDirectionGenerator randomDirectionGenerator, Ghost ghost,
      Level level, TimeGetter timeGetter) {
    this.randomDirectionGenerator = randomDirectionGenerator;
    this.pacman = level.getPacMan();
    this.level = level;
    this.ghost = ghost;
    this.timeGetter = timeGetter;
    this.lastUpdateTime = timeGetter.getMilliseconds();
  }

  @Override
  public Direction get() {
    lastUpdateTime = timeGetter.getMilliseconds();
    return randomDirectionGenerator.get();
  }

  @Override
  public Direction getOverridenDirection() {
    Direction direction = getPacmanDirectionIfInLineOfSight();

    if (direction == null) {
      return null;
    }

    long currentTime = timeGetter.getMilliseconds();

    if (currentTime - lastUpdateTime >= BEHAVIOR_INTERVAL) {
      lastUpdateTime = timeGetter.getMilliseconds();
      updateCounter++;
    }

    if (updateCounter % 2 == 0) {
      return getOppositeDirection(direction);
    } else {
      return direction;
    }
  }

  private Direction getOppositeDirection(Direction direction) {
    switch (direction) {
      case LEFT:
        return Direction.RIGHT;
      case RIGHT:
        return Direction.LEFT;
      case UP:
        return Direction.DOWN;
      case DOWN:
        return Direction.UP;
      default:
        return null;
    }
  }

}
