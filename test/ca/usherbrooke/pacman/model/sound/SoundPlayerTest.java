/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.model.sound;

import ca.usherbrooke.pacman.model.GameModel;
import ca.usherbrooke.pacman.model.IGameModel;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SoundPlayerTest {
  private ISoundModel soundPlayer;

  @Before
  public void setUp() {
    IGameModel model = new GameModel();
    soundPlayer = new SoundModel(model);
  }

  @Test
  public void isMuted() {
    assertFalse(soundPlayer.isMuted());
    soundPlayer.mute();
    assertTrue(soundPlayer.isMuted());
    soundPlayer.unmute();
    assertFalse(soundPlayer.isMuted());
  }

  @Test
  public void toggleSound() {
    assertFalse(soundPlayer.isMuted());
    soundPlayer.toggleSound();
    assertTrue(soundPlayer.isMuted());
    soundPlayer.toggleSound();
    assertFalse(soundPlayer.isMuted());
  }
}
