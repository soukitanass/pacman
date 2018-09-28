package ca.usherbrooke.pacman.model;

public class PacmanGhostCollisionManager {
  private final PacMan pacman;
  private final Level level;

  public PacmanGhostCollisionManager(PacMan pacman, Level level) {
    this.pacman = pacman;
    this.level = level;
  }

  public void update() {
    Position position = pacman.getPosition();
    if (!level.isGhost(position)) {
      return;
    }
    level.setLives(level.getLives() - 1);
  }
}
