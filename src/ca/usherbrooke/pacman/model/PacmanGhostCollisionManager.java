package ca.usherbrooke.pacman.model;

public class PacmanGhostCollisionManager {

  private final PacMan pacman;
  private final Ghost ghost;
  
  public PacmanGhostCollisionManager(PacMan pacman, Ghost ghost) {
    this.pacman = pacman;
    this.ghost = ghost;    
  }

  public boolean isPacmanEated() {
    return false;
    /*
    Position position = pacman.getPosition();
    if (!level.isPacgum(position)) {
      return false;
    }
    level.setScore(level.getScore());
    level.setEmptyMapTile(position);
    return true;
      */
  }
}