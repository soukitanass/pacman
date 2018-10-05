/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model;

import java.util.ArrayList;
import java.util.List;

public class PacmanGhostCollisionManager {
  private final PacMan pacman;
  private final Level level;
  private final IGameModel model;
  private final Level initialLevel;
  private List<Position> listPositions;
  private Position pacmanInitialPosition;

  public PacmanGhostCollisionManager(Level level, Level initialLevel, IGameModel model) {
    listPositions = new ArrayList<>();
    this.model = model;
    this.pacman = level.getPacMan();
    this.level = level;
    this.initialLevel = initialLevel;
    loadGhostInitialPosition();
    loadPacmanInitialPosition();
  }

  public void update() {
    model.setLives(model.getLives() - 1);
    pacman.setPosition(pacmanInitialPosition);
    pacman.setDirection(Direction.LEFT);
    int i = 0;
    for (Ghost ghost : level.getGhosts()) {
      ghost.setPosition(listPositions.get(i));
      i++;
    }
  }

  public boolean isCollision() {
    for (Ghost ghost : level.getGhosts()) {
      if (pacman.getPosition().equals(ghost.getPosition())
          || isOppositeDirection(pacman, ghost) && isSideBySide(pacman, ghost)) {
        return true;
      }
    }
    return false;
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

  private void loadPacmanInitialPosition() {
    this.pacmanInitialPosition = initialLevel.getPacMan().getPosition();
  }

  private List<Position> loadGhostInitialPosition() {
    for (Ghost ghost : initialLevel.getGhosts()) {
      listPositions.add(ghost.getPosition());
    }
    return listPositions;
  }
}
