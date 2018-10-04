package ca.usherbrooke.pacman.model;

import java.util.ArrayList;
import java.util.List;

public class PacmanGhostCollisionManager {
  private final PacMan pacman;
  private final Level level;
  private final Level initialLevel;
  private List<Position> listPositions;
  private Position pacmanInitialPosition;

  public PacmanGhostCollisionManager(Level level, Level initialLevel) {
    listPositions = new ArrayList<>();
    this.pacman = level.getPacMan();
    this.level = level;
    this.initialLevel = initialLevel;
    loadGhostInitialPosition();
    loadPacmanInitialPosition();
  }

  public void update() {
    level.setLives(level.getLives() - 1);
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
      if (pacman.getPosition().equals(ghost.getPosition())) {
        return true;
      } else if (isOppositeDirection(pacman, ghost) && isSideBySide(pacman, ghost)) {
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
    if (gameObject1.getDirection() == Direction.LEFT
        && gameObject2.getDirection() == Direction.RIGHT
        || gameObject2.getDirection() == Direction.LEFT
            && gameObject1.getDirection() == Direction.RIGHT) {
      return true;
    } else if (gameObject1.getDirection() == Direction.UP
        && gameObject2.getDirection() == Direction.DOWN
        || gameObject2.getDirection() == Direction.UP
            && gameObject1.getDirection() == Direction.DOWN) {
      return true;
    }
    return false;
  }

  private boolean isSideBySide(IGameObject gameObject1, IGameObject gameObject2) {
    Position gameObject1Position = gameObject1.getPosition();
    Position gameObject2Position = gameObject2.getPosition();

    if (gameObject1Position.getX() + 1 == gameObject2Position.getX()
        && isOntheSameColumn(gameObject1, gameObject2)) {
      return true;
    } else if (gameObject1Position.getX() - 1 == gameObject2Position.getX()
        && isOntheSameColumn(gameObject1, gameObject2)) {
      return true;
    } else if (gameObject1Position.getY() + 1 == gameObject2Position.getY()
        && isOnTheSameRow(gameObject1, gameObject2)) {
      return true;
    } else if (gameObject1Position.getY() - 1 == gameObject2Position.getY()
        && isOnTheSameRow(gameObject1, gameObject2)) {
      return true;
    }
    return false;
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
