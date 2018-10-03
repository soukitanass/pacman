package ca.usherbrooke.pacman.game;

import java.util.ArrayList;
import java.util.List;
import ca.usherbrooke.pacman.controller.IGameController;
import ca.usherbrooke.pacman.controller.PlayerKeyboardController;
import ca.usherbrooke.pacman.model.GameModel;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.threads.AudioThread;
import ca.usherbrooke.pacman.threads.RenderThread;
import ca.usherbrooke.pacman.view.GameView;
import ca.usherbrooke.pacman.view.IGameView;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class Game implements IGame {
  private static final int GHOST_SPRITE_TOGGLE_PERIOD = 10;
  private static final int PACMAN_SPRITE_TOGGLE_PERIOD = 2;
  private static final int JOIN_TIMER = 1000;

  private final long modelUpdatePeriod;
  private long lastModelUpdateTime;
  private IGameModel model;
  private List<IGameController> controllers;
  private static AudioThread audioThread;


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
    final int viewUpdatesPerSecond = 30;
    final int gameUpdatePeriodMilliseconds = (int) (1000.0 / gameUpdatesPerSecond);
    final int viewUpdatePeriodMilliseconds = (int) (1000.0 / viewUpdatesPerSecond);
    IGameModel model = new GameModel();
    model.loadLevels(LEVELS_PATH);
    audioThread = new AudioThread(model);
    audioThread.setName("Audio_Thread");
    audioThread.start();
    IGameView view =
        new GameView(model, GHOST_SPRITE_TOGGLE_PERIOD, PACMAN_SPRITE_TOGGLE_PERIOD, audioThread);
    audioThread.addKeyListenner(view);
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
    Thread viewThread = new Thread(renderThread);
    viewThread.start();
    while (game.isRunning()) {
      game.update(System.currentTimeMillis());
    }
    view.close();
    audioThread.setStop();
    try {
      audioThread.join(JOIN_TIMER);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
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

  public static void stopAudioThread() {
    try {
      audioThread.setStop();
      audioThread.join(JOIN_TIMER);
      if (audioThread.isAlive()) {
        throw new InterruptedException();
      }
    } catch (InterruptedException exception) {
      audioThread.interrupt();
      WarningDialog.display("Error stoping audioThread. ", exception);
    }
  }
}
