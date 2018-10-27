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
import ca.usherbrooke.pacman.model.objects.Ghost;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.objects.PacMan;
import ca.usherbrooke.pacman.model.position.Position;

public class PacmanGhostsCollisionManagerTest {
  private static final String LEVEL_PATH = "ThreeByOneLevelWithPacmanAndTwoGhosts.json";
  private Level level;
  private IGameModel model;
  private PacmanGhostCollisionManager pacmanGhostCollisionManager;

  @Before
  public void setUp() {
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
    pacmanGhostCollisionManager.update();
    assertEquals(Game.loadLevel(LEVEL_PATH), model.getInitialLevel());
  }

  @Test
  public void whenHitByGhostThenPacmanLosesLife() {
    assertEquals(3, model.getLives());

    level.getPacMan().setPosition(new Position(0, 0));
    pacmanGhostCollisionManager.update();
    assertEquals(2, model.getLives());

    level.getGhosts().get(0).setPosition(new Position(0, 1));
    level.getPacMan().setPosition(new Position(0, 1));
    pacmanGhostCollisionManager.update();
    assertEquals(1, model.getLives());

    level.getGhosts().get(0).setPosition(new Position(1, 0));
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
  public void pacmanGhostCollisionGhostsChangePositionTest() {
    Position expectedGhost0Position = new Position(1, 0);
    Position expectedGhost1Position = new Position(2, 0);
    level.getPacMan().setPosition(new Position(0, 0));
    pacmanGhostCollisionManager.update();
    assertEquals(expectedGhost0Position, model.getCurrentLevel().getGhosts().get(0).getPosition());
    assertEquals(expectedGhost1Position, model.getCurrentLevel().getGhosts().get(1).getPosition());
  }

  @Test
  public void whenCollisionWithInvinciblePacmanThenPacmanGhostKillCountIsIncremented() {
    level.getPacMan().setIsInvincible(true);
    PacMan expectedPacman = new PacMan(level.getPacMan());
    expectedPacman.setGhostKillsSinceInvincible(1);
    pacmanGhostCollisionManager.update();
    assertEquals(expectedPacman, level.getPacMan());
  }

  @Test
  public void whenCollisionWithInvinciblePacmanThenGhostDies() {
    level.getPacMan().setIsInvincible(true);
    assertEquals(4, level.getGhosts().size());
    model.getCurrentLevel().getPacMan().setPosition(new Position(1, 0));
    pacmanGhostCollisionManager.update();
    assertEquals(3, level.getGhosts().size());
  }

  @Test
  public void getCollidingGhost() {
    assertNull(pacmanGhostCollisionManager.getCollidingGhost());
    Ghost firstGhost = model.getCurrentLevel().getGhosts().get(0);
    Ghost secondGhost = model.getCurrentLevel().getGhosts().get(1);
    model.getCurrentLevel().getPacMan().setPosition(new Position(1, 0));
    assertEquals(firstGhost, pacmanGhostCollisionManager.getCollidingGhost());
    model.getCurrentLevel().getPacMan().setPosition(new Position(2, 0));
    assertEquals(secondGhost, pacmanGhostCollisionManager.getCollidingGhost());
  }

  @Test
  public void whenPacmanDiesThenReviveGhostsAtTheirInitialPosition() {
    model.getCurrentLevel().getPacMan().setIsInvincible(true);
    model.getCurrentLevel().getPacMan().setPosition(new Position(1, 0));
    assertEquals(4, model.getCurrentLevel().getGhosts().size());
    pacmanGhostCollisionManager.update();
    assertEquals(3, model.getCurrentLevel().getGhosts().size());
    model.getCurrentLevel().getPacMan().setIsInvincible(false);
    pacmanGhostCollisionManager.update();
    assertEquals(4, model.getCurrentLevel().getGhosts().size());
  }

  private void verifyInitialLevel() {
    assertEquals(new Position(0, 0), model.getCurrentLevel().getPacMan().getPosition());

    assertEquals(4, model.getCurrentLevel().getGhosts().size());
    assertEquals(new Position(1, 0), model.getCurrentLevel().getGhosts().get(0).getPosition());
    assertEquals(new Position(2, 0), model.getCurrentLevel().getGhosts().get(1).getPosition());

    assertEquals(Integer.valueOf(3), model.getCurrentLevel().getWidth());
    assertEquals(Integer.valueOf(1), model.getCurrentLevel().getHeight());
    assertFalse(model.getCurrentLevel().isWall(new Position(0, 0)));
    assertFalse(model.getCurrentLevel().isWall(new Position(1, 0)));
    assertFalse(model.getCurrentLevel().isWall(new Position(2, 0)));

    assertFalse(model.getCurrentLevel().getPacMan().isInvincible());
  }
}
