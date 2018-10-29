package ca.usherbrooke.pacman.mocktests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.awt.AWTException;
import java.awt.event.KeyEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.game.Game;
import ca.usherbrooke.pacman.model.GameModel;
import ca.usherbrooke.pacman.model.GameState;
import ca.usherbrooke.pacman.threads.AudioThread;
import ca.usherbrooke.pacman.view.GameView;

public class MenuMockTest {

  private MockTestController mockTestController;
  private GameModel model;
  private GameView view;
  private final int sleepTimeBetweenActionsMilliseconds = 1000;
  private AudioThread audio;

  @Before
  public void setUp() throws AWTException, InterruptedException {
    Game game = new Game();
    game.initialize();
    model = game.getModel();
    view = game.getView();
    audio = game.getAudioThread();
    mockTestController = new MockTestController(view, sleepTimeBetweenActionsMilliseconds);
    game.start();
    Thread.sleep(sleepTimeBetweenActionsMilliseconds);
    assertEquals(GameState.GAME_MENU, model.getGameState());
  }

  @After
  public void tearDown() {
    mockTestController.close();
  }

  @Test
  public void whenStartingTheGameThenGameIsUnpaused() throws AWTException, InterruptedException {
    mockTestController.clickStartOrResumeGame();
    assertEquals(GameState.GAME, model.getGameState());
    assertFalse(model.isPaused());
  }

  @Test
  public void whenPressingEscapeIngameThenGoBackToMenu() throws AWTException, InterruptedException {
    mockTestController.clickStartOrResumeGame();
    mockTestController.tapKey(KeyEvent.VK_ESCAPE);
    assertEquals(GameState.GAME_MENU, model.getGameState());
  }

  @Test
  public void whenResumingGameThenGameIsPaused() throws AWTException, InterruptedException {
    mockTestController.clickStartOrResumeGame();
    mockTestController.tapKey(KeyEvent.VK_ESCAPE);
    mockTestController.clickStartOrResumeGame();
    assertEquals(GameState.GAME, model.getGameState());
    assertTrue(model.isPaused());
  }

  @Test
  public void goToAudioMenuAndBackToMainMenu() throws InterruptedException {
    mockTestController.clickAudio();
    assertEquals(GameState.AUDIO_MENU, model.getGameState());
    mockTestController.clickGoBack();
    assertEquals(GameState.GAME_MENU, model.getGameState());
  }

  @Test
  public void muteMusic() throws InterruptedException {
    mockTestController.clickAudio();
    assertEquals(GameState.AUDIO_MENU, model.getGameState());
    assertFalse(audio.isMusicMuted());
    mockTestController.clickMuteMusicCheckbox();
    assertEquals(GameState.AUDIO_MENU, model.getGameState());
    assertTrue(audio.isMusicMuted());
    mockTestController.clickMuteMusicCheckbox();
    assertEquals(GameState.AUDIO_MENU, model.getGameState());
    assertFalse(audio.isMusicMuted());
  }

  @Test
  public void muteSound() throws InterruptedException {
    mockTestController.clickAudio();
    assertEquals(GameState.AUDIO_MENU, model.getGameState());
    assertFalse(audio.isSoundMuted());
    mockTestController.clickMuteSoundCheckbox();
    assertEquals(GameState.AUDIO_MENU, model.getGameState());
    assertTrue(audio.isSoundMuted());
    mockTestController.clickMuteSoundCheckbox();
    assertEquals(GameState.AUDIO_MENU, model.getGameState());
    assertFalse(audio.isSoundMuted());
  }
}
