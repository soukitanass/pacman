/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.direction;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Test;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.direction.ghostsdirectionmanagers.ClydePeriodicDirectionManager;
import ca.usherbrooke.pacman.model.exceptions.GameObjectCannotChangeDirectionException;
import ca.usherbrooke.pacman.model.objects.Ghost;

public class ClydePeriodicDirectionManagerTest {

  @Test
  public void updateDesiredDirectionWhenPeriodsReached()
      throws GameObjectCannotChangeDirectionException {
    IDirectionGenerator mockDirectionGenerator = mock(RandomDirectionGenerator.class);

    Ghost ghost = new Ghost();
    ghost.setDesiredDirection(Direction.DOWN);

    IGameModel mockGameModel = mock(IGameModel.class);

    ClydePeriodicDirectionManager directionManager =
        new ClydePeriodicDirectionManager(mockGameModel, mockDirectionGenerator, ghost, 2);
    when(mockDirectionGenerator.get()).thenReturn(Direction.UP).thenReturn(Direction.RIGHT);

    directionManager.update();
    directionManager.update();
    verify(mockGameModel, times(1)).setDirection(ghost, Direction.UP);

    directionManager.update();
    directionManager.update();
    verify(mockGameModel, times(1)).setDirection(ghost, Direction.RIGHT);
  }

}
