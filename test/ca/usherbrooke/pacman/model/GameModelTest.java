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
    assertTrue(model.isPaused());
    model.togglePause(true);
    assertFalse(model.isPaused());
  }

  @Test
  public void togglePause() {
    assertFalse(model.isPaused());
    model.togglePause(false);
    assertTrue(model.isPaused());
    model.togglePause(false);
    assertFalse(model.isPaused());
  }

  @Test
  public void isRunning() {
    assertFalse(model.isRunning());
    model.setRunning(true);
    assertTrue(model.isRunning());
    model.setRunning(false);
    assertFalse(model.isRunning());
  }
}
