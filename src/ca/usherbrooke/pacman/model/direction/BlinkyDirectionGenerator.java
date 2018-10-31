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

public class BlinkyDirectionGenerator implements IDirectionGenerator {
  private IDirectionGenerator randomDirectionGenerator;
  private boolean isFollowingPacman = false;
  private Direction pacmanDesiredDirection;
  private Position pacmanLastSeenPosition;
  private PacMan pacman;
  private Ghost ghost;
  private Level level;

  public BlinkyDirectionGenerator(IDirectionGenerator randomDirectionGenerator, Ghost ghost,
      Level level) {
    this.randomDirectionGenerator = randomDirectionGenerator;
    this.pacman = level.getPacMan();
    this.level = level;
    this.ghost = ghost;
  }

  @Override
  public Direction get() {
    Direction direction = getPacmanDirectionIfInLineOfSight();

    if (direction == null) {
      direction = randomDirectionGenerator.get();
    }
    return direction;
  }

  @Override
  public Direction getOverridenDirection() {
    Direction direction = getPacmanDirectionIfInLineOfSight();

    if (direction != null) {
      pacmanDesiredDirection = pacman.getDesiredDirection();
      pacmanLastSeenPosition = pacman.getPosition();
      isFollowingPacman = true;
    }

    if (isFollowingPacman && direction == null) {
      Position ghostPosition = ghost.getPosition();
      if (ghostPosition.equals(pacmanLastSeenPosition)) {
        direction = pacmanDesiredDirection;
        isFollowingPacman = false;
      } else {
        direction = level.getDirectionIfInLineOfSight(ghost.getPosition(), pacmanLastSeenPosition);
      }
    }
    
    return direction;
  }

  private Direction getPacmanDirectionIfInLineOfSight() {
    return level.getDirectionIfInLineOfSight(ghost.getPosition(), pacman.getPosition());
  }
}
