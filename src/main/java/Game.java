import controller.GameController;
import controller.IGameController;
import model.GameModel;
import model.IGameModel;
import view.GameView;
import view.IGameView;

public class Game implements IGame {
    private final long modelUpdatePeriod;
    private final long viewUpdatePeriod;
    private final long initialTime;
    private long lastModelUpdateTime;
    private long lastViewUpdateTime;
    private IGameModel model;
    private IGameView view;
    private IGameController controller;

    public Game(
            IGameModel model,
            IGameView view,
            IGameController controller,
            long modelUpdatePeriod,
            long viewUpdatePeriod,
            long initialTime
    ) {
        this.model = model;
        this.view = view;
        this.controller = controller;
        this.modelUpdatePeriod = modelUpdatePeriod;
        this.viewUpdatePeriod = viewUpdatePeriod;
        this.initialTime = initialTime;
        this.lastModelUpdateTime = this.initialTime;
        this.lastViewUpdateTime = this.initialTime;
    }

    public static void main(String[] args) {
        IGameModel model = new GameModel();
        IGameView view = new GameView(model);
        IGameController controller = new GameController(model, view);
        final int gameUpdatesPerSecond = 30;
        final int frameUpdatesPerSecond = 30;
        final int gameUpdatePeriodMilliseconds = (int) (1000.0 / gameUpdatesPerSecond);
        final int frameUpdatePeriodMilliseconds = (int) (1000.0 / frameUpdatesPerSecond);
        IGame game = new Game(
                model,
                view,
                controller,
                gameUpdatePeriodMilliseconds,
                frameUpdatePeriodMilliseconds,
                System.currentTimeMillis()
        );
        while (true) {
            game.update(System.currentTimeMillis());
        }
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
}
