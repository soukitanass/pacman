package ca.usherbrooke.pacman.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;
import ca.usherbrooke.pacman.model.Direction;
import ca.usherbrooke.pacman.model.IDirectionGenerator;
import ca.usherbrooke.pacman.model.IHasDesiredDirection;
import ca.usherbrooke.pacman.model.PacMan;
import ca.usherbrooke.pacman.model.random.RandomDirectionGenerator;

public class PeriodicRandomDirectionControllerTest {

  @Test
  public void updateDesiredDirectionWhenPeriodsReached() {
    IDirectionGenerator mockDirectionGenerator = mock(RandomDirectionGenerator.class);
    IHasDesiredDirection pacman = new PacMan();
    pacman.setDesiredDirection(Direction.DOWN);
    PeriodicDirectionController controller =
        new PeriodicDirectionController(mockDirectionGenerator, pacman, 2);
    when(mockDirectionGenerator.get()).thenReturn(Direction.UP).thenReturn(Direction.RIGHT);
    assertEquals(Direction.DOWN, pacman.getDesiredDirection());

    controller.update();
    assertEquals(Direction.DOWN, pacman.getDesiredDirection());
    controller.update();
    assertEquals(Direction.UP, pacman.getDesiredDirection());

    controller.update();
    assertEquals(Direction.UP, pacman.getDesiredDirection());
    controller.update();
    assertEquals(Direction.RIGHT, pacman.getDesiredDirection());
  }

}
