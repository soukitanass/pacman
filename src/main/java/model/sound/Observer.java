package model.sound;

import model.IGameModel;

public abstract class Observer {
  protected IGameModel subject;

  public abstract void consumingPacGums();

  public abstract void consumingGhost();

  public abstract void consumingFruit();

  public abstract void movingToEmptySpace();
}
