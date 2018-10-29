package ca.usherbrooke.pacman.threads;

import java.util.List;
import ca.usherbrooke.pacman.controller.IGameController;
import ca.usherbrooke.pacman.game.IGame;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.view.utilities.CloseObserver;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class GameThread extends Thread implements IGame, CloseObserver {

  private static final int GAME_UPDATES_PER_SECOND = 7;
  private static final long SLEEP_TIME = (int) (1000.0 / GAME_UPDATES_PER_SECOND);
  private static final long JOIN_TIME = 1500;

  private IGameModel model;
  private List<IGameController> controllers;
  private AudioThread audioThread;
  private Thread viewThread;

  public GameThread(IGameModel model, Thread viewThread, AudioThread audioThread,
      List<IGameController> controllers) {
    this.model = model;
    this.viewThread = viewThread;
    this.audioThread = audioThread;
    this.controllers = controllers;

    audioThread.start();
    viewThread.start();

    this.model.startNewGame();
  }

  @Override
  public void run() {
    System.out.println("START - " + this.getName());

    setRunning(true);
    while (isRunning()) {
      update();
      try {
        Thread.sleep(SLEEP_TIME);
      } catch (InterruptedException exception) {
        Thread.currentThread().interrupt();
        WarningDialog.display("Interrupt error in" + this.getName(), exception);
      }
    }
    stopViewThread();
    audioThread.stopThread();

    System.out.println("STOP - " + this.getName());
  }

  @Override
  public synchronized void update() {
    for (IGameController controller : controllers) {
      controller.update();
    }
    this.model.update();
  }

  @Override
  public synchronized boolean isRunning() {
    return model.isRunning();
  }

  @Override
  public synchronized void setRunning(boolean isRunning) {
    model.setRunning(isRunning);
  }

  @Override
  public synchronized void stopViewThread() {
    try {
      viewThread.join(JOIN_TIME);
    } catch (InterruptedException exception) {
      viewThread.interrupt();
      WarningDialog.display("Interrupt error in view thread", exception);
    }
  }

  @Override
  public void onClosingView() {
    setRunning(false);
  }

  @Override
  public void stopThread() {
    setRunning(false);
  }

}
