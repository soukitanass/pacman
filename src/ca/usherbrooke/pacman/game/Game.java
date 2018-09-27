package ca.usherbrooke.pacman.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ca.usherbrooke.pacman.controller.IGameController;
import ca.usherbrooke.pacman.controller.PeriodicDirectionController;
import ca.usherbrooke.pacman.controller.PlayerKeyboardController;
import ca.usherbrooke.pacman.controller.SoundController;
import ca.usherbrooke.pacman.model.GameModel;
import ca.usherbrooke.pacman.model.Ghost;
import ca.usherbrooke.pacman.model.IDirectionGenerator;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.random.RandomDirectionGenerator;
import ca.usherbrooke.pacman.model.sound.ISoundModel;
import ca.usherbrooke.pacman.model.sound.SoundModel;
import ca.usherbrooke.pacman.view.GameView;
import ca.usherbrooke.pacman.view.IGameView;

public class Game implements IGame {
  private static final int GHOSTS_DIRECTION_CHANGE_PERIOD = 3;

  private static final int RANDOM_GENERATOR_SEED = 8544574;

  private final long modelUpdatePeriod;
  private final long viewUpdatePeriod;
  private long lastModelUpdateTime;
  private long lastViewUpdateTime;
  private IGameModel model;
  private IGameView view;
  private List<IGameController> controllers;

  public Game(IGameModel model, IGameView view, List<IGameController> controllers,
      long modelUpdatePeriod, long viewUpdatePeriod, long initialTime) {
    this.model = model;
    this.view = view;
    this.controllers = controllers;
    this.modelUpdatePeriod = modelUpdatePeriod;
    this.viewUpdatePeriod = viewUpdatePeriod;
    this.lastModelUpdateTime = initialTime;
    this.lastViewUpdateTime = initialTime;
  }

  public static void main(String[] args) {
    final String LEVELS_PATH = "Levels.json";
    IGameModel model = new GameModel();
    model.loadLevels(LEVELS_PATH);
    IGameView view = new GameView(model);
    List<IGameController> controllers = new ArrayList<IGameController>();
    PlayerKeyboardController playerKeyboardController = new PlayerKeyboardController(model, view);
    Random randomNumberGenerator = new Random(RANDOM_GENERATOR_SEED);
    IDirectionGenerator randomDirectionGenerator = new RandomDirectionGenerator(randomNumberGenerator);
    controllers.add(playerKeyboardController);
    for (Ghost ghost : model.getCurrentLevel().getGhost()) {
      controllers.add(new PeriodicDirectionController(randomDirectionGenerator, ghost,
          GHOSTS_DIRECTION_CHANGE_PERIOD));
    }
    final int gameUpdatesPerSecond = 7;
    final int frameUpdatesPerSecond = 30;
    final int gameUpdatePeriodMilliseconds = (int) (1000.0 / gameUpdatesPerSecond);
    final int frameUpdatePeriodMilliseconds = (int) (1000.0 / frameUpdatesPerSecond);
    IGame game = new Game(model, view, controllers, gameUpdatePeriodMilliseconds,
        frameUpdatePeriodMilliseconds, System.currentTimeMillis());
    ISoundModel soundPlayer = new SoundModel(model);
    SoundController soundController = new SoundController(soundPlayer);
    view.addKeyListener(playerKeyboardController);
    view.addKeyListener(soundController);
    game.setRunning(true);
    while (game.isRunning()) {
      game.update(System.currentTimeMillis());
    }
    view.close();
  }

  public void update(long currentTime) {
    if (currentTime >= this.lastModelUpdateTime + this.modelUpdatePeriod) {
      this.lastModelUpdateTime = currentTime;
      for (IGameController controller : controllers) {
        controller.update();
      }
      this.model.update();
    }
    if (currentTime >= this.lastViewUpdateTime + this.viewUpdatePeriod) {
      this.lastViewUpdateTime = currentTime;
      this.view.update();
    }
  }

  @Override
  public boolean isRunning() {
    return model.isRunning();
  }

  @Override
  public void setRunning(boolean isRunning) {
    model.setRunning(isRunning);
  }
}
