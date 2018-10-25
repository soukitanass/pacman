/*******************************************************************************
 * Team agilea18b, Pacman
 *
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import ca.usherbrooke.pacman.model.collision.PacmanGhostCollisionManager;
import ca.usherbrooke.pacman.model.collision.PacmanPacgumCollisionManager;
import ca.usherbrooke.pacman.model.collision.PacmanSuperPacgumCollisionManager;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.direction.IDirectionGenerator;
import ca.usherbrooke.pacman.model.direction.IHasDesiredDirection;
import ca.usherbrooke.pacman.model.direction.PeriodicDirectionManager;
import ca.usherbrooke.pacman.model.direction.RandomDirectionGenerator;
import ca.usherbrooke.pacman.model.events.GameEvent;
import ca.usherbrooke.pacman.model.events.GameEventObject;
import ca.usherbrooke.pacman.model.objects.Ghost;
import ca.usherbrooke.pacman.model.objects.IGameObject;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.objects.PacMan;
import ca.usherbrooke.pacman.model.sound.Observer;
import ca.usherbrooke.pacman.threads.PhysicsThread;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class GameModel implements IGameModel {
  private static final int IS_LEVEL_COMPLETED_PERIOD = 20;
  private static final int GHOSTS_DIRECTION_CHANGE_PERIOD = 3;
  private static final int RANDOM_GENERATOR_SEED = 8544574;
  private static final int JOIN_TIMER = 1000; // ms
  private static final int INITIAL_SCORE = 0;
  private static final int INITIAL_NUMBER_OF_LIVES = 3;
  private static final int NUMBER_OF_LEVEL = 5;

  private Level level;
  private final Level initialLevel;
  private int currentGameFrame = 0;
  private boolean isManuallyPaused = false;
  private boolean isPaused = false;
  private boolean isRunning = false;
  private boolean isLevelCompleted = false;
  private boolean isGameOver = false;
  private boolean isPacmanDead = false;
  private boolean isGameCompleted = false;
  private PacmanGhostCollisionManager pacmanGhostCollisionManager;
  private GameState gameState = GameState.GAME_MENU;
  private PacMan pacman;
  private PacmanPacgumCollisionManager pacmanPacgumCollisionManager;
  private PacmanSuperPacgumCollisionManager pacmanSuperPacgumCollisionManager;
  private List<Observer> observers = new ArrayList<>();
  Random randomNumberGenerator = new Random(RANDOM_GENERATOR_SEED);
  IDirectionGenerator randomDirectionGenerator =
      new RandomDirectionGenerator(randomNumberGenerator);
  private List<PeriodicDirectionManager> ghostDirectionManagers = new ArrayList<>();
  private int isLevelCompletedUpdatesCounter = 0;
  private Queue<Level> moveQueue = new ConcurrentLinkedQueue<>(); // Thread Safe
  private Queue<GameEventObject> eventQueue = new ConcurrentLinkedQueue<>(); // Thread Safe
  private PhysicsThread physicsThread = new PhysicsThread(moveQueue, eventQueue, this);
  private Integer score = INITIAL_SCORE;
  private int lives = INITIAL_NUMBER_OF_LIVES;
  private int currentLevelIndex = 0;

  @Override
  public Integer getScore() {
    return score;
  }

  @Override
  public void setScore(Integer score) {
    this.score = score;
  }

  @Override
  public int getLives() {
    return lives;
  }

  @Override
  public void setLives(int lives) {
    this.lives = lives;
    if (getLives() == 0) {
      setGameOver();
    }
  }

  public GameModel(Level initialLevel) {
    this.initialLevel = initialLevel;
    this.level = new Level(initialLevel);
    physicsThread.start();
  }

  public GameModel() {
    physicsThread.start();
    initialLevel = new Level();
  }

  @Override
  public void attach(Observer observer) {
    observers.add(observer);
  }

  @Override
  public void onInterruption() {
    for (Observer observer : observers) {
      observer.onGameInterruption();
    }
  }

  @Override
  public void consumingPacGums() {
    for (Observer observer : observers) {
      observer.consumingPacGums();
    }
  }

  @Override
  public void consumingGhost() {
    for (Observer observer : observers) {
      observer.consumingGhost();
    }
  }

  @Override
  public void movingToEmptySpace() {
    for (Observer observer : observers) {
      observer.movingToEmptySpace();
    }
  }

  @Override
  public void update() {
    boolean isGameInProgress =
        !isPaused() && !isGameCompleted() && !isGameOver() && gameState == GameState.GAME;
    if (!isGameInProgress) {
      onInterruption();
      return;
    }
    if (isPacmanDead) {
      return;
    }
    Level level = getCurrentLevel();
    if (level.isCompleted()) {
      isLevelCompleted = true;
      onInterruption();
      updateIsLevelCompleted();
      return;
    }
    ++currentGameFrame;

    processPhysicsEvent();

    synchronized (moveQueue) {
      moveQueue.add(level);
      moveQueue.notifyAll();
    }

    updateGameObjectsPosition();
  }

  private void processPhysicsEvent() {
    while (!eventQueue.isEmpty()) {
      GameEventObject gameEventObject = eventQueue.poll();
      if (gameEventObject.getGameEvent() == GameEvent.PACGUM_CONSUMED) {
        pacmanPacgumCollisionManager.update();
        consumingPacGums();
        eventQueue.clear();
      } else {
        movingToEmptySpace();
      }
      if (gameEventObject.getGameEvent() == GameEvent.SUPER_PACGUM_CONSUMED) {
        pacmanSuperPacgumCollisionManager.update();
      }
      if (gameEventObject.getGameEvent() == GameEvent.PACMAN_GHOST_COLLISON) {
        setIsPacmanDead(true);
        consumingGhost();
        eventQueue.clear();
      }
      if (gameEventObject.getGameEvent() == GameEvent.ENTITY_MOVE) {
        IGameObject gameObject = gameEventObject.getGameObject();
        gameObject.setPosition(gameEventObject.getPosition());
      }
    }
    pacmanSuperPacgumCollisionManager.updateIsPacManInvincible();
  }

  @Override
  public boolean isPacmanDead() {
    return isPacmanDead;
  }

  @Override
  public void setIsPacmanDead(boolean isPacmanDead) {
    this.isPacmanDead = isPacmanDead;
    if (!isPacmanDead) {
      pacmanGhostCollisionManager.update();
    }
  }

  private void goToNextLevel() {
    isLevelCompleted = false;
    incrementCurrentLevel();
    initializeLevel();
  }

  public void incrementCurrentLevel() {
    currentLevelIndex++;
    if (currentLevelIndex == NUMBER_OF_LEVEL) {
      isGameCompleted = true;
    }
  }

  private void updateIsLevelCompleted() {
    ++isLevelCompletedUpdatesCounter;
    if (isLevelCompletedUpdatesCounter != IS_LEVEL_COMPLETED_PERIOD) {
      return;
    }
    isLevelCompletedUpdatesCounter = 0;
    goToNextLevel();
  }

  private void updateGameObjectsPosition() {
    for (PeriodicDirectionManager ghostDirectionManager : ghostDirectionManagers) {
      ghostDirectionManager.update();
    }
  }

  private void initializeLevel() {
    this.level = new Level(getInitialLevel());
    pacman = level.getPacMan();
    for (Ghost ghost : level.getGhosts()) {
      ghostDirectionManagers.add(new PeriodicDirectionManager(this, randomDirectionGenerator, ghost,
          GHOSTS_DIRECTION_CHANGE_PERIOD));
    }

    pacmanPacgumCollisionManager = new PacmanPacgumCollisionManager(level, this);
    pacmanSuperPacgumCollisionManager = new PacmanSuperPacgumCollisionManager(level, this);
    pacmanGhostCollisionManager = new PacmanGhostCollisionManager(level, initialLevel, this);

    isGameOver = false;
    isPacmanDead = false;
  }

  @Override
  public void startNewGame() {
    setScore(0);
    setLives(INITIAL_NUMBER_OF_LIVES);
    initializeLevel();
  }

  @Override
  public int getCurrentGameFrame() {
    return currentGameFrame;
  }

  @Override
  public void pause() {
    isPaused = true;
  }

  @Override
  public void unpause() {
    isPaused = false;
  }

  @Override
  public int getCurrentLevelIndex() {
    return currentLevelIndex;
  }

  @Override
  public boolean isLevelCompleted() {
    return isLevelCompleted;
  }

  @Override
  public boolean isPaused() {
    return isPaused;
  }

  @Override
  public void togglePause(boolean isManuallyPaused) {

    if (isManuallyPaused) {
      setManuallyPaused(!isManuallyPaused());
    }

    if (isPaused()) {
      unpause();
    } else {
      pause();
    }
  }

  @Override
  public void quit() {
    stopPhysicsThread();
    setRunning(false);
  }

  @Override
  public boolean isRunning() {
    return isRunning;
  }

  @Override
  public void setRunning(boolean isRunning) {
    this.isRunning = isRunning;
  }

  @Override
  public Level getCurrentLevel() {
    return level;
  }

  @Override
  public PacMan getPacman() {
    return pacman;
  }

  @Override
  public void setDirection(IHasDesiredDirection gameObject, Direction direction) {
    if (isPaused()) {
      return;
    }
    gameObject.setDesiredDirection(direction);
  }

  @Override
  public void setManuallyPaused(boolean isManuallyPaused) {
    this.isManuallyPaused = isManuallyPaused;
  }

  @Override
  public boolean isManuallyPaused() {
    return isManuallyPaused;
  }

  @Override
  public boolean isGameOver() {
    return isGameOver;
  }

  @Override
  public void setGameOver() {
    isGameOver = true;
  }

  @Override
  public boolean isGameCompleted() {
    return isGameCompleted;
  }

  @Override
  public void stopPhysicsThread() {
    if (physicsThread == null) {
      return;
    }

    try {
      physicsThread.stopThread();
      physicsThread.join(JOIN_TIMER);
      if (physicsThread.isAlive()) {
        throw new InterruptedException();
      }
    } catch (InterruptedException exception) {
      physicsThread.interrupt();
      WarningDialog.display("Error stoping physicsThread. ", exception);
    }
  }

  @Override
  public GameState getGameState() {
    return this.gameState;
  }

  @Override
  public void setGameState(GameState gameState) {
    this.gameState = gameState;
  }

  @Override
  public Level getInitialLevel() {
    return initialLevel;
  }

  @Override
  public void setCurrentLevel(Level level) {
    this.level = level;
  }

}
