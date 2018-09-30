package ca.usherbrooke.pacman.model;

public class PacmanGhostCollisionManager {
  private final PacMan pacman;
  private final Level level;

  public PacmanGhostCollisionManager(PacMan pacman, Level level) {
    this.pacman = pacman;
    this.level = level;
  }

  public void update() {
    level.setLives(level.getLives() - 1);
    pacman.setPosition(new Position(13, 23));
    pacman.setDirection(Direction.RIGHT);
    for (Ghost ghost : level.getGhosts()) {
      ghost.setPosition(ghost.getPosition());
    }
  }
}
