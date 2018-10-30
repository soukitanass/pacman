/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.collision;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.game.Game;
import ca.usherbrooke.pacman.model.GameModel;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.exceptions.InvalidGhostNameException;
import ca.usherbrooke.pacman.model.objects.Ghost;
import ca.usherbrooke.pacman.model.objects.GhostName;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.objects.PacMan;
import ca.usherbrooke.pacman.model.position.Position;

public class PacmanGhostsCollisionManagerTest {
  private static final String LEVEL_PATH = "ThreeByOneLevelWithPacmanAndGhosts.json";
  private Level level;
  private IGameModel model;
  private PacmanGhostCollisionManager pacmanGhostCollisionManager;

  @Before
  public void setUp() throws InvalidGhostNameException {
    model = new GameModel(Game.loadLevel(LEVEL_PATH));
    model.initializeGame();
    level = model.getCurrentLevel();
    pacmanGhostCollisionManager = new PacmanGhostCollisionManager(level, this.model);
    verifyInitialLevel();
  }

  @Test
  public void whenNotInvincibleThenUpdateDoesNotChangeInitialLevel() {
    pacmanGhostCollisionManager.update();
    assertEquals(Game.loadLevel(LEVEL_PATH), model.getInitialLevel());
  }

  @Test
  public void whenInvincibleThenUpdateDoesNotChangeInitialLevel() {
    level.getPacMan().setIsInvincible(true);
    model.getCurrentLevel().getPacMan().setPosition(new Position(1, 0));
    pacmanGhostCollisionManager.update();
    assertEquals(Game.loadLevel(LEVEL_PATH), model.getInitialLevel());
  }

  @Test
  public void whenHitByGhostThenPacmanLosesLife() throws InvalidGhostNameException {
    assertEquals(3, model.getLives());

    level.getPacMan().setPosition(new Position(0, 0));
    pacmanGhostCollisionManager.update();
    assertEquals(2, model.getLives());

    level.getGhostByName(GhostName.BLINKY).setPosition(new Position(0, 1));
    level.getPacMan().setPosition(new Position(0, 1));
    pacmanGhostCollisionManager.update();
    assertEquals(1, model.getLives());

    level.getGhostByName(GhostName.BLINKY).setPosition(new Position(1, 0));
    level.getPacMan().setPosition(new Position(1, 0));
    pacmanGhostCollisionManager.update();
    assertEquals(0, model.getLives());
  }

  @Test
  public void pacmanGhostCollisionPacManChangePositionTest() {
    Position expectedPacManPosition = new Position(0, 0);
    Direction expectedPacManDirection = Direction.LEFT;
    level.getPacMan().setPosition(new Position(0, 0));
    pacmanGhostCollisionManager.update();
    assertEquals(expectedPacManPosition, model.getCurrentLevel().getPacMan().getPosition());
    assertEquals(expectedPacManDirection, model.getCurrentLevel().getPacMan().getDirection());
  }

  @Test
  public void pacmanGhostCollisionGhostsChangePositionTest() throws InvalidGhostNameException {
    level.getPacMan().setPosition(new Position(0, 0));
    pacmanGhostCollisionManager.update();
    assertEquals(new Position(1, 0),
        model.getCurrentLevel().getGhostByName(GhostName.BLINKY).getPosition());
    assertEquals(new Position(2, 0), model.getCurrentLevel().getGhostByName(GhostName.INKY).getPosition());
  }

  @Test
  public void whenCollisionWithInvinciblePacmanThenPacmanGhostKillCountIsIncremented() {
    model.getCurrentLevel().getPacMan().setPosition(new Position(1, 0));
    model.getCurrentLevel().getPacMan().setIsInvincible(true);
    PacMan expectedPacman = new PacMan(level.getPacMan());
    expectedPacman.setGhostKillsSinceInvincible(1);
    pacmanGhostCollisionManager.update();
    assertEquals(expectedPacman, level.getPacMan());
  }

  @Test
  public void whenCollisionWithInvinciblePacmanThenGhostIsTeleportedToItsInitialPosition()
      throws InvalidGhostNameException {
    assertEquals(4, level.getGhosts().size());
    model.getCurrentLevel().getPacMan().setIsInvincible(true);
    model.getCurrentLevel().getGhostByName(GhostName.BLINKY).setPosition(new Position(42, 42));
    model.getCurrentLevel().getPacMan().setPosition(new Position(42, 42));
    pacmanGhostCollisionManager.update();
    assertEquals(4, level.getGhosts().size());
    assertEquals(new Position(13, 15),
        model.getCurrentLevel().getGhostByName(GhostName.BLINKY).getPosition());
  }

  @Test
  public void getCollidingGhost() throws InvalidGhostNameException {
    assertNull(pacmanGhostCollisionManager.getCollidingGhost());
    Ghost firstGhost = model.getCurrentLevel().getGhostByName(GhostName.BLINKY);
    Ghost secondGhost = model.getCurrentLevel().getGhostByName(GhostName.INKY);
    model.getCurrentLevel().getPacMan().setPosition(new Position(1, 0));
    assertEquals(firstGhost, pacmanGhostCollisionManager.getCollidingGhost());
    model.getCurrentLevel().getPacMan().setPosition(new Position(2, 0));
    assertEquals(secondGhost, pacmanGhostCollisionManager.getCollidingGhost());
  }

  @Test
  public void whenPacmanDiesThenTeleportGhostsAtTheirInitialPosition()
      throws InvalidGhostNameException {
    assertEquals(4, model.getCurrentLevel().getGhosts().size());
    model.getCurrentLevel().getGhostByName(GhostName.BLINKY).setPosition(new Position(42, 42));
    model.getCurrentLevel().getGhostByName(GhostName.INKY).setPosition(new Position(42, 42));
    model.getCurrentLevel().getGhostByName(GhostName.PINKY).setPosition(new Position(42, 42));
    model.getCurrentLevel().getGhostByName(GhostName.CLYDE).setPosition(new Position(42, 42));
    model.getCurrentLevel().getPacMan().setPosition(new Position(42, 42));
    pacmanGhostCollisionManager.update();
    assertEquals(4, model.getCurrentLevel().getGhosts().size());
    assertEquals(new Position(1, 0),
        model.getCurrentLevel().getGhostByName(GhostName.BLINKY).getPosition());
    assertEquals(new Position(2, 0), model.getCurrentLevel().getGhostByName(GhostName.INKY).getPosition());
    assertEquals(new Position(3, 0), model.getCurrentLevel().getGhostByName(GhostName.PINKY).getPosition());
    assertEquals(new Position(4, 0), model.getCurrentLevel().getGhostByName(GhostName.CLYDE).getPosition());
  }

  private void verifyInitialLevel() throws InvalidGhostNameException {
    assertEquals(new Position(0, 0), model.getCurrentLevel().getPacMan().getPosition());

    assertEquals(4, model.getCurrentLevel().getGhosts().size());
    assertEquals(new Position(1, 0),
        model.getCurrentLevel().getGhostByName(GhostName.BLINKY).getPosition());
    assertEquals(new Position(2, 0), model.getCurrentLevel().getGhostByName(GhostName.INKY).getPosition());
    assertEquals(new Position(3, 0), model.getCurrentLevel().getGhostByName(GhostName.PINKY).getPosition());
    assertEquals(new Position(4, 0), model.getCurrentLevel().getGhostByName(GhostName.CLYDE).getPosition());

    assertEquals(Integer.valueOf(3), model.getCurrentLevel().getWidth());
    assertEquals(Integer.valueOf(1), model.getCurrentLevel().getHeight());
    assertFalse(model.getCurrentLevel().isWall(new Position(0, 0)));
    assertFalse(model.getCurrentLevel().isWall(new Position(1, 0)));
    assertFalse(model.getCurrentLevel().isWall(new Position(2, 0)));

    assertFalse(model.getCurrentLevel().getPacMan().isInvincible());
  }
}
