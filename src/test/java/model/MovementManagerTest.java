package model;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import view.Direction;

public class MovementManagerTest {

  private PacMan pacman;
  private Level level;
  private MovementManager movementManager;

  @Before
  public void setUp() {
    this.pacman = new PacMan();
    this.level = new Level();
  }

  @Test
  public void moveRight() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0, 0));
    initializeMovementManager(map);
    pacman.setPosition(new Position(0, 0));

    movementManager.setPacmanDirection(Direction.RIGHT);
    movementManager.updatePacmanPosition();

    assertEquals(new Position(1, 0), pacman.getPosition());
  }

  @Test
  public void moveLeft() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0, 0));
    pacman.setPosition(new Position(1, 0));
    initializeMovementManager(map);

    pacman.setDirection(Direction.LEFT);
    movementManager.updatePacmanPosition();

    assertEquals(new Position(0, 0), pacman.getPosition());
  }

  @Test
  public void moveUp() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0), Arrays.asList(0));
    pacman.setPosition(new Position(0, 1));
    initializeMovementManager(map);

    pacman.setDirection(Direction.UP);
    movementManager.updatePacmanPosition();

    assertEquals(new Position(0, 0), pacman.getPosition());
  }

  @Test
  public void moveDown() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0), Arrays.asList(0));
    pacman.setPosition(new Position(0, 0));
    initializeMovementManager(map);

    pacman.setDirection(Direction.DOWN);
    movementManager.updatePacmanPosition();

    assertEquals(new Position(0, 1), pacman.getPosition());
  }

  @Test
  public void teleportingToTheLeft() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0, 0));
    pacman.setPosition(new Position(1, 0));
    initializeMovementManager(map);

    pacman.setDirection(Direction.RIGHT);
    movementManager.updatePacmanPosition();

    assertEquals(new Position(0, 0), pacman.getPosition());
  }

  @Test
  public void teleportingToTheRight() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0, 0));
    pacman.setPosition(new Position(0, 0));
    initializeMovementManager(map);

    pacman.setDirection(Direction.LEFT);
    movementManager.updatePacmanPosition();

    assertEquals(new Position(1, 0), pacman.getPosition());
  }

  @Test
  public void teleportingToTheTop() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0), Arrays.asList(0));
    pacman.setPosition(new Position(0, 1));
    initializeMovementManager(map);

    pacman.setDirection(Direction.DOWN);
    movementManager.updatePacmanPosition();

    assertEquals(new Position(0, 0), pacman.getPosition());
  }

  @Test
  public void teleportingToTheBottom() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0), Arrays.asList(0));
    pacman.setPosition(new Position(0, 0));
    initializeMovementManager(map);

    pacman.setDirection(Direction.UP);
    movementManager.updatePacmanPosition();

    assertEquals(new Position(0, 1), pacman.getPosition());
  }

  @Test
  public void preventTurningTowardsWall() {
    List<List<Integer>> map =
        Arrays.asList(Arrays.asList(1, 1, 1), Arrays.asList(1, 0, 1), Arrays.asList(1, 1, 1));
    pacman.setPosition(new Position(1, 1));
    initializeMovementManager(map);
    pacman.setDirection(Direction.UP);

    movementManager.setPacmanDirection(Direction.LEFT);
    assertEquals(Direction.UP, pacman.getDirection());

    movementManager.setPacmanDirection(Direction.RIGHT);
    assertEquals(Direction.UP, pacman.getDirection());

    movementManager.setPacmanDirection(Direction.DOWN);
    assertEquals(Direction.UP, pacman.getDirection());

    pacman.setDirection(Direction.DOWN);
    movementManager.setPacmanDirection(Direction.UP);
    assertEquals(Direction.DOWN, pacman.getDirection());
  }

  @Test
  public void preventMovingIntoWall() {
    List<List<Integer>> map =
        Arrays.asList(Arrays.asList(1, 1, 1), Arrays.asList(1, 0, 1), Arrays.asList(1, 1, 1));
    pacman.setPosition(new Position(1, 1));
    initializeMovementManager(map);

    pacman.setDirection(Direction.UP);
    movementManager.updatePacmanPosition();
    assertEquals(new Position(1, 1), pacman.getPosition());

    pacman.setDirection(Direction.DOWN);
    movementManager.updatePacmanPosition();
    assertEquals(new Position(1, 1), pacman.getPosition());

    pacman.setDirection(Direction.LEFT);
    movementManager.updatePacmanPosition();
    assertEquals(new Position(1, 1), pacman.getPosition());

    pacman.setDirection(Direction.RIGHT);
    movementManager.updatePacmanPosition();
    assertEquals(new Position(1, 1), pacman.getPosition());
  }

  @Test
  public void turnTowardsDesiredDirectionAsSoonAsThereIsNoWall() {
    List<List<Integer>> map =
        Arrays.asList(Arrays.asList(0, 0, 0), Arrays.asList(0, 1, 0), Arrays.asList(0, 0, 0));
    initializeMovementManager(map);
    pacman.setPosition(new Position(0, 0));
    movementManager.setPacmanDirection(Direction.RIGHT);

    movementManager.updatePacmanPosition();
    movementManager.setPacmanDirection(Direction.DOWN);
    assertEquals(Direction.RIGHT, pacman.getDirection());
    movementManager.updatePacmanPosition();
    assertEquals(Direction.DOWN, pacman.getDirection());

    movementManager.updatePacmanPosition();
    movementManager.setPacmanDirection(Direction.LEFT);
    assertEquals(Direction.DOWN, pacman.getDirection());
    movementManager.updatePacmanPosition();
    assertEquals(Direction.LEFT, pacman.getDirection());

    movementManager.updatePacmanPosition();
    movementManager.setPacmanDirection(Direction.UP);
    assertEquals(Direction.LEFT, pacman.getDirection());
    movementManager.updatePacmanPosition();
    assertEquals(Direction.UP, pacman.getDirection());

    movementManager.updatePacmanPosition();
    movementManager.setPacmanDirection(Direction.RIGHT);
    assertEquals(Direction.UP, pacman.getDirection());
    movementManager.updatePacmanPosition();
    assertEquals(Direction.RIGHT, pacman.getDirection());
  }

  private void initializeMovementManager(List<List<Integer>> map) {
    level.setMap(map);
    IMoveValidator moveValidator = new MoveValidator(level);
    movementManager = new MovementManager(pacman, moveValidator);
  }
}
