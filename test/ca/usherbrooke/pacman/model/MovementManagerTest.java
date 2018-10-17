/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.direction.IGameObject;
import ca.usherbrooke.pacman.model.direction.Position;
import ca.usherbrooke.pacman.model.movement.IMoveValidator;
import ca.usherbrooke.pacman.model.movement.MovementManager;
import ca.usherbrooke.pacman.model.movement.PacmanMoveValidator;

public class MovementManagerTest {

  private IGameObject gameObject;
  private Level level;
  private MovementManager movementManager;

  @Before
  public void setUp() {
    this.gameObject = new PacMan();
    this.level = new Level();
  }

  @Test
  public void moveRight() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0, 0));
    initializeMovementManager(map);
    gameObject.setPosition(new Position(0, 0));

    movementManager.setDirection(Direction.RIGHT);

    assertEquals(new Position(1, 0), movementManager.getPosition());
  }

  @Test
  public void moveLeft() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0, 0));
    gameObject.setPosition(new Position(1, 0));
    initializeMovementManager(map);

    gameObject.setDirection(Direction.LEFT);

    assertEquals(new Position(0, 0), movementManager.getPosition());
  }

  @Test
  public void moveUp() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0), Arrays.asList(0));
    gameObject.setPosition(new Position(0, 1));
    initializeMovementManager(map);

    gameObject.setDirection(Direction.UP);

    assertEquals(new Position(0, 0), movementManager.getPosition());
  }

  @Test
  public void moveDown() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0), Arrays.asList(0));
    gameObject.setPosition(new Position(0, 0));
    initializeMovementManager(map);

    gameObject.setDirection(Direction.DOWN);

    assertEquals(new Position(0, 1), movementManager.getPosition());
  }

  @Test
  public void teleportingToTheLeft() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0, 0));
    gameObject.setPosition(new Position(1, 0));
    initializeMovementManager(map);

    gameObject.setDirection(Direction.RIGHT);

    assertEquals(new Position(0, 0), movementManager.getPosition());
  }

  @Test
  public void teleportingToTheRight() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0, 0));
    gameObject.setPosition(new Position(0, 0));
    initializeMovementManager(map);

    gameObject.setDirection(Direction.LEFT);

    assertEquals(new Position(1, 0), movementManager.getPosition());
  }

  @Test
  public void teleportingToTheTop() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0), Arrays.asList(0));
    gameObject.setPosition(new Position(0, 1));
    initializeMovementManager(map);

    gameObject.setDirection(Direction.DOWN);

    assertEquals(new Position(0, 0), movementManager.getPosition());
  }

  @Test
  public void teleportingToTheBottom() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0), Arrays.asList(0));
    gameObject.setPosition(new Position(0, 0));
    initializeMovementManager(map);

    gameObject.setDirection(Direction.UP);

    assertEquals(new Position(0, 1), movementManager.getPosition());
  }

  @Test
  public void preventTurningTowardsWall() {
    List<List<Integer>> map =
        Arrays.asList(Arrays.asList(1, 1, 1), Arrays.asList(1, 0, 1), Arrays.asList(1, 1, 1));
    gameObject.setPosition(new Position(1, 1));
    initializeMovementManager(map);
    gameObject.setDirection(Direction.UP);

    movementManager.setDirection(Direction.LEFT);
    assertEquals(Direction.UP, gameObject.getDirection());

    movementManager.setDirection(Direction.RIGHT);
    assertEquals(Direction.UP, gameObject.getDirection());

    movementManager.setDirection(Direction.DOWN);
    assertEquals(Direction.UP, gameObject.getDirection());

    gameObject.setDirection(Direction.DOWN);
    movementManager.setDirection(Direction.UP);
    assertEquals(Direction.DOWN, gameObject.getDirection());
  }

  @Test
  public void preventMovingIntoWall() {
    List<List<Integer>> map =
        Arrays.asList(Arrays.asList(1, 1, 1), Arrays.asList(1, 0, 1), Arrays.asList(1, 1, 1));
    gameObject.setPosition(new Position(1, 1));
    initializeMovementManager(map);

    gameObject.setDirection(Direction.UP);

    assertEquals(new Position(1, 1), movementManager.getPosition());

    gameObject.setDirection(Direction.DOWN);

    assertEquals(new Position(1, 1), movementManager.getPosition());

    gameObject.setDirection(Direction.LEFT);

    assertEquals(new Position(1, 1), movementManager.getPosition());

    gameObject.setDirection(Direction.RIGHT);

    assertEquals(new Position(1, 1), movementManager.getPosition());
  }

  private void initializeMovementManager(List<List<Integer>> map) {
    level.setMap(map);
    IMoveValidator moveValidator = new PacmanMoveValidator(level);
    movementManager = new MovementManager(gameObject, moveValidator);
  }
}
