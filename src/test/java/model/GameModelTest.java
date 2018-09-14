package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameModelTest {
    private IGameModel model;

    @Before
    public void setUp() {
        model = new GameModel();
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
    public void togglePause() {
        assertFalse(model.isPaused());
        model.togglePause();
        assertTrue(model.isPaused());
        model.togglePause();
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
