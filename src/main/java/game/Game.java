package game;

import controller.GameController;
import controller.IGameController;
import controller.SoundController;
import model.GameModel;
import model.IGameModel;
import model.sound.ISoundModel;
import model.sound.SoundModel;
import view.GameView;
import view.IGameView;

public class Game implements IGame {
  private final long modelUpdatePeriod;
  private final long viewUpdatePeriod;
  private long lastModelUpdateTime;
  private long lastViewUpdateTime;
  private IGameModel model;
  private IGameView view;
  private IGameController controller;

  public Game(IGameModel model, IGameView view, IGameController controller, long modelUpdatePeriod,
      long viewUpdatePeriod, long initialTime) {
    this.model = model;
    this.view = view;
    this.controller = controller;
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
    IGameController controller = new GameController(model, view);
    final int gameUpdatesPerSecond = 7;
    final int frameUpdatesPerSecond = 30;
    final int gameUpdatePeriodMilliseconds = (int) (1000.0 / gameUpdatesPerSecond);
    final int frameUpdatePeriodMilliseconds = (int) (1000.0 / frameUpdatesPerSecond);
    IGame game = new Game(model, view, controller, gameUpdatePeriodMilliseconds,
        frameUpdatePeriodMilliseconds, System.currentTimeMillis());
    ISoundModel soundPlayer = new SoundModel(model);
    SoundController soundController = new SoundController(soundPlayer);
    view.addKeyListener(controller);
    view.addKeyListener(soundController);
    game.setRunning(true);
    while (game.isRunning()) {
      game.update(System.currentTimeMillis());
    }
    view.close();
  }

  public void update(long currentTime) {
    this.controller.update();
    if (currentTime >= this.lastModelUpdateTime + this.modelUpdatePeriod) {
      this.lastModelUpdateTime = currentTime;
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
