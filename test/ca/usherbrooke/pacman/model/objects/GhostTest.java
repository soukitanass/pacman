package ca.usherbrooke.pacman.model.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.position.Position;

public class GhostTest {

  @Test
  public void hash() {
    Ghost ghost = new Ghost();
    ghost.setDesiredDirection(Direction.DOWN);
    ghost.setDirection(Direction.UP);
    ghost.setPosition(new Position(0, 0));
    Ghost ghostSame = new Ghost();
    ghostSame.setDesiredDirection(Direction.DOWN);
    ghostSame.setDirection(Direction.UP);
    ghostSame.setPosition(new Position(0, 0));
    Ghost ghostDifferent = new Ghost();
    ghostDifferent.setDesiredDirection(Direction.LEFT);
    ghostDifferent.setDirection(Direction.RIGHT);
    ghostDifferent.setPosition(new Position(1, 0));

    assertEquals(ghost.hashCode(), ghostSame.hashCode());
    assertNotEquals(ghost.hashCode(), ghostDifferent.hashCode());
  }

}
