/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.direction;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.model.GameModel;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.direction.ghostsdirectionmanagers.PeriodicGhostDirectionManager;
import ca.usherbrooke.pacman.model.exceptions.GameObjectCannotChangeDirectionException;
import ca.usherbrooke.pacman.model.objects.Ghost;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.objects.MockLevelFactory;
import ca.usherbrooke.pacman.model.objects.PacMan;
import ca.usherbrooke.pacman.model.position.Position;

public class PeriodicGhostDirectionManagerTest {

  private IGameModel model;
  private Ghost ghost;
  private PacMan pacman;

  @Before
  public void setUp() {
    ghost = new Ghost();
    ghost.setPosition(new Position(0, 0));
    ghost.setDesiredDirection(Direction.DOWN);
    pacman = new PacMan();
    pacman.setPosition(new Position(1, 1));
    Level level = MockLevelFactory.getMockLevelThreeByThreeEmpty();
    level.setPacMan(pacman);

    model = new GameModel();
    model.setCurrentLevel(level);
    ghost.setDesiredDirection(Direction.DOWN);
  }

  @Test
  public void updateDesiredDirectionWhenPeriodsReached()
      throws GameObjectCannotChangeDirectionException {
    IDirectionGenerator mockDirectionGenerator = mock(RandomDirectionGenerator.class);
    when(mockDirectionGenerator.get()).thenReturn(Direction.UP).thenReturn(Direction.RIGHT);
    PeriodicGhostDirectionManager directionManager =
        new PeriodicGhostDirectionManager(model, mockDirectionGenerator, ghost, 2);

    directionManager.update();
    directionManager.update();
    assertEquals(Direction.UP, ghost.getDesiredDirection());

    directionManager.update();
    directionManager.update();
    assertEquals(Direction.RIGHT, ghost.getDesiredDirection());
  }

  @Test
  public void whenAfraidAndPacmanInLineOfSightThenMoveInOppositeDirection() {
    pacman.setIsInvincible(true);
    PeriodicGhostDirectionManager directionManager =
        new PeriodicGhostDirectionManager(model, null, ghost, 1);

    ghost.setPosition(new Position(1, 2));
    directionManager.update();
    assertEquals(Direction.DOWN, ghost.getDesiredDirection());

    ghost.setPosition(new Position(1, 0));
    directionManager.update();
    assertEquals(Direction.UP, ghost.getDesiredDirection());

    ghost.setPosition(new Position(2, 1));
    directionManager.update();
    assertEquals(Direction.RIGHT, ghost.getDesiredDirection());

    ghost.setPosition(new Position(0, 1));
    directionManager.update();
    assertEquals(Direction.LEFT, ghost.getDesiredDirection());
  }

  @Test
  public void whenNotAfraidAndPacmanInLineOfSightThenFollowDirectionGenerator() {
    IDirectionGenerator mockDirectionGenerator = mock(RandomDirectionGenerator.class);
    when(mockDirectionGenerator.get()).thenReturn(Direction.UP).thenReturn(Direction.RIGHT)
        .thenReturn(Direction.DOWN).thenReturn(Direction.LEFT);
    pacman.setIsInvincible(false);
    PeriodicGhostDirectionManager directionManager =
        new PeriodicGhostDirectionManager(model, mockDirectionGenerator, ghost, 1);

    directionManager.update();
    assertEquals(Direction.UP, ghost.getDesiredDirection());

    directionManager.update();
    assertEquals(Direction.RIGHT, ghost.getDesiredDirection());

    directionManager.update();
    assertEquals(Direction.DOWN, ghost.getDesiredDirection());

    directionManager.update();
    assertEquals(Direction.LEFT, ghost.getDesiredDirection());
  }

  @Test
  public void whenAfraidAndPacmanNotInLineOfSightThenFollowDirectionGenerator() {
    IDirectionGenerator mockDirectionGenerator = mock(RandomDirectionGenerator.class);
    when(mockDirectionGenerator.get()).thenReturn(Direction.UP).thenReturn(Direction.RIGHT)
        .thenReturn(Direction.DOWN).thenReturn(Direction.LEFT);
    ghost.setPosition(new Position(0, 0));
    pacman.setIsInvincible(true);
    PeriodicGhostDirectionManager directionManager =
        new PeriodicGhostDirectionManager(model, mockDirectionGenerator, ghost, 1);

    directionManager.update();
    assertEquals(Direction.UP, ghost.getDesiredDirection());

    directionManager.update();
    assertEquals(Direction.RIGHT, ghost.getDesiredDirection());

    directionManager.update();
    assertEquals(Direction.DOWN, ghost.getDesiredDirection());

    directionManager.update();
    assertEquals(Direction.LEFT, ghost.getDesiredDirection());
  }

  @Test
  public void whenOverrideCommandReturnNotNullDirection() {
    IDirectionGenerator mockDirectionGenerator = mock(RandomDirectionGenerator.class);
    when(mockDirectionGenerator.get()).thenReturn(null);
    when(mockDirectionGenerator.getOverridenDirection()).thenReturn(Direction.DOWN);
    ghost.setPosition(new Position(0, 0));

    PeriodicGhostDirectionManager directionManager =
        new PeriodicGhostDirectionManager(model, mockDirectionGenerator, ghost, 1);

    directionManager.update();
    assertEquals(Direction.DOWN, ghost.getDesiredDirection());
  }

  @Test
  public void whenOverrideCommandReturnNullDirection() {
    IDirectionGenerator mockDirectionGenerator = mock(RandomDirectionGenerator.class);
    when(mockDirectionGenerator.get()).thenReturn(Direction.UP).thenReturn(Direction.RIGHT)
        .thenReturn(Direction.DOWN).thenReturn(Direction.LEFT);
    ghost.setPosition(new Position(0, 0));

    PeriodicGhostDirectionManager directionManager =
        new PeriodicGhostDirectionManager(model, mockDirectionGenerator, ghost, 1);

    directionManager.update();
    assertEquals(Direction.UP, ghost.getDesiredDirection());

    directionManager.update();
    assertEquals(Direction.RIGHT, ghost.getDesiredDirection());

    directionManager.update();
    assertEquals(Direction.DOWN, ghost.getDesiredDirection());

    directionManager.update();
    assertEquals(Direction.LEFT, ghost.getDesiredDirection());
  }

  @Test
  public void whenInGhostRoomUseOverrideDirectionToEscapeThenFollowDirectionGenerator() {
    // Change the level
    Level ghostRoomLevel = MockLevelFactory.getMockLevelSingleGhostRoomSurroundedByGhostGates();
    ghostRoomLevel.setPacMan(pacman);
    model.setCurrentLevel(ghostRoomLevel);

    IDirectionGenerator mockDirectionGenerator = mock(RandomDirectionGenerator.class);
    when(mockDirectionGenerator.get()).thenReturn(Direction.UP).thenReturn(Direction.RIGHT)
        .thenReturn(Direction.DOWN).thenReturn(Direction.LEFT);
    PeriodicGhostDirectionManager directionManager =
        new PeriodicGhostDirectionManager(model, mockDirectionGenerator, ghost, 2);

    ghost.setPosition(new Position(1, 1));
    directionManager.update();
    assertEquals(Direction.UP, ghost.getDesiredDirection());

    directionManager.update();
    assertEquals(Direction.RIGHT, ghost.getDesiredDirection());

    directionManager.update();
    assertEquals(Direction.DOWN, ghost.getDesiredDirection());

    directionManager.update();
    assertEquals(Direction.LEFT, ghost.getDesiredDirection());

    // Revert to the old level.
    Level emptyLevel = MockLevelFactory.getMockLevelThreeByThreeEmpty();
    emptyLevel.setPacMan(pacman);
    model.setCurrentLevel(emptyLevel);
  }
}
