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
import ca.usherbrooke.pacman.model.direction.BlinkyDirectionGenerator;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.direction.IDirectionGenerator;
import ca.usherbrooke.pacman.model.direction.PinkyDirectionGenerator;
import ca.usherbrooke.pacman.model.direction.RandomDirectionGenerator;
import ca.usherbrooke.pacman.model.direction.ghostsdirectionmanagers.PeriodicGhostDirectionManager;
import ca.usherbrooke.pacman.model.events.GameEvent;
import ca.usherbrooke.pacman.model.events.GameEventObject;
import ca.usherbrooke.pacman.model.highscores.HighScores;
import ca.usherbrooke.pacman.model.objects.Ghost;
import ca.usherbrooke.pacman.model.objects.GhostName;
import ca.usherbrooke.pacman.model.objects.IGameObject;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.objects.PacMan;
import ca.usherbrooke.pacman.model.position.Position;
import ca.usherbrooke.pacman.model.sound.Observer;
import ca.usherbrooke.pacman.threads.PhysicsThread;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class GameModel implements IGameModel {
  private static final int IS_LEVEL_COMPLETED_PERIOD = 20;
  private static final int GHOSTS_DIRECTION_CHANGE_PERIOD = 14;
  private static final int RANDOM_GENERATOR_SEED = 8544574;
  private static final int JOIN_TIMER = 1000; // ms
  private static final int INITIAL_SCORE = 0;
  private static final int INITIAL_NUMBER_OF_LIVES = 3;
  private static final int NUMBER_OF_LEVEL = 5;
  private static final String HIGH_SCORES_PATH = "Highscores.json";
  private static final int BASE_GHOST_KILL_POINTS = 200;
  private static final int EXTRA_LIVE_SCORE = 10000;

  // The position was hard-coded because in the teacher's specifications it
  // says that they must spawn in the room and not leave as long as pacman is
  // invincible. We can't simply respawn the ghosts at their starting
  // position because the red ghost is initially outside of the ghost room.
  private static final Position INITIAL_GHOST_POSITION = new Position(13, 15);

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
  private boolean hasReceivedAnExtraLive;
  private boolean isPacmanPreviousStateInvincible = false;
  private boolean isHighScoreSaved = false;
  private PacmanGhostCollisionManager pacmanGhostCollisionManager;
  private GameState gameState = GameState.GAME_MENU;
  private PacMan pacman;
  private PacmanPacgumCollisionManager pacmanPacgumCollisionManager;
  private PacmanSuperPacgumCollisionManager pacmanSuperPacgumCollisionManager;
  private List<Observer> observers = new ArrayList<>();
  private HighScores highScores = new HighScores();
  Random randomNumberGenerator = new Random(RANDOM_GENERATOR_SEED);
  IDirectionGenerator randomDirectionGenerator =
      new RandomDirectionGenerator(randomNumberGenerator);
  IDirectionGenerator blinkyDirectionGenerator;
  IDirectionGenerator pinkyDirectionGenerator;
  private List<PeriodicGhostDirectionManager> ghostDirectionManagers = new ArrayList<>();
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
    if (score >= EXTRA_LIVE_SCORE && !hasReceivedAnExtraLive) {
      lives++;
      hasReceivedAnExtraLive = true;
    }
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
  public void startGame() {
    for (Observer observer : observers) {
      observer.startGame();
    }
  }

  @Override
  public void startInvincibleMusic() {
    for (Observer observer : observers) {
      observer.startInvincibleMusic();
    }
  }

  @Override
  public void startBackgroundMusic() {
    for (Observer observer : observers) {
      observer.startBackgroundMusic();
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
  public void pacmanKilled() {
    for (Observer observer : observers) {
      observer.pacmanKilled();
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
      if ((isGameCompleted() || isGameOver()) && highScores.isHighScore(this.getScore())
          && !isHighScoreSaved) {
        gameState = GameState.NEW_HIGHSCORE;
        isHighScoreSaved = true;
      }
      onInterruption();
      return;
    }

    ++currentGameFrame;
    if (isPacmanDead) {
      return;
    }
    Level currentLevel = getCurrentLevel();
    if (currentLevel.isCompleted()) {
      isLevelCompleted = true;
      onInterruption();
      updateIsLevelCompleted();
      return;
    }

    processAllPhysicsEvents();

    synchronized (moveQueue) {
      moveQueue.add(currentLevel);
      moveQueue.notifyAll();
    }

    updateGameObjectsPosition();

    if (currentLevel.getPacMan().isInvincible() && !isPacmanPreviousStateInvincible) {
      isPacmanPreviousStateInvincible = true;
      startInvincibleMusic();
    } else if (!currentLevel.getPacMan().isInvincible() && isPacmanPreviousStateInvincible) {
      isPacmanPreviousStateInvincible = false;
      startBackgroundMusic();
    }

  }

  private void processAllPhysicsEvents() {
    while (!eventQueue.isEmpty()) {
      GameEventObject gameEventObject = eventQueue.poll();
      processPhysicEvent(gameEventObject);
    }
    pacmanSuperPacgumCollisionManager.updateIsPacManInvincible();
  }

  private void processPhysicEvent(GameEventObject gameEventObject) {
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
      if (gameEventObject.getGameObject().getClass() == PacMan.class) {
        processPacmanKilled();
      } else {
        processGhostKilled((Ghost) (gameEventObject.getGameObject()));
      }
    }
    if (gameEventObject.getGameEvent() == GameEvent.ENTITY_MOVE) {
      IGameObject gameObject = gameEventObject.getGameObject();
      gameObject.setPosition(gameEventObject.getPosition());
    }
  }

  @Override
  public void processGhostKilled(Ghost ghost) {
    ghost.setPosition(new Position(INITIAL_GHOST_POSITION));
    setScore(getScore() + getScoreForGhostKill());
    pacman.setGhostKillsSinceInvincible(pacman.getGhostKillsSinceInvincible() + 1);
    consumingGhost();
    eventQueue.clear();
  }

  private int getScoreForGhostKill() {
    final int ghostKillsSinceInvincible = pacman.getGhostKillsSinceInvincible();
    final int multikillScoreMultiplier = 1 << ghostKillsSinceInvincible;
    return BASE_GHOST_KILL_POINTS * multikillScoreMultiplier;
  }

  private void processPacmanKilled() {
    setIsPacmanDead(true);
    pacmanKilled();
    eventQueue.clear();
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
    for (PeriodicGhostDirectionManager ghostDirectionManager : ghostDirectionManagers) {
      ghostDirectionManager.update();
    }
  }

  @Override
  public void initializeGame() {
    initializeLevel();
    highScores = HighScores.loadHighScores(HIGH_SCORES_PATH);
    isGameOver = false;
  }

  @Override
  public void initializeLevel() {
    final boolean isLevelCompleted = getCurrentLevel().isCompleted();
    List<List<Integer>> levelMapBeforeInitializing = getCurrentLevel().getMap();
    setCurrentLevel(new Level(getInitialLevel()));
    if (!isLevelCompleted && !isGameOver()) {
      getCurrentLevel().setMap(levelMapBeforeInitializing);
    }
    pacman = level.getPacMan();
    initializeGhostsDirectionManagers();
    initializeCollisionManagers();
    isPacmanDead = false;
  }

  private void initializeGhostsDirectionManagers() {
    try {
      blinkyDirectionGenerator = new BlinkyDirectionGenerator(randomDirectionGenerator,
          level.getGhostByName(GhostName.BLINKY), level);
      pinkyDirectionGenerator = new PinkyDirectionGenerator(randomDirectionGenerator,
          level.getGhostByName(GhostName.PINKY), level);

      ghostDirectionManagers.add(new PeriodicGhostDirectionManager(this, blinkyDirectionGenerator,
          level.getGhostByName(GhostName.BLINKY), GHOSTS_DIRECTION_CHANGE_PERIOD));
      ghostDirectionManagers.add(new PeriodicGhostDirectionManager(this, randomDirectionGenerator,
          level.getGhostByName(GhostName.INKY), GHOSTS_DIRECTION_CHANGE_PERIOD));
      ghostDirectionManagers.add(new PeriodicGhostDirectionManager(this, pinkyDirectionGenerator,
          level.getGhostByName(GhostName.PINKY), GHOSTS_DIRECTION_CHANGE_PERIOD));
      ghostDirectionManagers.add(new PeriodicGhostDirectionManager(this, randomDirectionGenerator,
          level.getGhostByName(GhostName.CLYDE), GHOSTS_DIRECTION_CHANGE_PERIOD));
    } catch (Exception exception) {
      WarningDialog.display("Error getting a ghost. ", exception);
    }
  }

  private void initializeCollisionManagers() {
    Level currentLevel = getCurrentLevel();
    pacmanPacgumCollisionManager = new PacmanPacgumCollisionManager(currentLevel, this);
    pacmanSuperPacgumCollisionManager = new PacmanSuperPacgumCollisionManager(currentLevel, this);
    pacmanGhostCollisionManager = new PacmanGhostCollisionManager(currentLevel, this);
  }

  @Override
  public void startNewGame() {
    setCurrentLevelIndex(0);
    setScore(0);
    setLives(INITIAL_NUMBER_OF_LIVES);
    hasReceivedAnExtraLive = false;
    initializeGame();
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
    return level.getPacMan();
  }

  @Override
  public void setDirection(IGameObject gameObject, Direction direction) {
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

  @Override
  public void updatePacmanDeath() {
    setLives(getLives() - 1);
    initializeLevel();
  }

  @Override
  public HighScores getHighScores() {
    return highScores;
  }

  @Override
  public void setHighScores(HighScores highScores) {
    this.highScores = highScores;
  }

  public void setCurrentLevelIndex(int levelIndex) {
    currentLevelIndex = levelIndex;
  }
}
