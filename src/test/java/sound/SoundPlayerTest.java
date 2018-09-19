package sound;

import model.GameModel;
import model.IGameModel;
import model.sound.ISoundModel;
import model.sound.SoundModel;
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
