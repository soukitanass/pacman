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
    assertEquals(new File(
        SoundFactory.class.getClassLoader().getResource("sounds/pacman_beginning.wav").getFile()),
        soundFactory.getFile(Sound.BEGINNING_SOUND));
    assertEquals(
        new File(
            SoundFactory.class.getClassLoader().getResource("sounds/pacman_chomp.wav").getFile()),
        soundFactory.getFile(Sound.CHOMP_SOUND));
    assertEquals(
        new File(
            SoundFactory.class.getClassLoader().getResource("sounds/pacman_death.wav").getFile()),
        soundFactory.getFile(Sound.DEATH_SOUND));
    assertEquals(new File(
        SoundFactory.class.getClassLoader().getResource("sounds/pacman_eatfruit.wav").getFile()),
        soundFactory.getFile(Sound.EAT_FRUIT_SOUND));
    assertEquals(new File(
        SoundFactory.class.getClassLoader().getResource("sounds/pacman_eatghost.wav").getFile()),
        soundFactory.getFile(Sound.EAT_GHOST_SOUND));
    assertEquals(new File(
        SoundFactory.class.getClassLoader().getResource("sounds/pacman_extrapac.wav").getFile()),
        soundFactory.getFile(Sound.EXTRA_PAC));
    assertEquals(new File(SoundFactory.class.getClassLoader()
        .getResource("sounds/pacman_intermission.wav").getFile()),
        soundFactory.getFile(Sound.INTERMISSION));
    assertEquals(
        new File(
            SoundFactory.class.getClassLoader().getResource("sounds/pacman_siren.wav").getFile()),
        soundFactory.getFile(Sound.SIREN));
    assertEquals(
        new File(
            SoundFactory.class.getClassLoader().getResource("sounds/pacman_waka.wav").getFile()),
        soundFactory.getFile(Sound.WAKA_WAKA));
  }

}
