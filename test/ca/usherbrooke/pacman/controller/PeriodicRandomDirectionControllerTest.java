package ca.usherbrooke.pacman.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import org.junit.Test;
import ca.usherbrooke.pacman.model.Direction;
import ca.usherbrooke.pacman.model.Ghost;
import ca.usherbrooke.pacman.model.IDirectionGenerator;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.Level;
import ca.usherbrooke.pacman.model.random.RandomDirectionGenerator;

public class PeriodicRandomDirectionControllerTest {

  @Test
  public void updateDesiredDirectionWhenPeriodsReached() {
    IDirectionGenerator mockDirectionGenerator = mock(RandomDirectionGenerator.class);
    Ghost ghost = new Ghost();
    ArrayList<Ghost> ghosts = new ArrayList<>();
    IGameModel mockGameModel = mock(IGameModel.class);
    Level level = mock(Level.class);

    ghost.setDesiredDirection(Direction.DOWN);
    ghosts.add(ghost);

    PeriodicDirectionController controller =
        new PeriodicDirectionController(mockGameModel, mockDirectionGenerator, 2);
    when(mockDirectionGenerator.get()).thenReturn(Direction.UP).thenReturn(Direction.RIGHT);
    when(level.getGhosts()).thenReturn(ghosts);
    when(mockGameModel.getCurrentLevel()).thenReturn(level);

    controller.update();
    controller.update();
    verify(mockGameModel, times(1)).setDirection(ghost, Direction.UP);

    controller.update();
    controller.update();
    verify(mockGameModel, times(1)).setDirection(ghost, Direction.RIGHT);
  }

}
