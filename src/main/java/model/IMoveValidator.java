package model;

public interface IMoveValidator {
  boolean isValid(IMoveRequest moveRequest);

  Position getTargetPosition(IMoveRequest moveRequest);
}
