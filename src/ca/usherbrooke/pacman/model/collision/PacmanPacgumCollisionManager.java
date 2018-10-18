/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.collision;

import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.position.Position;

public class PacmanPacgumCollisionManager {

  private final Level level;
  private static final Integer POINT = 10;
  private IGameModel model;

  public PacmanPacgumCollisionManager(Level level,IGameModel model) {
    this.level = level;
    this.model = model;
  }


  public void update() {
    Position position = level.getPacMan().getPosition();
    model.setScore(model.getScore() + POINT);
    level.setEmptyMapTile(position);
  }
}
