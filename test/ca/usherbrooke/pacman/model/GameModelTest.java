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
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.game.Game;
import ca.usherbrooke.pacman.model.direction.Direction;
import ca.usherbrooke.pacman.model.objects.Ghost;
import ca.usherbrooke.pacman.model.objects.Level;
import ca.usherbrooke.pacman.model.position.Position;

public class GameModelTest {
  private IGameModel model;

  @Before
  public void setUp() {
    final String LEVEL_PATH = "Level.json";
    model = new GameModel(Game.loadLevel(LEVEL_PATH));
    model.startNewGame();
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

  @Test
  public void initialLevelDoesNotChange() {
    Level level = model.getCurrentLevel();
    Level initialLevel = model.getInitialLevel();
    level.getPacMan().setPosition(new Position(0, 0));
    assertNotEquals(initialLevel.getPacMan().getPosition(), level.getPacMan().getPosition());
    level.getGhosts().get(0).setPosition(new Position(0, 0));
    assertNotEquals(initialLevel.getGhosts().get(0).getPosition(),
        level.getGhosts().get(0).getPosition());
  }

  @Test
  public void whenLivesSetToZeroThenGameIsOver() {
    assertFalse(model.isGameOver());
    model.setLives(1);
    assertFalse(model.isGameOver());
    model.setLives(0);
    assertTrue(model.isGameOver());
  }

  public void whenGhostIsKilledThenItIsRemoved() {
    Ghost killedGhost = model.getCurrentLevel().getGhosts().get(0);
    assertEquals(4, model.getCurrentLevel().getGhosts().size());
    model.processGhostKilled(killedGhost);
    assertEquals(4, model.getCurrentLevel().getGhosts().size());
    assertTrue(model.getCurrentLevel().getGhosts().contains(killedGhost));
    assertEquals(new Position(13, 15), killedGhost.getPosition());
  }

  @Test
  public void whenFirstGhostIsKilledThenScoreIncreasesBy200() {
    assertEquals(Integer.valueOf(0), model.getScore());
    model.processGhostKilled(model.getCurrentLevel().getGhosts().get(0));
    assertEquals(Integer.valueOf(200), model.getScore());
  }

  @Test
  public void whenSubsequentGhostsAreKilledThenScoreIncreasesBy200Then400Then800Then1600IfPacmanIsInvincible() {
    model.getPacman().setIsInvincible(true);
    assertEquals(Integer.valueOf(0), model.getScore());
    model.processGhostKilled(model.getCurrentLevel().getGhosts().get(0));
    assertEquals(Integer.valueOf(200), model.getScore());
    model.processGhostKilled(model.getCurrentLevel().getGhosts().get(0));
    assertEquals(Integer.valueOf(600), model.getScore());
    model.processGhostKilled(model.getCurrentLevel().getGhosts().get(0));
    assertEquals(Integer.valueOf(1400), model.getScore());
    model.processGhostKilled(model.getCurrentLevel().getGhosts().get(0));
    assertEquals(Integer.valueOf(3000), model.getScore());
  }

  @Test
  public void whenInitializingLevelThenEatenPacgumsRemainEaten() {
    final Position positionWithInitialPacgum = new Position(1, 1);
    assertTrue(model.getCurrentLevel().isPacgum(positionWithInitialPacgum));
    model.getCurrentLevel().setEmptyMapTile(positionWithInitialPacgum);
    assertFalse(model.getCurrentLevel().isPacgum(positionWithInitialPacgum));
    model.initializeLevel();
    assertFalse(model.getCurrentLevel().isPacgum(positionWithInitialPacgum));
  }

  @Test
  public void receiveAnExtraLive() {
    model.setScore(9999);
    assertEquals(3, model.getLives());
    model.setScore(10000);
    assertEquals(4, model.getLives());
    model.setScore(50000);
    assertEquals(4, model.getLives());
  }

  @Test(timeout = 2000)
  public void whenLevelIsCompletedThenLoadNextLevel() throws InterruptedException {
    GameModel model =
        new GameModel(Game.loadLevel("ThreeByOneLevelWithPacmanAndPacgumAndGhosts.json"));
    model.startNewGame();
    model.setGameState(GameState.GAME);
    assertTrue(model.getCurrentLevel().isPacgum(new Position(1, 0)));
    assertEquals(0, model.getCurrentLevelIndex());

    model.setDirection(model.getPacman(), Direction.RIGHT);
    while (!model.isLevelCompleted()) {
      model.update();
      Thread.sleep(100);
    }
    assertFalse(model.getCurrentLevel().isPacgum(new Position(1, 0)));
    while (1 != model.getCurrentLevelIndex()) {
      model.update();
    }
    assertFalse(model.isLevelCompleted());
    assertTrue(model.getCurrentLevel().isPacgum(new Position(1, 0)));

    model.setDirection(model.getPacman(), Direction.RIGHT);
    while (!model.isLevelCompleted()) {
      model.update();
      Thread.sleep(100);
    }
    while (2 != model.getCurrentLevelIndex()) {
      model.update();
    }
    assertFalse(model.isLevelCompleted());
    assertTrue(model.getCurrentLevel().isPacgum(new Position(1, 0)));
  }

  @Test(timeout = 2000)
  public void whenStartingNewGameThenLoadFirstLevel() throws InterruptedException {
    GameModel model = new GameModel(
        Game.loadLevel("FourByOneLevelWithPacmanAndPacgumAndWallAndPacgumAndGhosts.json"));
    model.startNewGame();
    model.setGameState(GameState.GAME);
    model.setCurrentLevelIndex(1);
    assertTrue(model.getCurrentLevel().isPacgum(new Position(1, 0)));
    assertTrue(model.getCurrentLevel().isPacgum(new Position(3, 0)));
    assertEquals(1, model.getCurrentLevelIndex());

    model.setDirection(model.getPacman(), Direction.RIGHT);
    while (model.getCurrentLevel().isPacgum(new Position(1, 0))) {
      model.update();
      Thread.sleep(100);
    }
    assertFalse(model.getCurrentLevel().isPacgum(new Position(1, 0)));
    assertTrue(model.getCurrentLevel().isPacgum(new Position(3, 0)));
    model.setLives(0);
    while (!model.isGameOver()) {
      model.update();
    }
    assertTrue(model.isGameOver());

    model.startNewGame();
    assertFalse(model.isLevelCompleted());
    assertEquals(0, model.getCurrentLevelIndex());
    assertTrue(model.getCurrentLevel().isPacgum(new Position(1, 0)));
    assertTrue(model.getCurrentLevel().isPacgum(new Position(3, 0)));
  }

}
