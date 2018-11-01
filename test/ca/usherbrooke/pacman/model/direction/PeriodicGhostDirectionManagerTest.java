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
import java.util.Arrays;
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
  private IDirectionGenerator mockBackupDirectionGenerator;

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
    mockBackupDirectionGenerator = mock(RandomDirectionGenerator.class);
  }

  @Test
  public void updateDesiredDirectionWhenPeriodsReached()
      throws GameObjectCannotChangeDirectionException {
    IDirectionGenerator mockDirectionGenerator = mock(RandomDirectionGenerator.class);
    when(mockDirectionGenerator.get()).thenReturn(Direction.UP).thenReturn(Direction.RIGHT)
        .thenReturn(Direction.LEFT).thenReturn(Direction.DOWN);
    PeriodicGhostDirectionManager directionManager = new PeriodicGhostDirectionManager(model,
        mockDirectionGenerator, ghost, 2, mockBackupDirectionGenerator);
    ghost.setPosition(new Position(0, 2));
    directionManager.update();
    assertEquals(Direction.DOWN, ghost.getDesiredDirection());
    directionManager.update();
    assertEquals(Direction.UP, ghost.getDesiredDirection());

    directionManager.update();
    assertEquals(Direction.UP, ghost.getDesiredDirection());
    directionManager.update();
    assertEquals(Direction.RIGHT, ghost.getDesiredDirection());

    directionManager.update();
    assertEquals(Direction.RIGHT, ghost.getDesiredDirection());
    directionManager.update();
    assertEquals(Direction.LEFT, ghost.getDesiredDirection());

    directionManager.update();
    assertEquals(Direction.LEFT, ghost.getDesiredDirection());
    directionManager.update();
    assertEquals(Direction.DOWN, ghost.getDesiredDirection());
  }

  @Test
  public void whenAfraidAndPacmanInLineOfSightThenMoveInOppositeDirection() {
    pacman.setIsInvincible(true);
    PeriodicGhostDirectionManager directionManager =
        new PeriodicGhostDirectionManager(model, null, ghost, 1, mockBackupDirectionGenerator);

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
    PeriodicGhostDirectionManager directionManager = new PeriodicGhostDirectionManager(model,
        mockDirectionGenerator, ghost, 1, mockBackupDirectionGenerator);

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
    PeriodicGhostDirectionManager directionManager = new PeriodicGhostDirectionManager(model,
        mockDirectionGenerator, ghost, 1, mockBackupDirectionGenerator);

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

    PeriodicGhostDirectionManager directionManager = new PeriodicGhostDirectionManager(model,
        mockDirectionGenerator, ghost, 1, mockBackupDirectionGenerator);

    directionManager.update();
    assertEquals(Direction.DOWN, ghost.getDesiredDirection());
  }

  @Test
  public void whenOverrideCommandReturnNullDirection() {
    IDirectionGenerator mockDirectionGenerator = mock(RandomDirectionGenerator.class);
    when(mockDirectionGenerator.get()).thenReturn(Direction.UP).thenReturn(Direction.RIGHT)
        .thenReturn(Direction.DOWN).thenReturn(Direction.LEFT);
    ghost.setPosition(new Position(0, 0));

    PeriodicGhostDirectionManager directionManager = new PeriodicGhostDirectionManager(model,
        mockDirectionGenerator, ghost, 1, mockBackupDirectionGenerator);

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
    Level ghostRoomLevel =
        MockLevelFactory.getMockLevelGhostRoomWithOneGhostGatesSurroundedByWall();
    ghostRoomLevel.setPacMan(pacman);
    model.setCurrentLevel(ghostRoomLevel);

    IDirectionGenerator mockDirectionGenerator = mock(RandomDirectionGenerator.class);
    when(mockDirectionGenerator.get()).thenReturn(Direction.DOWN);
    PeriodicGhostDirectionManager directionManager = new PeriodicGhostDirectionManager(model,
        mockDirectionGenerator, ghost, 2, mockBackupDirectionGenerator);

    ghost.setPosition(new Position(3, 1));
    directionManager.update();
    assertEquals(Direction.LEFT, ghost.getDesiredDirection());

    ghost.setPosition(new Position(2, 1));
    directionManager.update();
    assertEquals(Direction.UP, ghost.getDesiredDirection());

    ghost.setPosition(new Position(1, 1));
    directionManager.update();
    assertEquals(Direction.RIGHT, ghost.getDesiredDirection());

    ghost.setPosition(new Position(2, 1));
    directionManager.update();
    assertEquals(Direction.UP, ghost.getDesiredDirection());
  }

  @Test
  public void WhenTheChosenDirectionIsInvalidThenPickAValidBackupDirection() {
    IDirectionGenerator mockDirectionGenerator = mock(IDirectionGenerator.class);
    Level mockLevelFourByFourEmptySurroundedByWalls =
        MockLevelFactory.getMockLevelFourByFourEmptySurroundedByWalls();
    mockLevelFourByFourEmptySurroundedByWalls.setPacMan(pacman);
    mockLevelFourByFourEmptySurroundedByWalls.setGhosts(Arrays.asList(ghost));
    model.setCurrentLevel(mockLevelFourByFourEmptySurroundedByWalls);

    when(mockDirectionGenerator.get()).thenReturn(Direction.UP).thenReturn(Direction.RIGHT)
        .thenReturn(Direction.LEFT).thenReturn(Direction.DOWN);
    PeriodicGhostDirectionManager directionManager = new PeriodicGhostDirectionManager(model,
        mockDirectionGenerator, ghost, 1, mockBackupDirectionGenerator);

    ghost.setDesiredDirection(Direction.UP);
    model.getCurrentLevel().getGhosts().get(0).setPosition(new Position(1, 1));
    when(mockBackupDirectionGenerator.get()).thenReturn(Direction.DOWN);
    directionManager.update();
    assertEquals(Direction.DOWN, ghost.getDesiredDirection());

    ghost.setDesiredDirection(Direction.UP);
    model.getCurrentLevel().getGhosts().get(0).setPosition(new Position(2, 1));
    when(mockBackupDirectionGenerator.get()).thenReturn(Direction.LEFT);
    directionManager.update();
    assertEquals(Direction.LEFT, ghost.getDesiredDirection());

    ghost.setDesiredDirection(Direction.DOWN);
    model.getCurrentLevel().getGhosts().get(0).setPosition(new Position(1, 2));
    when(mockBackupDirectionGenerator.get()).thenReturn(Direction.RIGHT);
    directionManager.update();
    assertEquals(Direction.RIGHT, ghost.getDesiredDirection());

    ghost.setDesiredDirection(Direction.DOWN);
    model.getCurrentLevel().getGhosts().get(0).setPosition(new Position(2, 2));
    when(mockBackupDirectionGenerator.get()).thenReturn(Direction.UP);
    directionManager.update();
    assertEquals(Direction.UP, ghost.getDesiredDirection());
  }

  @Test
  public void WhenTheChosenDirectionAndFirstBackupDirectionIsInvalidThenPickTheNextValidBackupDirection() {
    IDirectionGenerator mockDirectionGenerator = mock(IDirectionGenerator.class);
    Level mockLevelFourByFourEmptySurroundedByWalls =
        MockLevelFactory.getMockLevelFourByFourEmptySurroundedByWalls();
    mockLevelFourByFourEmptySurroundedByWalls.setPacMan(pacman);
    mockLevelFourByFourEmptySurroundedByWalls.setGhosts(Arrays.asList(ghost));
    model.setCurrentLevel(mockLevelFourByFourEmptySurroundedByWalls);

    when(mockDirectionGenerator.get()).thenReturn(Direction.UP).thenReturn(Direction.RIGHT)
        .thenReturn(Direction.LEFT).thenReturn(Direction.DOWN);
    PeriodicGhostDirectionManager directionManager = new PeriodicGhostDirectionManager(model,
        mockDirectionGenerator, ghost, 1, mockBackupDirectionGenerator);

    ghost.setDesiredDirection(Direction.UP);
    model.getCurrentLevel().getGhosts().get(0).setPosition(new Position(1, 1));
    when(mockBackupDirectionGenerator.get()).thenReturn(Direction.UP).thenReturn(Direction.LEFT)
        .thenReturn(Direction.DOWN);
    directionManager.update();
    assertEquals(Direction.DOWN, ghost.getDesiredDirection());
  }

}
