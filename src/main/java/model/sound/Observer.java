package model.sound;

import model.IGameModel;

public abstract class Observer {
  protected IGameModel subject;

  public abstract void update(Sound sound);
}
