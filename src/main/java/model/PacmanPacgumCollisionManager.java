package model;

public class PacmanPacgumCollisionManager {

  private final PacMan pacman;
  private final Level level;
  private static final int POINT = 10;

  public PacmanPacgumCollisionManager(PacMan pacman, Level level) {
    this.pacman = pacman;
    this.level = level;
  }

  public void update() {
    Position position = pacman.getPosition();
    if (!level.isPacgum(position)) {
      return;
    }
    level.setScore(level.getScore() + POINT);
    //System.out.println("Score = "+level.getScore());
    level.setEmptyMapTile(position);
  }
}
