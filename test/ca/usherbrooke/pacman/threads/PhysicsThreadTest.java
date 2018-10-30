/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.threads;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.model.GameModel;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.events.GameEvent;
import ca.usherbrooke.pacman.model.events.GameEventObject;
import ca.usherbrooke.pacman.model.exceptions.InvalidGhostNameException;
import ca.usherbrooke.pacman.model.objects.Ghost;
import ca.usherbrooke.pacman.model.objects.GhostName;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.objects.PacMan;
import ca.usherbrooke.pacman.model.position.Position;

public class PhysicsThreadTest {
  private final int SLEEP_TIME = 250;
  private final int JOIN_TIME = 1500;

  private static final int PACGUM_CODE = 39;
  private static final int SUPER_PACGUM_CODE = 40;

  private final Queue<Level> moveQueue = new ConcurrentLinkedQueue<>();
  private final Queue<GameEventObject> eventQueue = new ConcurrentLinkedQueue<>();
  private final IGameModel model = new GameModel();
  private final PhysicsThread physicsThread = new PhysicsThread(moveQueue, eventQueue, model);

  @Before
  public void setUp() {
    physicsThread.start();
    try {
      physicsThread.join(100);
    } catch (InterruptedException e) {
      physicsThread.interrupt();
      e.printStackTrace();
    }
    assertTrue("thread dead", physicsThread.isAlive());
  }

  @After
  public void tearDown() {
    boolean isAlive = physicsThread.isAlive();
    if (isAlive) {
      physicsThread.stopThread();
      try {
        physicsThread.join(JOIN_TIME);
        isAlive = physicsThread.isAlive();
      } catch (InterruptedException e) {
        physicsThread.interrupt();
        e.printStackTrace();
      }
      assertFalse(isAlive);
    }
  }

  @Test
  public void pacmanPacgumConsumedTest() throws InterruptedException {
    Level level = getLevel();
    level.getPacMan().setPosition(new Position(2, 0));

    synchronized (moveQueue) {
      moveQueue.add(level);
      moveQueue.notify();
    }
    Thread.sleep(SLEEP_TIME);

    assertEquals(3, eventQueue.size());
    GameEventObject gameEventObject = eventQueue.poll();
    assertEquals(GameEvent.PACGUM_CONSUMED, gameEventObject.getGameEvent());
    gameEventObject = eventQueue.poll();
    assertEquals(GameEvent.ENTITY_MOVE, gameEventObject.getGameEvent());
    gameEventObject = eventQueue.poll();
    assertEquals(GameEvent.ENTITY_MOVE, gameEventObject.getGameEvent());
  }

  @Test
  public void pacmanSuperPacgumConsumedTest() throws InterruptedException {
    Level level = getLevel();
    level.getPacMan().setPosition(new Position(3, 0));

    synchronized (moveQueue) {
      moveQueue.add(level);
      moveQueue.notify();
    }
    Thread.sleep(SLEEP_TIME);

    assertEquals(3, eventQueue.size());
    GameEventObject gameEventObject = eventQueue.poll();
    assertEquals(GameEvent.SUPER_PACGUM_CONSUMED, gameEventObject.getGameEvent());
    gameEventObject = eventQueue.poll();
    assertEquals(GameEvent.ENTITY_MOVE, gameEventObject.getGameEvent());
    gameEventObject = eventQueue.poll();
    assertEquals(GameEvent.ENTITY_MOVE, gameEventObject.getGameEvent());
  }

  @Test
  public void pacmanGhostColision() throws InterruptedException, InvalidGhostNameException {
    Level level = getLevel();
    level.getPacMan().setPosition(new Position(5, 0));
    level.getGhostByName(GhostName.BLINKY).setPosition(new Position(5, 0));

    synchronized (moveQueue) {
      moveQueue.add(level);
      moveQueue.notify();
    }
    Thread.sleep(SLEEP_TIME);

    assertEquals(3, eventQueue.size());
    GameEventObject gameEventObject = eventQueue.poll();
    assertEquals(GameEvent.PACMAN_GHOST_COLLISON, gameEventObject.getGameEvent());
    gameEventObject = eventQueue.poll();
    assertEquals(GameEvent.ENTITY_MOVE, gameEventObject.getGameEvent());
    gameEventObject = eventQueue.poll();
    assertEquals(GameEvent.ENTITY_MOVE, gameEventObject.getGameEvent());
  }

  @Test
  public void EntitiesMoveTest() throws InterruptedException {
    Level level = getLevel();
    level.getPacMan().setPosition(new Position(0, 0));

    synchronized (moveQueue) {
      moveQueue.add(level);
      moveQueue.notify();
    }
    Thread.sleep(SLEEP_TIME);

    assertEquals(2, eventQueue.size());
    GameEventObject gameEventObject = eventQueue.poll();
    assertEquals(GameEvent.ENTITY_MOVE, gameEventObject.getGameEvent());
    gameEventObject = eventQueue.poll();
    assertEquals(GameEvent.ENTITY_MOVE, gameEventObject.getGameEvent());
  }

  private Level getLevel() {
    List<List<Integer>> map =
        Arrays.asList(Arrays.asList(0, 0, PACGUM_CODE, SUPER_PACGUM_CODE, 0, 0));
    List<Ghost> ghosts = new ArrayList<>();

    Ghost ghost = new Ghost();
    ghost.setName(GhostName.BLINKY);
    ghost.setDesiredDirection(Direction.RIGHT);
    ghost.setDirection(Direction.RIGHT);
    ghost.setPosition(new Position(5, 0));
    ghosts.add(ghost);

    PacMan pacman = new PacMan();
    pacman.setDesiredDirection(Direction.LEFT);
    pacman.setDirection(Direction.LEFT);
    pacman.setPosition(new Position(0, 0));

    Level level = new Level();
    level.setPacMan(pacman);
    level.setGhosts(ghosts);
    level.setMap(map);

    return level;
  }
}
