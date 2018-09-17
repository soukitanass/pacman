import java.util.TimerTask;
import model.IGameModel;
import view.IGameView;

public class GameTimer extends TimerTask {

  private int NUMBER_OF_VIEW_SPRITES = 6;
  private IGame game;
  private IGameView view;
  private IGameModel model;
  private int i = 0;

  public GameTimer(IGame game, IGameModel model, IGameView view) {
    this.game = game;
    this.model = model;
    this.view = view;
  }

  @Override
  public void run() {
    if (!game.isRunning()) {
      view.close();
      return;
    }

    view.update();
    if (i == NUMBER_OF_VIEW_SPRITES) {
      model.update();
      i = 0;
    }
    i++;
  }

}
