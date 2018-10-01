package ca.usherbrooke.pacman.model;

public class PacmanPacgumCollisionManager {

  private final Level level;
  private static final Integer POINT = 10;

  public PacmanPacgumCollisionManager(Level level) {
    this.level = level;
  }

  public void update() {
    Position position = level.getPacMan().getPosition();
    level.setScore(level.getScore() + POINT);
    level.setEmptyMapTile(position);
  }
}
