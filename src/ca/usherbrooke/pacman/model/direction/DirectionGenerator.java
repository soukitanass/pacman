package ca.usherbrooke.pacman.model.direction;

import ca.usherbrooke.pacman.model.objects.Ghost;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.objects.PacMan;

public abstract class DirectionGenerator implements IDirectionGenerator {

  protected Ghost ghost;
  protected PacMan pacman;
  protected Level level;

  protected Direction getPacmanDirectionIfInLineOfSight() {
    return level.getDirectionIfInLineOfSight(ghost.getPosition(), pacman.getPosition());
  }

}
