package ca.usherbrooke.pacman.model;

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
