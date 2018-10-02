package ca.usherbrooke.pacman.threads;

import ca.usherbrooke.pacman.model.Position;
import java.util.ArrayList;
import java.util.List;

public class ThreadBlackboard {
  private List<Position> listValues = new ArrayList<>();
  // private final Object registerLock = new Object();

  public ThreadBlackboard() {}

  public synchronized void create(Position value) {
    listValues.add(value);
  }

  public synchronized void edit(int valueId, Position value) {
    if (valueId < listValues.size()) {
      listValues.set(valueId, value);
    }
  }

  public synchronized void delete(int valueId) {
    if (valueId < listValues.size()) {
      listValues.remove(valueId);
    }
  }
}

