import model.IGameModel;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class MainLoopTest {
  private IGame game;
  private IGameModel mockModel;

  @Before
  public void setUp() {
    mockModel = mock(IGameModel.class);
    game = new Game(mockModel);
  }

  @Test
  public void setRunning() {
    game.setRunning(true);
    verify(mockModel, times(1)).setRunning(true);
    game.setRunning(false);
    verify(mockModel, times(1)).setRunning(false);
  }
  
  @Test
  public void isRunning() {
    game.isRunning();
    verify(mockModel, times(1)).isRunning();
    game.setRunning(false);
    verify(mockModel, times(1)).isRunning();
  }
}
