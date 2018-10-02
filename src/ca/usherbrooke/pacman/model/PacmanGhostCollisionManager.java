package ca.usherbrooke.pacman.model;

import java.util.ArrayList;
import java.util.List;

public clasws PacmanGhostCollisionManager {
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
