import java.util.Timer;
import controller.GameController;
import controller.IGameController;
import controller.SoundController;
import model.GameModel;
import model.IGameModel;
import model.ISoundModel;
import model.SoundModel;
import view.GameView;
import view.IGameView;

public class Game implements IGame {
  private IGameModel model;

  public Game(IGameModel model) {
    this.model = model;
  }

  public static void main(String[] args) {
    final String LEVELS_PATH = "src\\main\\res\\Levels.json";
    IGameModel model = new GameModel();
    IGameView view = new GameView(model);
    IGameController controller = new GameController(model, view);
    final int frameUpdatesPerSecond = 30;
    IGame game = new Game(model);
    ISoundModel soundPlayer = new SoundModel();
    SoundController soundController = new SoundController(soundPlayer);
    model.loadLevels(LEVELS_PATH);
    view.addKeyListener(controller);
    view.addKeyListener(soundController);
    view.display();
    game.setRunning(true);

    Timer timer = new Timer();
    timer.schedule(new GameTimer(game, model, view), 0, frameUpdatesPerSecond);
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
