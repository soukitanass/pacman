/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.threads;

import java.util.Queue;
import ca.usherbrooke.pacman.model.GameEvent;
import ca.usherbrooke.pacman.model.GameEventObject;
import ca.usherbrooke.pacman.model.Ghost;
import ca.usherbrooke.pacman.model.GhostMoveValidator;
import ca.usherbrooke.pacman.model.IGameObject;
import ca.usherbrooke.pacman.model.Level;
import ca.usherbrooke.pacman.model.MovementManager;
import ca.usherbrooke.pacman.model.PacMan;
import ca.usherbrooke.pacman.model.PacmanGhostCollisionManager;
import ca.usherbrooke.pacman.model.PacmanMoveValidator;
import ca.usherbrooke.pacman.model.Position;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class PhysicsThread extends Thread {
  private volatile boolean isRunning = false;

  private static final int WAIT_TIME = 30;

  private static final String THREAD_NAME = "Physic_Thread";
  private final Queue<GameEventObject> eventQueue;
  private final Queue<Level> moveQueue;

  public PhysicsThread(Queue<Level> moveQueue, Queue<GameEventObject> eventQueue) {
    this.setName(THREAD_NAME);
    this.eventQueue = eventQueue;
    this.moveQueue = moveQueue;
  }

  public synchronized void stopThread() {
    isRunning = false;
  }

  @Override
  @SuppressWarnings("squid:S106")
  public void run() {
    isRunning = true;
    System.out.println("START - " + this.getName());

    while (isRunning) {
      try {
        synchronized (moveQueue) {
          while (!moveQueue.isEmpty()) {
            Level level = moveQueue.poll();

            validPacgumConsumedEvent(level);
            validSuperPacgumConsumedEvent(level);
            validPacmanGhostsCollisionEvent(level);
            validPacmanMovement(level);
            validGhostMovement(level);
          }
          moveQueue.wait(WAIT_TIME);
        }
      } catch (InterruptedException exception) {
        Thread.currentThread().interrupt();
        WarningDialog.display("Interrupt error in" + this.getName(), exception);
      }
    }

    System.out.println("STOP - " + this.getName());
  }


  private void validPacgumConsumedEvent(Level level) {
    PacMan pacman = level.getPacMan();
    Position position = pacman.getPosition();
    if (level.isPacgum(position)) {
      addEventToQueue(pacman, GameEvent.PACGUM_CONSUMED, position);
    }
  }

  private void validSuperPacgumConsumedEvent(Level level) {
    PacMan pacman = level.getPacMan();
    Position position = pacman.getPosition();
    if (level.isSuperPacgum(position)) {
      addEventToQueue(pacman, GameEvent.SUPER_PACGUM_CONSUMED, position);
    }
  }

  private void validPacmanGhostsCollisionEvent(Level level) {
    PacmanGhostCollisionManager pacmanGhostCollisionManager =
        new PacmanGhostCollisionManager(level, level);
    if (pacmanGhostCollisionManager.isCollision()) {
      PacMan pacman = level.getPacMan();
      addEventToQueue(pacman, GameEvent.PACMAN_GHOST_COLLISON, pacman.getPosition());
    }
  }

  private void validPacmanMovement(Level level) {
    PacmanMoveValidator moveValidator = new PacmanMoveValidator(level);
    PacMan pacman = level.getPacMan();
    MovementManager movementManager = new MovementManager(pacman, moveValidator);
    movementManager.setDirection(pacman.getDesiredDirection());
    addEventToQueue(pacman, GameEvent.ENTITY_MOVE, movementManager.getPosition());
  }

  private void validGhostMovement(Level level) {
    GhostMoveValidator moveValidator = new GhostMoveValidator(level);
    for (Ghost ghost : level.getGhosts()) {
      MovementManager movementManager = new MovementManager(ghost, moveValidator);
      movementManager.setDirection(ghost.getDesiredDirection());
      addEventToQueue(ghost, GameEvent.ENTITY_MOVE, movementManager.getPosition());
    }
  }

  private void addEventToQueue(IGameObject gameObject, GameEvent gameEvent, Position position) {
    GameEventObject gameEventObject = new GameEventObject(gameObject, gameEvent, position);
    synchronized (eventQueue) {
      eventQueue.add(gameEventObject);
    }
  }
}
