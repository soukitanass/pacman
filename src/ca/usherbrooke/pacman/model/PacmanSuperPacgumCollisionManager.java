package ca.usherbrooke.pacman.model;

public class PacmanSuperPacgumCollisionManager {
  private final Level level;
  private static final int POINT = 50;

  public PacmanSuperPacgumCollisionManager(Level level) {
    this.level = level;
  }

  public void update() {
    Position position = level.getPacMan().getPosition();
    level.setScore(level.getScore() + POINT);
    level.setEmptyMapTile(position);
  }
}
