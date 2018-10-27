package ca.usherbrooke.pacman.model.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.position.Position;

public class PacmanTest {

  @Test
  public void hash() {
    PacMan pacman = new PacMan();
    pacman.setDesiredDirection(Direction.DOWN);
    pacman.setDirection(Direction.UP);
    pacman.setIsInvincible(true);
    pacman.setPosition(new Position(0, 0));
    PacMan pacmanSame = new PacMan();
    pacmanSame.setDesiredDirection(Direction.DOWN);
    pacmanSame.setDirection(Direction.UP);
    pacmanSame.setIsInvincible(true);
    pacmanSame.setPosition(new Position(0, 0));
    PacMan pacmanDifferent = new PacMan();
    pacmanDifferent.setDesiredDirection(Direction.LEFT);
    pacmanDifferent.setDirection(Direction.RIGHT);
    pacmanDifferent.setIsInvincible(false);
    pacmanDifferent.setPosition(new Position(1, 0));

    assertEquals(pacman.hashCode(), pacmanSame.hashCode());
    assertNotEquals(pacman.hashCode(), pacmanDifferent.hashCode());
  }

}
