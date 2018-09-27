package ca.usherbrooke.pacman.model;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

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
    movementManager.updatePosition();

    assertEquals(new Position(1, 0), gameObject.getPosition());
  }

  @Test
  public void moveLeft() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0, 0));
    gameObject.setPosition(new Position(1, 0));
    initializeMovementManager(map);

    gameObject.setDirection(Direction.LEFT);
    movementManager.updatePosition();

    assertEquals(new Position(0, 0), gameObject.getPosition());
  }

  @Test
  public void moveUp() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0), Arrays.asList(0));
    gameObject.setPosition(new Position(0, 1));
    initializeMovementManager(map);

    gameObject.setDirection(Direction.UP);
    movementManager.updatePosition();

    assertEquals(new Position(0, 0), gameObject.getPosition());
  }

  @Test
  public void moveDown() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0), Arrays.asList(0));
    gameObject.setPosition(new Position(0, 0));
    initializeMovementManager(map);

    gameObject.setDirection(Direction.DOWN);
    movementManager.updatePosition();

    assertEquals(new Position(0, 1), gameObject.getPosition());
  }

  @Test
  public void teleportingToTheLeft() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0, 0));
    gameObject.setPosition(new Position(1, 0));
    initializeMovementManager(map);

    gameObject.setDirection(Direction.RIGHT);
    movementManager.updatePosition();

    assertEquals(new Position(0, 0), gameObject.getPosition());
  }

  @Test
  public void teleportingToTheRight() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0, 0));
    gameObject.setPosition(new Position(0, 0));
    initializeMovementManager(map);

    gameObject.setDirection(Direction.LEFT);
    movementManager.updatePosition();

    assertEquals(new Position(1, 0), gameObject.getPosition());
  }

  @Test
  public void teleportingToTheTop() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0), Arrays.asList(0));
    gameObject.setPosition(new Position(0, 1));
    initializeMovementManager(map);

    gameObject.setDirection(Direction.DOWN);
    movementManager.updatePosition();

    assertEquals(new Position(0, 0), gameObject.getPosition());
  }

  @Test
  public void teleportingToTheBottom() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0), Arrays.asList(0));
    gameObject.setPosition(new Position(0, 0));
    initializeMovementManager(map);

    gameObject.setDirection(Direction.UP);
    movementManager.updatePosition();

    assertEquals(new Position(0, 1), gameObject.getPosition());
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
    movementManager.updatePosition();
    assertEquals(new Position(1, 1), gameObject.getPosition());

    gameObject.setDirection(Direction.DOWN);
    movementManager.updatePosition();
    assertEquals(new Position(1, 1), gameObject.getPosition());

    gameObject.setDirection(Direction.LEFT);
    movementManager.updatePosition();
    assertEquals(new Position(1, 1), gameObject.getPosition());

    gameObject.setDirection(Direction.RIGHT);
    movementManager.updatePosition();
    assertEquals(new Position(1, 1), gameObject.getPosition());
  }

  @Test
  public void turnTowardsDesiredDirectionAsSoonAsThereIsNoWall() {
    List<List<Integer>> map =
        Arrays.asList(Arrays.asList(0, 0, 0), Arrays.asList(0, 1, 0), Arrays.asList(0, 0, 0));
    initializeMovementManager(map);
    gameObject.setPosition(new Position(0, 0));
    movementManager.setDirection(Direction.RIGHT);

    movementManager.updatePosition();
    movementManager.setDirection(Direction.DOWN);
    assertEquals(Direction.RIGHT, gameObject.getDirection());
    movementManager.updatePosition();
    assertEquals(Direction.DOWN, gameObject.getDirection());

    movementManager.updatePosition();
    movementManager.setDirection(Direction.LEFT);
    assertEquals(Direction.DOWN, gameObject.getDirection());
    movementManager.updatePosition();
    assertEquals(Direction.LEFT, gameObject.getDirection());

    movementManager.updatePosition();
    movementManager.setDirection(Direction.UP);
    assertEquals(Direction.LEFT, gameObject.getDirection());
    movementManager.updatePosition();
    assertEquals(Direction.UP, gameObject.getDirection());

    movementManager.updatePosition();
    movementManager.setDirection(Direction.RIGHT);
    assertEquals(Direction.UP, gameObject.getDirection());
    movementManager.updatePosition();
    assertEquals(Direction.RIGHT, gameObject.getDirection());
  }

  private void initializeMovementManager(List<List<Integer>> map) {
    level.setMap(map);
    IMoveValidator moveValidator = new PacmanMoveValidator(level);
    movementManager = new MovementManager(gameObject, moveValidator);
  }
}
