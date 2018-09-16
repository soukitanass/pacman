package model;

public class PacmanPacgumCollisionManager {

  private final PacMan pacman;
  private final Level level;

  public PacmanPacgumCollisionManager(PacMan pacman, Level level) {
    this.pacman = pacman;
    this.level = level;
  }

  public void update() {
    Position position = pacman.getPosition();
    if (!level.isPacgum(position)) {
      return;
    }
    level.setEmptyMapTile(position);
  }
}
