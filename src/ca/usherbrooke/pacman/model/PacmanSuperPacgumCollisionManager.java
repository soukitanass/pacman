package ca.usherbrooke.pacman.model;

public class PacmanSuperPacgumCollisionManager {
  private final PacMan pacman;
  private final Level level;
  private static final int POINT = 50;

  public PacmanSuperPacgumCollisionManager(PacMan pacman, Level level) {
    this.level = level;
    this.pacman = pacman;
  }

  public void update() {
    Position position = pacman.getPosition();
    level.setScore(level.getScore() + POINT);
    level.setEmptyMapTile(position);
  }
}
