package model;

import model.exceptions.InvalidDirectionException;

public interface IMoveValidator {
  boolean isValid(IMoveRequest moveRequest) throws InvalidDirectionException;

  Position getTargetPosition(IMoveRequest moveRequest) throws InvalidDirectionException;
}
