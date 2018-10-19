/*******************************************************************************
 * Team agilea18b, Pacman
 *
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class GameModelTest {
  private IGameModel model;

  @Before
  public void setUp() {
    final String LEVELS_PATH = "Levels.json";
    model = new GameModel();
    model.loadLevels(LEVELS_PATH);
    model.setGameState(GameState.GAME);
  }

  @Test
  public void updates() {
    assertEquals(0, model.getCurrentGameFrame());
    model.update();
    assertEquals(1, model.getCurrentGameFrame());
  }

  @Test
  public void doesNotUpdateWhenPaused() {
    assertEquals(0, model.getCurrentGameFrame());
    model.pause();
    model.update();
    assertEquals(0, model.getCurrentGameFrame());
  }

  @Test
  public void updatesWhenUnpaused() {
    assertEquals(0, model.getCurrentGameFrame());
    model.pause();
    model.unpause();
    model.update();
    assertEquals(1, model.getCurrentGameFrame());
  }

  @Test
  public void isPaused() {
    assertFalse(model.isPaused());
    model.pause();
    assertTrue(model.isPaused());
    model.unpause();
    assertFalse(model.isPaused());
  }

  @Test
  public void toggleManualPause() {
    assertFalse(model.isPaused());
    model.togglePause(true);
    assertTrue(model.isManuallyPaused());
    assertTrue(model.isPaused());
    model.togglePause(true);
    assertFalse(model.isPaused());
    assertFalse(model.isManuallyPaused());
  }

  @Test
  public void togglePause() {
    assertFalse(model.isPaused());
    model.togglePause(false);
    assertTrue(model.isPaused());
    assertFalse(model.isManuallyPaused());
    model.togglePause(false);
    assertFalse(model.isPaused());
    assertFalse(model.isManuallyPaused());
  }

  @Test
  public void isRunning() {
    assertFalse(model.isRunning());
    model.setRunning(true);
    assertTrue(model.isRunning());
    model.setRunning(false);
    assertFalse(model.isRunning());
  }

  @Test
  public void startNewGameResetsScoreToZero() {
    model.setScore(42);
    model.startNewGame();
    assertEquals(Integer.valueOf(0), model.getScore());
  }

  @Test
  public void startNewGameResetsToFullLives() {
    final int initialNumberOfLives = model.getLives();
    model.setLives(initialNumberOfLives - 1);
    model.startNewGame();
    assertEquals(initialNumberOfLives, model.getLives());
  }
}
