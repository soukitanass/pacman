package ca.usherbrooke.pacman.game;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.controller.IGameController;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.view.IGameView;

public class MainLoopTest {
  private IGame game;
  private IGameModel mockModel;
  private IGameView mockView;
  private IGameController mockController;

  @Before
  public void setUp() {
    final int modelUpdatePeriod = 2;
    final int viewUpdatePeriod = 3;
    final int initialTime = 0;
    mockModel = mock(IGameModel.class);
    mockView = mock(IGameView.class);
    mockController = mock(IGameController.class);
    List<IGameController> controllers = new ArrayList<IGameController>();
    controllers.add(mockController);
    game = new Game(mockModel, mockView, controllers, modelUpdatePeriod, viewUpdatePeriod,
        initialTime);
  }

  @Test
  public void modelIsCalledAtEveryPeriodOnUpdate() {
    game.update(0);
    verify(mockModel, times(0)).update();

    game.update(1);
    verify(mockModel, times(0)).update();

    game.update(2);
    verify(mockModel, times(1)).update();

    game.update(3);
    verify(mockModel, times(1)).update();

    game.update(4);
    verify(mockModel, times(2)).update();

    game.update(5);
    verify(mockModel, times(2)).update();

    game.update(6);
    verify(mockModel, times(3)).update();

    game.update(7);
    verify(mockModel, times(3)).update();
  }

  @Test
  public void viewIsCalledAtEveryPeriodOnUpdate() {
    game.update(0);
    verify(mockView, times(0)).update();

    game.update(1);
    verify(mockView, times(0)).update();

    game.update(2);
    verify(mockView, times(0)).update();

    game.update(3);
    verify(mockView, times(1)).update();

    game.update(4);
    verify(mockView, times(1)).update();

    game.update(5);
    verify(mockView, times(1)).update();

    game.update(6);
    verify(mockView, times(2)).update();

    game.update(7);
    verify(mockView, times(2)).update();
  }

  @Test
  public void controllerIsCalledAtEveryGamePeriodUpdate() {
    game.update(0);
    verify(mockController, times(0)).update();

    game.update(1);
    verify(mockController, times(0)).update();

    game.update(2);
    verify(mockController, times(1)).update();

    game.update(3);
    verify(mockController, times(1)).update();

    game.update(4);
    verify(mockController, times(2)).update();

    game.update(5);
    verify(mockController, times(2)).update();

    game.update(6);
    verify(mockController, times(3)).update();

    game.update(7);
    verify(mockController, times(3)).update();
  }

  @Test
  public void isRunning() {
    game.setRunning(true);
    verify(mockModel, times(1)).setRunning(true);
    game.setRunning(false);
    verify(mockModel, times(1)).setRunning(false);
  }
}
