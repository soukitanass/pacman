/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.collision;

import ca.usherbrooke.pacman.game.ITimeGetter;
import ca.usherbrooke.pacman.game.TimeGetter;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.objects.PacMan;
import ca.usherbrooke.pacman.model.position.Position;

public class PacmanSuperPacgumCollisionManager {
  private final Level level;
  private static final int POINT = 50;
  private static final int INVINCIBLE_TIME = 8000; // ms
  private IGameModel model;
  private PacMan pacman;
  private ITimeGetter timeGetter = new TimeGetter();
  private long initialInvincibleTime;

  public PacmanSuperPacgumCollisionManager(Level level, IGameModel model) {
    this.level = level;
    this.model = model;
    this.pacman = model.getPacman();
  }

  public void setTimeGetter(ITimeGetter timeGetter) {
    this.timeGetter = timeGetter;
  }

  public void update() {
    Position position = level.getPacMan().getPosition();
    model.setScore(model.getScore() + POINT);
    level.setEmptyMapTile(position);
    pacman.setIsInvincible(true);
    initialInvincibleTime = timeGetter.getMilliseconds();
  }

  public void updateIsPacManInvincible() {
    if (!pacman.isInvincible()) {
      return;
    }

    if (timeGetter.getMilliseconds() - initialInvincibleTime >= INVINCIBLE_TIME) {
      pacman.setIsInvincible(false);
    }
  }
}
