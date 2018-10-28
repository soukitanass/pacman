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
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.model.highscores.HighScore;
import ca.usherbrooke.pacman.model.highscores.HighScores;

public class HighScoresTest {

  private HighScores highScores;
  private final String HIGH_SCORES_PATH = "Highscores.json";

  @Before
  public void setUp() throws Exception {
    highScores = new HighScores();
  }

  @Test
  public void highScoresAreLoaded() {

    highScores = HighScores.loadHighScores(HIGH_SCORES_PATH);
    assertFalse(highScores.getListHighScores().isEmpty());
  }

  @Test
  public void highScoresAreSaved() {
    final String HIGH_SCORES_PATH = "HighScoresTestingFile.json";
    highScores = HighScores.loadHighScores(HIGH_SCORES_PATH);
    HighScores highScoresCopy = new HighScores(highScores.getListHighScores());

    int size = highScores.getListHighScores().size();
    int expectedSize = size - 1;

    highScores.getListHighScores().remove(size - 1);
    highScores.saveHighScores(HIGH_SCORES_PATH);
    highScores = HighScores.loadHighScores(HIGH_SCORES_PATH);
    assertEquals(expectedSize, highScores.getListHighScores().size());

    highScoresCopy.saveHighScores(HIGH_SCORES_PATH);
    highScores = HighScores.loadHighScores(HIGH_SCORES_PATH);
    assertEquals(size, highScores.getListHighScores().size());
  }

  @Test
  public void isHighScoreTest() {
    highScores = HighScores.loadHighScores(HIGH_SCORES_PATH);
    int aHighScore = highScores.getListHighScores().get(0).getScore() + 1;
    assertTrue(highScores.isHighScore(aHighScore));

    int size = highScores.getListHighScores().size();
    int notHighscore = highScores.getListHighScores().get(size - 1).getScore() - 1;
    assertFalse(highScores.isHighScore(notHighscore));
  }

  @Test
  public void setHighScoreTest() {
    final String HIGH_SCORES_PATH = "HighScoresTestingFile.json";
    highScores = HighScores.loadHighScores(HIGH_SCORES_PATH);
    int aHighScore = highScores.getListHighScores().get(0).getScore() + 1;
    HighScore expectedHighScore = new HighScore("abc", aHighScore);
    highScores.setHighScore(expectedHighScore.getScore(), expectedHighScore.getName());
    Collections.sort(highScores.getListHighScores());
    assertEquals(expectedHighScore, highScores.getListHighScores().get(0));
  }

}
