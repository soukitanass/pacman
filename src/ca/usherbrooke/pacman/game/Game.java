package ca.usherbrooke.pacman.game;

import java.util.ArrayList;
import java.util.List;
import ca.usherbrooke.pacman.controller.IGameController;
import ca.usherbrooke.pacman.controller.PlayerKeyboardController;
import ca.usherbrooke.pacman.controller.SoundController;
import ca.usherbrooke.pacman.model.GameModel;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.sound.ISoundModel;
import ca.usherbrooke.pacman.model.sound.SoundModel;
import ca.usherbrooke.pacman.threads.RenderThread;
import ca.usherbrooke.pacman.view.GameView;
import ca.usherbrooke.pacman.view.IGameView;
import ca.usherbrooke.pacman.view.panel.FpsOptionListener;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class Game implements IGame {
  private static final int GHOST_SPRITE_TOGGLE_PERIOD = 4;
  private static final int PACMAN_SPRITE_TOGGLE_PERIOD = 1;

  private final long modelUpdatePeriod;
  private long lastModelUpdateTime;
  private IGameModel model;
  private List<IGameController> controllers;


  public Game(IGameModel model, List<IGameController> controllers, long modelUpdatePeriod,
      long initialTime) {
    this.model = model;
    this.controllers = controllers;
    this.modelUpdatePeriod = modelUpdatePeriod;
    this.lastModelUpdateTime = initialTime;
  }

  public static void main(String[] args) {
    final String LEVELS_PATH = "Levels.json";
    final int gameUpdatesPerSecond = 7;
    final int viewUpdatesPerSecond = 20;
    final int gameUpdatePeriodMilliseconds = (int) (1000.0 / gameUpdatesPerSecond);
    final int viewUpdatePeriodMilliseconds = (int) (1000.0 / viewUpdatesPerSecond);
    IGameModel model = new GameModel();
    model.loadLevels(LEVELS_PATH);
    FpsOptionListener fpsOptionListener = new FpsOptionListener();
    IGameView view = new GameView(model, GHOST_SPRITE_TOGGLE_PERIOD, PACMAN_SPRITE_TOGGLE_PERIOD,
        fpsOptionListener);
    ITimeGetter timeGetter = new TimeGetter();
    RenderThread renderThread = new RenderThread(view, viewUpdatePeriodMilliseconds, timeGetter);
    fpsOptionListener.setRenderThread(renderThread);
    view.addCloseObserver(renderThread);
    List<IGameController> controllers = new ArrayList<>();
    PlayerKeyboardController playerKeyboardController = new PlayerKeyboardController(model, view);
    controllers.add(playerKeyboardController);
    IGame game =
        new Game(model, controllers, gameUpdatePeriodMilliseconds, System.currentTimeMillis());
    ISoundModel soundPlayer = new SoundModel(model);
    SoundController soundController = new SoundController(soundPlayer);
    view.addKeyListener(playerKeyboardController);
    view.addKeyListener(soundController);
    game.setRunning(true);
    Thread viewThread = new Thread(renderThread);
    viewThread.start();
    while (game.isRunning()) {
      game.update(System.currentTimeMillis());
    }
    view.close();
    try {
      viewThread.join();
    } catch (InterruptedException e) {
      viewThread.interrupt();
      WarningDialog.display("An error occured when waiting for the view to stop", e);
    }
  }

  public void update(long currentTime) {
    if (currentTime >= this.lastModelUpdateTime + this.modelUpdatePeriod) {
      this.lastModelUpdateTime = currentTime;
      for (IGameController controller : controllers) {
        controller.update();
      }
      this.model.update();
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
