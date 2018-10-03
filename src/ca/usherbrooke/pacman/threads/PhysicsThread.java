package ca.usherbrooke.pacman.threads;


import java.util.Queue;
import ca.usherbrooke.pacman.model.Direction;
import ca.usherbrooke.pacman.model.GameEvent;
import ca.usherbrooke.pacman.model.GameEventObject;
import ca.usherbrooke.pacman.model.Ghost;
import ca.usherbrooke.pacman.model.IGameObject;
import ca.usherbrooke.pacman.model.IMoveValidator;
import ca.usherbrooke.pacman.model.Level;
import ca.usherbrooke.pacman.model.MoveRequest;
import ca.usherbrooke.pacman.model.PacMan;
import ca.usherbrooke.pacman.model.PacmanMoveValidator;
import ca.usherbrooke.pacman.model.Position;
import ca.usherbrooke.pacman.model.exceptions.InvalidDirectionException;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class PhysicsThread extends Thread {
  private volatile boolean isRunning = false;

  private static final int SLEEP_TIME = 10;
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
            IMoveValidator moveValidator = new PacmanMoveValidator(level);
            updatePosition(level.getPacMan(), moveValidator);
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
    PacMan pacman = level.getPacMan();
    Position position = pacman.getPosition();
    if (level.isPacgum(position)) {   
      eventQueue.add(new GameEventObject(pacman, GameEvent.PACGUM_CONSUMED, position));
    }
  }

  private void validSuperPacgumConsumedEvent(Level level) {
    PacMan pacman = level.getPacMan();
    Position position = pacman.getPosition();
    if (level.isSuperPacgum(position)) {
      eventQueue.add(new GameEventObject(pacman, GameEvent.SUPER_PACGUM_CONSUMED, position));
    }
  }

  private void validPacmanGhostsCollisionEvent(Level level) {
    PacMan pacman = level.getPacMan();
    Position position = pacman.getPosition();
    for (Ghost ghost : level.getGhosts()) {
      if (position.equals(ghost.getPosition())) {
        eventQueue.add(new GameEventObject(pacman, GameEvent.PACMAN_GHOST_COLLISON, position));
        return;
      }
    }
  } 
  
  public void updatePosition(IGameObject gameObject, IMoveValidator moveValidator) {

    MoveRequest desiredMoveRequest =
        new MoveRequest(gameObject.getPosition(), gameObject.getDesiredDirection());
    try {
      if (moveValidator.isDesiredDirectionValid(desiredMoveRequest)
          && moveValidator.isValid(desiredMoveRequest)) {
        gameObject.setPosition(moveValidator.getTargetPosition(desiredMoveRequest));
        return;
      }
    } catch (InvalidDirectionException exception) {
     
    }

    MoveRequest fallbackMoveRequest =
        new MoveRequest(gameObject.getPosition(), gameObject.getDirection());
    try {
      if (moveValidator.isValid(fallbackMoveRequest)) {
        gameObject.setPosition(moveValidator.getTargetPosition(fallbackMoveRequest));
      }
    } catch (InvalidDirectionException exception) {

    }

    setDirection(gameObject, moveValidator);
  }

  public void setDirection(IGameObject gameObject, IMoveValidator moveValidator) {
    Direction direction = gameObject.getDesiredDirection();
    gameObject.setDesiredDirection(direction);
    MoveRequest moveRequest = new MoveRequest(gameObject.getPosition(), direction);
    try {
      if (!moveValidator.isDesiredDirectionValid(moveRequest)) {
        return;
      }
    } catch (InvalidDirectionException exception) {

    }
    gameObject.setDirection(direction);
  }
  
  private void createGameEvent() {
    
  }
}
