package model.sound;

import java.io.File;

public class SoundFactory {
  public File getFile(Sound sound) throws Exception {
    switch (sound) {
      case BEGINNING_SOUND:
        return new File("src" + File.separator + "main" + File.separator + "res" + File.separator + "sounds" + File.separator + "pacman_beginning.wav");
      case CHOMP_SOUND:
        return new File("src" + File.separator + "main" + File.separator + "res" + File.separator + "sounds" + File.separator + "pacman_chomp.wav");
      case DEATH_SOUND:
        return new File("src" + File.separator + "main" + File.separator + "res" + File.separator + "sounds" + File.separator + "pacman_death.wav");
      case EAT_FRUIT_SOUND:
        return new File("src" + File.separator + "main" + File.separator + "res" + File.separator + "sounds" + File.separator + "pacman_eatfruit.wav");
      case EAT_GHOST_SOUND:
        return new File("src" + File.separator + "main" + File.separator + "res" + File.separator + "sounds" + File.separator + "pacman_eatghost.wav");
      case EXTRA__PAC:
        return new File("src" + File.separator + "main" + File.separator + "res" + File.separator + "sounds" + File.separator + "pacman_extrapac.wav");
      case INTERMISSION:
        return new File("src" + File.separator + "main" + File.separator + "res" + File.separator + "sounds" + File.separator + "pacman_intermission.wav");
      case WAKA_WAKA:
        return new File("src" + File.separator + "main" + File.separator + "res" + File.separator + "sounds" + File.separator + "pacman_waka.wav");
      case SIREN:
        return new File("src" + File.separator + "main" + File.separator + "res" + File.separator + "sounds" + File.separator + "pacman_siren.wav");
      default:
        throw new Exception("Invalid sound");
    }
  }
}
