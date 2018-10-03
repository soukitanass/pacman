package ca.usherbrooke.pacman.game;

import java.util.ArrayList;
import java.util.List;
import ca.usherbrooke.pacman.controller.IGameController;
import ca.usherbrooke.pacman.controller.PlayerKeyboardController;
import ca.usherbrooke.pacman.model.GameModel;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.threads.AudioThread;
import ca.usherbrooke.pacman.view.GameView;
import ca.usherbrooke.pacman.view.IGameView;

public class Game implements IGame {

  private static final int GHOST_SPRITE_TOGGLE_PERIOD = 10;
  private static final int PACMAN_SPRITE_TOGGLE_PERIOD = 2;
  private static final int JOIN_TIMER = 1000;

  private final long modelUpdatePeriod;
  private final long viewUpdatePeriod;
  private long lastModelUpdateTime;
  private long lastViewUpdateTime;
  private IGameModel model;
  private IGameView view;
  private List<IGameController> controllers;
  private static AudioThread audioThread;


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
    audioThread = new AudioThread(model);
    audioThread.setName("Audio_Thread");
    audioThread.start();
    IGameView view =
        new GameView(model, GHOST_SPRITE_TOGGLE_PERIOD, PACMAN_SPRITE_TOGGLE_PERIOD, audioThread);
    audioThread.addKeyListenner(view);
    audioThread.addCloseListenner(view);
    List<IGameController> controllers = new ArrayList<>();
    PlayerKeyboardController playerKeyboardController = new PlayerKeyboardController(model, view);
    controllers.add(playerKeyboardController);



    final int gameUpdatesPerSecond = 7;
    final int frameUpdatesPerSecond = 30;
    final int gameUpdatePeriodMilliseconds = (int) (1000.0 / gameUpdatesPerSecond);
    final int frameUpdatePeriodMilliseconds = (int) (1000.0 / frameUpdatesPerSecond);
    IGame game = new Game(model, view, controllers, gameUpdatePeriodMilliseconds,
        frameUpdatePeriodMilliseconds, System.currentTimeMillis());

    view.addKeyListener(playerKeyboardController);
    game.setRunning(true);

    while (game.isRunning()) {
      game.update(System.currentTimeMillis());
    }
    view.close();
    try {
      audioThread.join(JOIN_TIMER);
    } catch (InterruptedException e) {
      e.printStackTrace();
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
