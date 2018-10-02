package ca.usherbrooke.pacman.threads;


import java.util.Queue;
import ca.usherbrooke.pacman.model.GameEvent;
import ca.usherbrooke.pacman.model.Ghost;
import ca.usherbrooke.pacman.model.Level;
import ca.usherbrooke.pacman.model.Position;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class PhysicsThread extends Thread {
  private volatile boolean isRunning = false;

  private static final int SLEEP_TIME = 20;

  private final Queue<GameEvent> eventQueue;
  private final Queue<Level> moveQueue;

  public PhysicsThread(Queue<Level> moveQueue, Queue<GameEvent> eventQueue) {
    this.setName("Physic_Thread");
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
          }
        }
        Thread.sleep(SLEEP_TIME);

      } catch (InterruptedException exception) {
        Thread.currentThread().interrupt();

        WarningDialog.display("Interrupt error in" + this.getName(), exception);
      }
    }
    System.out.println("STOP - " + this.getName());
  }


  private void validPacgumConsumedEvent(Level level) {
    Position position = level.getPacMan().getPosition();
    if (level.isPacgum(position)) {
      eventQueue.add(GameEvent.PACGUM_CONSUMED);
    }
  }

  private void validSuperPacgumConsumedEvent(Level level) {
    Position position = level.getPacMan().getPosition();
    if (level.isSuperPacgum(position)) {
      eventQueue.add(GameEvent.SUPER_PACGUM_CONSUMED);
    }
  }

  private void validPacmanGhostsCollisionEvent(Level level) {
    Position pacmanPosition = level.getPacMan().getPosition();
    for (Ghost ghost : level.getGhosts()) {
      if (pacmanPosition.equals(ghost.getPosition())) {
        eventQueue.add(GameEvent.PACMAN_GHOST_COLLISON);
        return;
      }
    }
  }
}
