package model.sound;

import java.io.File;
import model.exceptions.InvalidSoundException;

public class SoundFactory {
  private final static String SOUND_PATH = "src" + File.separator + "main" + File.separator + "res"
      + File.separator + "sounds" + File.separator;

  public File getFile(Sound sound) throws InvalidSoundException {
    switch (sound) {
      case BEGINNING_SOUND:
        return new File(SOUND_PATH + "pacman_beginning.wav");
      case CHOMP_SOUND:
        return new File(SOUND_PATH + "pacman_chomp.wav");
      case DEATH_SOUND:
        return new File(SOUND_PATH + "pacman_death.wav");
      case EAT_FRUIT_SOUND:
        return new File(SOUND_PATH + "pacman_eatfruit.wav");
      case EAT_GHOST_SOUND:
        return new File(SOUND_PATH + "pacman_eatghost.wav");
      case EXTRA_PAC:
        return new File(SOUND_PATH + "pacman_extrapac.wav");
      case INTERMISSION:
        return new File(SOUND_PATH + "pacman_intermission.wav");
      case WAKA_WAKA:
        return new File(SOUND_PATH + "pacman_waka.wav");
      case SIREN:
        return new File(SOUND_PATH + "pacman_siren.wav");
      default:
        throw new InvalidSoundException("Invalid sound");
    }
  }
}
