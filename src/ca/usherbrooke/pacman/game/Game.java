/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import ca.usherbrooke.pacman.controller.IGameController;
import ca.usherbrooke.pacman.controller.PlayerKeyboardController;
import ca.usherbrooke.pacman.model.GameModel;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.threads.AudioThread;
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
    final String LEVEL_PATH = "Level.json";
    final int gameUpdatesPerSecond = 7;
    final int gameUpdatePeriodMilliseconds = (int) (1000.0 / gameUpdatesPerSecond);
    final IGameModel model = new GameModel(Game.loadLevel(LEVEL_PATH));
    final AudioThread audioThread = new AudioThread(model);
    audioThread.setName("Audio_Thread");
    audioThread.start();
    model.startNewGame();
    FpsOptionListener fpsOptionListener = new FpsOptionListener();
    IGameView view = new GameView(model, GHOST_SPRITE_TOGGLE_PERIOD, PACMAN_SPRITE_TOGGLE_PERIOD,
        audioThread, fpsOptionListener);
    view.addKeyListener(audioThread.getSoundController());
    view.addCloseObserver(audioThread);
    ITimeGetter timeGetter = new TimeGetter();
    RenderThread renderThread = new RenderThread(view, timeGetter);
    fpsOptionListener.setRenderThread(renderThread);
    view.addCloseObserver(renderThread);
    List<IGameController> controllers = new ArrayList<>();
    PlayerKeyboardController playerKeyboardController = new PlayerKeyboardController(model, view);
    controllers.add(playerKeyboardController);
    IGame game =
        new Game(model, controllers, gameUpdatePeriodMilliseconds, timeGetter.getMilliseconds());
    view.addKeyListener(playerKeyboardController);
    game.setRunning(true);
    Thread viewThread = new Thread(renderThread);
    viewThread.start();
    while (game.isRunning()) {
      game.update(timeGetter.getMilliseconds());
    }
    view.close();
    try {
      viewThread.join();
    } catch (InterruptedException e) {
      viewThread.interrupt();
      WarningDialog.display("An error occured when waiting for the view to stop", e);
    }
  }

  @Override
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

  public static Level loadLevel(String levelPath) {
    Level level = null;
    Gson gson = new Gson();
    File file = new File(GameModel.class.getClassLoader().getResource(levelPath).getFile());

    try (FileReader fileReader = new FileReader(file)) {
      level = gson.fromJson(new BufferedReader(fileReader), Level.class);
    } catch (Exception exception) {
      WarningDialog.display("Error while opening level file. ", exception);
    }
    return level;
  }

}
