/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model;

public class PacmanSuperPacgumCollisionManager {
  private final Level level;
  private static final int POINT = 50;
  private IGameModel model;

  public PacmanSuperPacgumCollisionManager(Level level,IGameModel model) {
    this.level = level;
    this.model = model;
  }

  public void update() {
    Position position = level.getPacMan().getPosition();
    model.setScore(model.getScore() + POINT);
    level.setEmptyMapTile(position);
  }
}
