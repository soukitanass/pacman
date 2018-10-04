package ca.usherbrooke.pacman.threads;

import java.util.List;
import ca.usherbrooke.pacman.controller.IGameController;
import ca.usherbrooke.pacman.controller.PlayerKeyboardController;
import ca.usherbrooke.pacman.controller.SoundController;
import ca.usherbrooke.pacman.game.IGame;
import ca.usherbrooke.pacman.game.ITimeGetter;
import ca.usherbrooke.pacman.game.TimeGetter;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.sound.ISoundModel;
import ca.usherbrooke.pacman.model.sound.SoundModel;
import ca.usherbrooke.pacman.view.CloseObserver;
import ca.usherbrooke.pacman.view.GameView;
import ca.usherbrooke.pacman.view.IGameView;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class GameThread extends Thread implements IGame, CloseObserver {

  private static final int GHOST_SPRITE_TOGGLE_PERIOD = 10;
  private static final int PACMAN_SPRITE_TOGGLE_PERIOD = 2;
  private static final long THREAD_SLEEP = 2;

  private long modelUpdatePeriod;
  private IGameModel model;
  private  List<IGameController> controllers;

  private final int viewUpdatesPerSecond = 30;
  private final int viewUpdatePeriodMilliseconds = (int) (1000.0 / viewUpdatesPerSecond);
  private IGameView view;
  private RenderThread renderThread;
  private Thread viewThread;

  public GameThread(IGameModel model, List<IGameController> controllers, long modelUpdatePeriod) {
    this.model = model;
    this.controllers = controllers;
    this.modelUpdatePeriod = modelUpdatePeriod;
    view = new GameView(this.model, GHOST_SPRITE_TOGGLE_PERIOD, PACMAN_SPRITE_TOGGLE_PERIOD);
    ITimeGetter timeGetter = new TimeGetter();
    renderThread = new RenderThread(view, viewUpdatePeriodMilliseconds, timeGetter);
    view.addCloseObserver(renderThread);
    PlayerKeyboardController playerKeyboardController = new PlayerKeyboardController(model, view);
    this.controllers.add(playerKeyboardController);

    ISoundModel soundPlayer = new SoundModel(model);
    SoundController soundController = new SoundController(soundPlayer);
    view.addKeyListener(playerKeyboardController);
    view.addKeyListener(soundController);

    Thread viewThread = new Thread(renderThread);
    viewThread.start();

    view.addCloseObserver(this);
  }


  public void run() {
    System.out.println("START - " + this.getName());
    setRunning(true);
    while (isRunning()) {
      update();
      try {
        Thread.sleep(THREAD_SLEEP + modelUpdatePeriod);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    closeGame();
    System.out.println("STOP - " + this.getName());
  }

  @Override
  public void update() {
    for (IGameController controller : controllers) {
      controller.update();
    }
    this.model.update();
  }


  @Override
  public boolean isRunning() {
    return model.isRunning();
  }

  @Override
  public void setRunning(boolean isRunning) {
    model.setRunning(isRunning);
  }

  public void closeGame() {
    view.close();
    if (viewThread != null) {
      try {
        viewThread.join();
      } catch (InterruptedException e) {
        viewThread.interrupt();
        WarningDialog.display("An error occured when waiting for the view to stop", e);
      }
    }

  }

  @Override
  public void onClosingView() {
    setRunning(false);
  }
  
  public void stopThread() {
    setRunning(false);
  }

}

