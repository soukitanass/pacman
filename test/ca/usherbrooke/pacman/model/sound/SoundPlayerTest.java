package ca.usherbrooke.pacman.model.sound;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import ca.usherbrooke.pacman.model.GameModel;
import ca.usherbrooke.pacman.model.IGameModel;

public class SoundPlayerTest {
  private ISoundModel soundPlayer;

  @Before
  public void setUp() {
    IGameModel model = new GameModel();
    soundPlayer = new SoundModel(model);
  }

  @Test
  public void isMuted() {
    assertFalse(soundPlayer.isSoundMuted());
    soundPlayer.muteSound();
    assertTrue(soundPlayer.isSoundMuted());
    soundPlayer.unmuteSound();
    assertFalse(soundPlayer.isSoundMuted());
  }

  @Test
  public void toggleSound() {
    assertFalse(soundPlayer.isSoundMuted());
    soundPlayer.toggleSound();
    assertTrue(soundPlayer.isSoundMuted());
    soundPlayer.toggleSound();
    assertFalse(soundPlayer.isSoundMuted());
  }
}
