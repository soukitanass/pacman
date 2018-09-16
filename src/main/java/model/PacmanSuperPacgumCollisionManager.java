package model;

public class PacmanSuperPacgumCollisionManager {
  private final PacMan pacman;
  private final Level level;

  public PacmanSuperPacgumCollisionManager(PacMan pacman, Level level) {
    this.pacman = pacman;
    this.level = level;
  }

  public void update() {
    Position position = pacman.getPosition();
    if (!level.isSuperPacgum(position)) {
      return;
    }
    level.setEmptyMapTile(position);
  }
}
