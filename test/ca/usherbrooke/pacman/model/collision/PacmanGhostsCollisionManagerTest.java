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
import java.util.ArrayList;
import java.util.List;
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
  private static final String LEVEL_PATH = "Level.json";
  private Level level;
  private Ghost ghost;
  private IGameModel model;
  private PacmanGhostCollisionManager pacmanGhostCollisionManager;


  @Before
  public void setUp() {

    this.ghost = new Ghost();
    this.model = new GameModel(Game.loadLevel(LEVEL_PATH));
    this.level = model.getCurrentLevel();
    initializeGhostsList();
  }

  @Test
  public void pacmanGhostCollisionManagerLivesTest() {
    int actualValue;
    level.getPacMan().setPosition(new Position(0, 0));
    pacmanGhostCollisionManager.update();
    actualValue = 2;
    assertEquals(actualValue, model.getLives());

    level.getGhosts().get(0).setPosition(new Position(0, 1));
    level.getPacMan().setPosition(new Position(0, 1));
    pacmanGhostCollisionManager.update();
    actualValue = 1;
    assertEquals(actualValue, model.getLives());

    level.getGhosts().get(0).setPosition(new Position(1, 0));
    level.getPacMan().setPosition(new Position(1, 0));
    pacmanGhostCollisionManager.update();
    actualValue = 0;
    assertEquals(actualValue, model.getLives());

  }

  @Test
  public void pacmanGhostCollisionPacManChangePositionTest() {
    Position expectedPacManPosition = new Position(13, 23);
    Direction expectedPacManDirection = Direction.LEFT;
    level.getPacMan().setPosition(new Position(0, 0));
    pacmanGhostCollisionManager.update();
    assertEquals(expectedPacManPosition, model.getCurrentLevel().getPacMan().getPosition());
    assertEquals(expectedPacManDirection, model.getCurrentLevel().getPacMan().getDirection());
  }

  @Test
  public void pacmanGhostCollisionGhostsChangePositionTest() {
    Position expectedGhost0Position = new Position(13, 11);
    Position expectedGhost1Position = new Position(11, 15);
    Ghost ghost = new Ghost();
    ghost.setPosition(new Position(1, 0));
    level.getGhosts().add(ghost);
    level.getPacMan().setPosition(new Position(0, 0));
    pacmanGhostCollisionManager.update();
    assertEquals(expectedGhost0Position, model.getCurrentLevel().getGhosts().get(0).getPosition());
    assertEquals(expectedGhost1Position, model.getCurrentLevel().getGhosts().get(1).getPosition());
  }


  private void initializeGhostsList() {
    List<Ghost> ghosts = new ArrayList<Ghost>();
    IGameModel model = new GameModel(Game.loadLevel(LEVEL_PATH));
    level.setGhost(ghosts);
    level.setPacMan(new PacMan(new Position(0, 0)));
    ghost.setPosition(new Position(0, 0));
    level.getGhosts().add(ghost);
    pacmanGhostCollisionManager =
        new PacmanGhostCollisionManager(level, model.getCurrentLevel(), this.model);
  }
}
