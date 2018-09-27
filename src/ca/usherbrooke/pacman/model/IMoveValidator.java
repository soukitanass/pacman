package ca.usherbrooke.pacman.model;

import ca.usherbrooke.pacman.model.exceptions.InvalidDirectionException;

public interface IMoveValidator {
  boolean isValid(IMoveRequest moveRequest) throws InvalidDirectionException;

  boolean isDesiredDirectionValid(IMoveRequest moveRequest) throws InvalidDirectionException;

  Position getTargetPosition(IMoveRequest moveRequest) throws InvalidDirectionException;
}
