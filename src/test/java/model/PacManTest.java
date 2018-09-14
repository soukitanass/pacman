package model;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class PacManTest {
  private PacMan pacman;
  int levelWidth = 10;
  int levelHeight = 10;

  @Before
  public void setUp() {
    Position initialPosition = new Position(2, 2);
    pacman = new PacMan();
    pacman.setPosition(initialPosition);
  }

  @Test
  public void moveRight() {
    // Assign
    Direction direction = Direction.RIGHT;

    // Act
    pacman.setDirection(direction);
    pacman.updatePosition(levelWidth, levelHeight);

    // Assert
    Position position = pacman.getPosition();
    assertEquals(3, (int) position.getX());
    assertEquals(2, (int) position.getY());
  }

  @Test
  public void moveLeft() {
    // Assign
    Direction direction = Direction.LEFT;

    // Act
    pacman.setDirection(direction);
    pacman.updatePosition(levelWidth, levelHeight);

    // Assert
    Position position = pacman.getPosition();
    assertEquals(1, (int) position.getX());
    assertEquals(2, (int) position.getY());
  }

  @Test
  public void moveUp() {
    // Assign
    Direction direction = Direction.UP;

    // Act
    pacman.setDirection(direction);
    pacman.updatePosition(levelWidth, levelHeight);

    // Assert
    Position position = pacman.getPosition();
    assertEquals(2, (int) position.getX());
    assertEquals(1, (int) position.getY());
  }

  @Test
  public void moveDown() {
    // Assign
    Direction direction = Direction.DOWN;

    // Act
    pacman.setDirection(direction);
    pacman.updatePosition(levelWidth, levelHeight);

    // Assert
    Position position = pacman.getPosition();
    assertEquals(2, (int) position.getX());
    assertEquals(3, (int) position.getY());
  }

  @Test
  public void teleportingToTheLeft() {
    // Assign
    Direction direction = Direction.RIGHT;
    Position initialPosition = new Position(levelWidth, 2);

    // Act
    pacman.setPosition(initialPosition);
    pacman.setDirection(direction);
    pacman.updatePosition(levelWidth, levelHeight);

    // Assert
    Position position = pacman.getPosition();
    assertEquals(0, (int) position.getX());
    assertEquals(2, (int) position.getY());
  }

  @Test
  public void teleportingToTheRight() {
    // Assign
    Direction direction = Direction.LEFT;
    Position initialPosition = new Position(0, 2);

    // Act
    pacman.setPosition(initialPosition);
    pacman.setDirection(direction);
    pacman.updatePosition(levelWidth, levelHeight);

    // Assert
    Position position = pacman.getPosition();
    assertEquals(levelWidth, (int) position.getX());
    assertEquals(2, (int) position.getY());
  }

  @Test
  public void teleportingToTheTop() {
    // Assign
    Direction direction = Direction.DOWN;
    Position initialPosition = new Position(2, levelHeight);

    // Act
    pacman.setPosition(initialPosition);
    pacman.setDirection(direction);
    pacman.updatePosition(levelWidth, levelHeight);

    // Assert
    Position position = pacman.getPosition();
    assertEquals(2, (int) position.getX());
    assertEquals(0, (int) position.getY());
  }

  @Test
  public void teleportingToTheBottom() {
    // Assign
    Direction direction = Direction.UP;
    Position initialPosition = new Position(2, 0);

    // Act
    pacman.setPosition(initialPosition);
    pacman.setDirection(direction);
    pacman.updatePosition(levelWidth, levelHeight);

    // Assert
    Position position = pacman.getPosition();
    assertEquals(2, (int) position.getX());
    assertEquals(levelHeight, (int) position.getY());
  }
}
