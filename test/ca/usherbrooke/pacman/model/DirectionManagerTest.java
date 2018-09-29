package ca.usherbrooke.pacman.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Test;
import ca.usherbrooke.pacman.model.exceptions.GameObjectCannotChangeDirectionException;
import ca.usherbrooke.pacman.model.random.RandomDirectionGenerator;

public class DirectionManagerTest {

  @Test
  public void updateDesiredDirectionWhenPeriodsReached()
      throws GameObjectCannotChangeDirectionException {
    IDirectionGenerator mockDirectionGenerator = mock(RandomDirectionGenerator.class);

    Ghost ghost = new Ghost();
    ghost.setDesiredDirection(Direction.DOWN);

    IGameModel mockGameModel = mock(IGameModel.class);

    PeriodicDirectionManager directionManager =
        new PeriodicDirectionManager(mockGameModel, mockDirectionGenerator, ghost, 2);
    when(mockDirectionGenerator.get()).thenReturn(Direction.UP).thenReturn(Direction.RIGHT);

    directionManager.update();
    directionManager.update();
    verify(mockGameModel, times(1)).setDirection(ghost, Direction.UP);

    directionManager.update();
    directionManager.update();
    verify(mockGameModel, times(1)).setDirection(ghost, Direction.RIGHT);
  }

}
