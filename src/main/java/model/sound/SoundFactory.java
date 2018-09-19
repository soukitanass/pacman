package model.sound;

import java.io.File;

public class SoundFactory {
  public File getFile(Sound sound) throws Exception {
    switch (sound) {
      case BEGINNING_SOUND:
        return new File(SoundFactory.class.getClassLoader()
            .getResource("sounds/pacman_beginning.wav").getFile());
      case CHOMP_SOUND:
        return new File(
            SoundFactory.class.getClassLoader().getResource("sounds/pacman_chomp.wav").getFile());
      case DEATH_SOUND:
        return new File(
            SoundFactory.class.getClassLoader().getResource("sounds/pacman_death.wav").getFile());
      case EAT_FRUIT_SOUND:
        return new File(SoundFactory.class.getClassLoader()
            .getResource("sounds/pacman_eatfruit.wav").getFile());
      case EAT_GHOST_SOUND:
        return new File(SoundFactory.class.getClassLoader()
            .getResource("sounds/pacman_eatghost.wav").getFile());
      case EXTRA__PAC:
        return new File(SoundFactory.class.getClassLoader()
            .getResource("sounds/pacman_extrapac.wav").getFile());
      case INTERMISSION:
        return new File(SoundFactory.class.getClassLoader()
            .getResource("sounds/pacman_intermission.wav").getFile());
      case WAKA_WAKA:
        return new File(
            SoundFactory.class.getClassLoader().getResource("sounds/pacman_waka.wav").getFile());
      case SIREN:
        return new File(
            SoundFactory.class.getClassLoader().getResource("sounds/pacman_siren.wav").getFile());
      default:
        throw new Exception("Invalid sound");
    }
  }
}
