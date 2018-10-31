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
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.threads.AudioThread;
import ca.usherbrooke.pacman.threads.GameThread;
import ca.usherbrooke.pacman.threads.RenderThread;
import ca.usherbrooke.pacman.view.GameView;
import ca.usherbrooke.pacman.view.panel.FpsOptionListener;
import ca.usherbrooke.pacman.view.utilities.WarningDialog;

public class Game {

  private static final String LEVEL_PATH = "Level.json";
  private static final int GHOST_SPRITE_TOGGLE_PERIOD = 10;
  private static final int PACMAN_SPRITE_TOGGLE_PERIOD = 2;
  private GameThread gameThread;
  private GameModel model;
  private GameView view;
  private AudioThread audioThread;
  private RenderThread renderThread;

  public static void main(String[] args) {
    Game game = new Game();
    game.initialize(LEVEL_PATH);
    game.start();
  }

  public void initialize(String levelPath) {
    FpsOptionListener fpsOptionListener = new FpsOptionListener();
    ITimeGetter timeGetter = new TimeGetter();
    List<IGameController> controllers = new ArrayList<>();

    model = new GameModel(Game.loadLevel(levelPath));
    final PlayerKeyboardController playerKeyboardController = new PlayerKeyboardController(model);
    audioThread = new AudioThread(model);
    view = new GameView(model, GHOST_SPRITE_TOGGLE_PERIOD, PACMAN_SPRITE_TOGGLE_PERIOD, audioThread,
        fpsOptionListener);
    renderThread = new RenderThread(view, timeGetter);
    final Thread viewThread = new Thread(renderThread);

    controllers.add(playerKeyboardController);

    view.addKeyListener(audioThread.getSoundController());
    view.addCloseObserver(audioThread);
    view.addCloseObserver(renderThread);
    view.addKeyListener(playerKeyboardController);

    fpsOptionListener.setRenderThread(renderThread);

    gameThread = new GameThread(model, viewThread, audioThread, view, controllers);
    gameThread.setName("Game thread");
  }

  public void start() {
    gameThread.start();
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

  public GameModel getModel() {
    return model;
  }

  public GameView getView() {
    return view;
  }

  public AudioThread getAudioThread() {
    return audioThread;
  }

  public RenderThread getRenderThread() {
    return renderThread;
  }

}
