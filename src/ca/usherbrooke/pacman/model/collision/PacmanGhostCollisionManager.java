/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.collision;

import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.objects.Ghost;
import ca.usherbrooke.pacman.model.objects.IGameObject;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.objects.PacMan;
import ca.usherbrooke.pacman.model.position.Position;

public class PacmanGhostCollisionManager {
  private final PacMan pacman;
  private Level level;
  private final IGameModel model;

  public PacmanGhostCollisionManager(Level level, IGameModel model) {
    this.model = model;
    this.pacman = level.getPacMan();
    setLevel(level);
  }

  public void update() {
    if (pacman.isInvincible()) {
      model.updateGhostDeath(getCollidingGhost());
    } else {
      model.updatePacmanDeath();
    }
  }

  public Ghost getCollidingGhost() {
    for (Ghost ghost : level.getGhosts()) {
      if (pacman.getPosition().equals(ghost.getPosition())
          || isOppositeDirection(pacman, ghost) && isSideBySide(pacman, ghost)) {
        return ghost;
      }
    }
    return null;
  }

  private boolean isOnTheSameRow(IGameObject gameObject1, IGameObject gameObject2) {
    return gameObject1.getPosition().getX() == gameObject2.getPosition().getX();
  }

  private boolean isOntheSameColumn(IGameObject gameObject1, IGameObject gameObject2) {
    return gameObject1.getPosition().getY() == gameObject2.getPosition().getY();
  }

  private boolean isOppositeDirection(IGameObject gameObject1, IGameObject gameObject2) {
    return gameObject1.getDirection() == Direction.LEFT
        && gameObject2.getDirection() == Direction.RIGHT
        || gameObject2.getDirection() == Direction.LEFT
            && gameObject1.getDirection() == Direction.RIGHT
        || gameObject1.getDirection() == Direction.UP
            && gameObject2.getDirection() == Direction.DOWN
        || gameObject2.getDirection() == Direction.UP
            && gameObject1.getDirection() == Direction.DOWN;
  }

  private boolean isSideBySide(IGameObject gameObject1, IGameObject gameObject2) {
    Position gameObject1Position = gameObject1.getPosition();
    Position gameObject2Position = gameObject2.getPosition();

    return gameObject1Position.getX() + 1 == gameObject2Position.getX()
        && isOntheSameColumn(gameObject1, gameObject2)
        || gameObject1Position.getX() - 1 == gameObject2Position.getX()
            && isOntheSameColumn(gameObject1, gameObject2)
        || gameObject1Position.getY() + 1 == gameObject2Position.getY()
            && isOnTheSameRow(gameObject1, gameObject2)
        || gameObject1Position.getY() - 1 == gameObject2Position.getY()
            && isOnTheSameRow(gameObject1, gameObject2);
  }

  public void setLevel(Level level) {
    this.level = level;
  }
}
