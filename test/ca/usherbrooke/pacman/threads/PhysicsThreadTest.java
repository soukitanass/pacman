package ca.usherbrooke.pacman.threads;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ca.usherbrooke.pacman.model.GameEvent;
import ca.usherbrooke.pacman.model.Ghost;
import ca.usherbrooke.pacman.model.Level;
import ca.usherbrooke.pacman.model.PacMan;
import ca.usherbrooke.pacman.model.Position;

public class PhysicsThreadTest {
  private static final int PACGUM_CODE = 39;
  private static final int SUPER_PACGUM_CODE = 40;
  private final int SLEEP_TIME = 100;

  private PhysicsThread physicsThread;
  private Queue<Level> moveQueue = new ConcurrentLinkedQueue<>();
  private Queue<GameEvent> eventQueue = new ConcurrentLinkedQueue<>();
  private static List<Ghost> ghosts = new ArrayList<>();
  private static PacMan pacman = new PacMan();
  private static Ghost ghost = new Ghost();
  private static Level level = new Level();

  @BeforeClass
  public static void createLevel() {
    List<List<Integer>> map = Arrays.asList(Arrays.asList(0, PACGUM_CODE, SUPER_PACGUM_CODE, 0));
    ghosts.add(ghost);
    level.setPacMan(pacman);
    level.setGhost(ghosts);
    level.setMap(map);
  }

  @Before
  public void setUp() throws Exception {
    physicsThread = new PhysicsThread(moveQueue, eventQueue);
    physicsThread.start();
    physicsThread.join(100);
    assertTrue("thread dead", physicsThread.isAlive());
  }

  @After
  public void tearDown() {
    boolean expectedStop = true;
    boolean actualStop = physicsThread.isAlive();
    if (actualStop) {
      physicsThread.stopThread();
      try {
        physicsThread.join(1500);
        actualStop = !physicsThread.isAlive();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      assertEquals("Thread not stopped", expectedStop, actualStop);
    }
  }

  @Test
  public void pacmanPacgumConsumedTest() throws InterruptedException {
    pacman.setPosition(new Position(1, 0));
    moveQueue.add(level);
    Thread.sleep(SLEEP_TIME);
    assertEquals(GameEvent.PACGUM_CONSUMED, eventQueue.poll());
  }

  @Test
  public void pacmanSuperPacgumConsumedTest() throws InterruptedException {
    pacman.setPosition(new Position(2, 0));
    moveQueue.add(level);
    Thread.sleep(SLEEP_TIME);
    assertEquals(GameEvent.SUPER_PACGUM_CONSUMED, eventQueue.poll());
  }

  @Test
  public void pacmanGhostColision() throws InterruptedException {
    pacman.setPosition(new Position(3, 0));
    ghost.setPosition(new Position(3, 0));
    moveQueue.add(level);
    Thread.sleep(SLEEP_TIME);
    assertEquals(GameEvent.PACMAN_GHOST_COLLISON, eventQueue.poll());
  }

  @Test
  public void pacmanNothingConsumedTest() throws InterruptedException {
    pacman.setPosition(new Position(0, 0));
    moveQueue.add(level);
    Thread.sleep(SLEEP_TIME);
    assertNull(eventQueue.poll());
  }
}
