package sound;

import static org.junit.Assert.assertEquals;
import java.io.File;
import org.junit.Test;
import model.sound.Sound;
import model.sound.SoundFactory;

public class SoundFactoryTest {

  SoundFactory soundFactory = new SoundFactory();

  @Test
  public void getFileTest() throws Exception {
    assertEquals(new File("src/main/res/sounds/pacman_beginning.wav"),
        soundFactory.getFile(Sound.BEGINNING_SOUND));
    assertEquals(new File("src/main/res/sounds/pacman_chomp.wav"),
        soundFactory.getFile(Sound.CHOMP_SOUND));
    assertEquals(new File("src/main/res/sounds/pacman_death.wav"),
        soundFactory.getFile(Sound.DEATH_SOUND));
    assertEquals(new File("src/main/res/sounds/pacman_eatfruit.wav"),
        soundFactory.getFile(Sound.EAT_FRUIT_SOUND));
    assertEquals(new File("src/main/res/sounds/pacman_eatghost.wav"),
        soundFactory.getFile(Sound.EAT_GHOST_SOUND));
    assertEquals(new File("src/main/res/sounds/pacman_extrapac.wav"),
        soundFactory.getFile(Sound.EXTRA_PAC));
    assertEquals(new File("src/main/res/sounds/pacman_intermission.wav"),
        soundFactory.getFile(Sound.INTERMISSION));
    assertEquals(new File("src/main/res/sounds/pacman_siren.wav"),
        soundFactory.getFile(Sound.SIREN));
    assertEquals(new File("src/main/res/sounds/pacman_waka.wav"),
        soundFactory.getFile(Sound.WAKA_WAKA));
  }

}
