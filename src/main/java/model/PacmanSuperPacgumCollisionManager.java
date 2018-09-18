package model;

public class PacmanSuperPacgumCollisionManager {
  private final PacMan pacman;
  private final Level level;
  private static final int POINT = 100;

  public PacmanSuperPacgumCollisionManager(PacMan pacman, Level level) {
    this.pacman = pacman;
    this.level = level;
  }

  public void update() {
    Position position = pacman.getPosition();
    if (!level.isSuperPacgum(position)) {
      return;
    }
    level.setScore(level.getScore() + POINT);
    level.setEmptyMapTile(position);
  }
}
