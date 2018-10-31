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
import ca.usherbrooke.pacman.threads.RenderThread;
import ca.usherbrooke.pacman.view.GameView;

public class MenuMockTest {

  private MockTestController mockTestController;
  private GameModel model;
  private GameView view;
  private final int sleepTimeBetweenActionsMilliseconds = 1000;
  private AudioThread audio;
  private RenderThread renderThread;

  @Before
  public void setUp() throws AWTException, InterruptedException {
    Game game = new Game();
    game.initialize("Level.json");
    model = game.getModel();
    view = game.getView();
    audio = game.getAudioThread();
    renderThread = game.getRenderThread();
    mockTestController = new MockTestController(view, sleepTimeBetweenActionsMilliseconds);
    game.start();
    Thread.sleep(sleepTimeBetweenActionsMilliseconds);
    assertEquals(GameState.GAME_MENU, model.getGameState());
    assertTrue(model.isRunning());
  }

  @After
  public void tearDown() {
    mockTestController.close();
  }

  @Test(timeout = 30000)
  public void whenStartingTheGameThenGameIsUnpaused() throws AWTException, InterruptedException {
    mockTestController.clickStartOrResumeGame();
    assertEquals(GameState.GAME, model.getGameState());
    assertFalse(model.isPaused());
  }

  @Test(timeout = 30000)
  public void whenPressingEscapeIngameThenGoBackToMenu() throws AWTException, InterruptedException {
    mockTestController.clickStartOrResumeGame();
    mockTestController.tapKey(KeyEvent.VK_ESCAPE);
    assertEquals(GameState.GAME_MENU, model.getGameState());
  }

  @Test(timeout = 30000)
  public void whenResumingGameThenGameIsPaused() throws AWTException, InterruptedException {
    mockTestController.clickStartOrResumeGame();
    mockTestController.tapKey(KeyEvent.VK_ESCAPE);
    mockTestController.clickStartOrResumeGame();
    assertEquals(GameState.GAME, model.getGameState());
    assertTrue(model.isPaused());
  }

  @Test(timeout = 30000)
  public void goToAudioMenuAndBackToMainMenu() throws InterruptedException {
    mockTestController.clickAudio();
    assertEquals(GameState.AUDIO_MENU, model.getGameState());
    mockTestController.clickAudioGoBack();
    assertEquals(GameState.GAME_MENU, model.getGameState());
  }

  @Test(timeout = 30000)
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

  @Test(timeout = 30000)
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

  @Test(timeout = 30000)
  public void goToHighscoresMenuAndBackToMainMenu() throws InterruptedException {
    mockTestController.clickHighscores();
    assertEquals(GameState.HIGHSCORES_MENU, model.getGameState());
    mockTestController.clickHighscoreGoBack();
    assertEquals(GameState.GAME_MENU, model.getGameState());
  }

  @Test(timeout = 30000)
  public void enableFramesPerSecond() throws InterruptedException {
    assertFalse(view.getCanvas().isFpsEnabled());
    mockTestController.clickFramesPerSecondCheckbox();
    assertEquals(GameState.GAME_MENU, model.getGameState());
    assertTrue(view.getCanvas().isFpsEnabled());
    mockTestController.clickFramesPerSecondCheckbox();
    assertEquals(GameState.GAME_MENU, model.getGameState());
    assertFalse(view.getCanvas().isFpsEnabled());
  }

  @Test(timeout = 30000)
  public void changeFramesPerSecond() throws InterruptedException {
    assertEquals(30, renderThread.getTargetFps());
    mockTestController.setFramesPerSecond(60);
    assertEquals(GameState.GAME_MENU, model.getGameState());
    assertEquals(60, renderThread.getTargetFps());
  }

  @Test(timeout = 30000)
  public void exitGame() throws InterruptedException {
    mockTestController.clickExitGame();
    assertFalse(model.isRunning());
  }
}
