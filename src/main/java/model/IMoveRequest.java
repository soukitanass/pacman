package model;

import view.Direction;

public interface IMoveRequest {
  Position getPosition();

  Direction getDirection();
}
