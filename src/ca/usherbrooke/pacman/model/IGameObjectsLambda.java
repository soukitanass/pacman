package ca.usherbrooke.pacman.model;

import java.util.List;

@FunctionalInterface
public interface IGameObjectsLambda {
  List<IHasDesiredDirection> getHasDesiredDirection();
}
