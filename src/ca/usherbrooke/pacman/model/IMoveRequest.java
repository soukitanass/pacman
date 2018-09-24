package ca.usherbrooke.pacman.model;

public interface IMoveRequest {
  Position getPosition();

  Direction getDirection();
}
