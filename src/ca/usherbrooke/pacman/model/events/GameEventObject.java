/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.events;

import ca.usherbrooke.pacman.model.direction.IGameObject;
import ca.usherbrooke.pacman.model.direction.Position;

public class GameEventObject {

  private final IGameObject gameObject;
  private final GameEvent gameEvent;
  private final Position position;

  public GameEventObject(IGameObject gameObject, GameEvent gameEvent, Position position) {
    this.gameObject = gameObject;
    this.gameEvent = gameEvent;
    this.position = position;
  }

  public IGameObject getGameObject() {
    return gameObject;
  }

  public GameEvent getGameEvent() {
    return gameEvent;
  }

  public Position getPosition() {
    return position;
  }
}
