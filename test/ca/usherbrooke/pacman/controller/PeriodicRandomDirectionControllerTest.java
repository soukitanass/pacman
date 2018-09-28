package ca.usherbrooke.pacman.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Test;
import ca.usherbrooke.pacman.model.Direction;
import ca.usherbrooke.pacman.model.IDirectionGenerator;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.IHasDesiredDirection;
import ca.usherbrooke.pacman.model.PacMan;
import ca.usherbrooke.pacman.model.random.RandomDirectionGenerator;

public class PeriodicRandomDirectionControllerTest {

  @Test
  public void updateDesiredDirectionWhenPeriodsReached() {
    IDirectionGenerator mockDirectionGenerator = mock(RandomDirectionGenerator.class);
    IHasDesiredDirection pacman = new PacMan();
    pacman.setDesiredDirection(Direction.DOWN);
    IGameModel mockGameModel = mock(IGameModel.class);

    PeriodicDirectionController controller =
        new PeriodicDirectionController(mockGameModel, mockDirectionGenerator, pacman, 2);
    when(mockDirectionGenerator.get()).thenReturn(Direction.UP).thenReturn(Direction.RIGHT);

    controller.update();
    controller.update();
    verify(mockGameModel, times(1)).setDirection(pacman, Direction.UP);

    controller.update();
    controller.update();
    verify(mockGameModel, times(1)).setDirection(pacman, Direction.RIGHT);
  }

}
