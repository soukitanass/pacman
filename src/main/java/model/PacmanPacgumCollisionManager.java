package model;

public class PacmanPacgumCollisionManager {

  private final PacMan pacman;
  private final Level level;
  private static final Integer POINT = 10;

  public PacmanPacgumCollisionManager(PacMan pacman, Level level) {
    this.pacman = pacman;
    this.level = level;
  }

  public boolean isPacgumConsumed() {
    Position position = pacman.getPosition();
    if (!level.isPacgum(position)) {
      return false;
    }
    level.setScore(level.getScore() + POINT);
    level.setEmptyMapTile(position);
    return true;
  }
}
