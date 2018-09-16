import model.IGameModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import view.IGameView;
import static org.mockito.Mockito.*;

public class GameTimerTest {
  private GameTimer gameTimer;
  private IGame mockGame;
  private IGameModel mockModel;
  private IGameView mockView;

  @Before
  public void setUp() {
    mockGame = mock(IGame.class);
    mockModel = mock(IGameModel.class);
    mockView = mock(IGameView.class);
    gameTimer = new GameTimer(mockGame, mockModel, mockView);

    Mockito.when(mockGame.isRunning()).thenReturn(true);
  }

  @Test
  public void modelIsCalledAtEveryPeriodOnUpdate() {
    gameTimer.run();
    verify(mockModel, times(0)).update();

    gameTimer.run();
    verify(mockModel, times(0)).update();

    gameTimer.run();
    verify(mockModel, times(0)).update();

    gameTimer.run();
    verify(mockModel, times(0)).update();

    gameTimer.run();
    verify(mockModel, times(0)).update();

    gameTimer.run();
    verify(mockModel, times(0)).update();

    gameTimer.run();
    verify(mockModel, times(1)).update();
  }

  @Test
  public void viewIsCalledAtEveryPeriodOnUpdate() {
    gameTimer.run();
    verify(mockView, times(1)).update();

    gameTimer.run();
    verify(mockView, times(2)).update();

    gameTimer.run();
    verify(mockView, times(3)).update();

    gameTimer.run();
    verify(mockView, times(4)).update();

    gameTimer.run();
    verify(mockView, times(5)).update();

    gameTimer.run();
    verify(mockView, times(6)).update();

    gameTimer.run();
    verify(mockView, times(7)).update();

    gameTimer.run();
    verify(mockView, times(8)).update();
  }
}
