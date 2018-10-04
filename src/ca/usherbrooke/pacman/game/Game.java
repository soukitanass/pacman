package ca.usherbrooke.pacman.game;

import java.util.ArrayList;
import java.util.List;
import ca.usherbrooke.pacman.controller.IGameController;
import ca.usherbrooke.pacman.model.GameModel;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.threads.GameThread;

public class Game {
  private static GameThread gameThread;

  public static void main(String[] args) {
    final String LEVELS_PATH = "Levels.json";
    final int gameUpdatesPerSecond = 7;
    final int gameUpdatePeriodMilliseconds = (int) (1000.0 / gameUpdatesPerSecond);
    IGameModel model = new GameModel();
    List<IGameController> controllers = new ArrayList<>();
    model.loadLevels(LEVELS_PATH);
    gameThread = new GameThread(model, controllers, gameUpdatePeriodMilliseconds);
    gameThread.setName("Game_Thread");
    gameThread.start();
  }
}
