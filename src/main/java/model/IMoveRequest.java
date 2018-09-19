package model;

import model.Direction;

public interface IMoveRequest {
  Position getPosition();

  Direction getDirection();
}
