package ca.usherbrooke.pacman.threads;

import java.util.List;
import ca.usherbrooke.pacman.controller.IGameController;
import ca.usherbrooke.pacman.game.IGame;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.view.IGameView;
import ca.usherbrooke.pacman.view.utilities.CloseObserver;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class GameThread extends Thread implements IGame, CloseObserver {
  private static final String NAME = "Game_Thread";
  private static final int GAME_UPDATES_PER_SECOND = 7;
  private static final long SLEEP_TIME = (int) (1000.0 / GAME_UPDATES_PER_SECOND);
  private static final long JOIN_TIME = 2500;

  private AudioThread audioThread;
  private RenderThread renderThread;
  private IGameModel model;
  private List<IGameController> controllers;
  private IGameView view;

  public GameThread(IGameModel model, RenderThread renderThread, AudioThread audioThread,
      IGameView view, List<IGameController> controllers) {
    this.setName(NAME);
    this.model = model;
    this.renderThread = renderThread;
    this.view = view;
    this.audioThread = audioThread;
    this.controllers = controllers;

    audioThread.start();
    renderThread.start();

    this.model.startNewGame();
  }

  @Override
  public void run() {
    setRunning(true);
    System.out.println("START - " + this.getName());

    while (isRunning()) {
      update();
      try {
        Thread.sleep(SLEEP_TIME);
      } catch (InterruptedException exception) {
        Thread.currentThread().interrupt();
        WarningDialog.display("Interrupt error in" + this.getName(), exception);
      }
    }
    stopRenderThread();
    stopAudioThread();

    System.out.println("STOP - " + this.getName());
  }

  @Override
  public synchronized void update() {
    for (IGameController controller : controllers) {
      controller.update();
    }
    model.update();
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
  public synchronized void stopRenderThread() {
    if (renderThread == null) {
      return;
    }

    view.close();
    try {
      renderThread.stopThread();
      renderThread.join(JOIN_TIME);
      if (renderThread.isAlive()) {
        throw new InterruptedException();
      }
    } catch (InterruptedException exception) {
      renderThread.interrupt();
      WarningDialog.display("Error stoping renderThread. ", exception);
    }
  }

  @Override
  public synchronized void stopAudioThread() {
    if (audioThread == null) {
      return;
    }

    try {
      audioThread.stopThread();
      audioThread.join(JOIN_TIME);
      if (audioThread.isAlive()) {
        throw new InterruptedException();
      }
    } catch (InterruptedException exception) {
      audioThread.interrupt();
      WarningDialog.display("Error stoping audioThread. ", exception);
    }
  }

  @Override
  public synchronized void onClosingView() {
    stopThread();
  }

  @Override
  public void stopThread() {
    setRunning(false);
  }

}
