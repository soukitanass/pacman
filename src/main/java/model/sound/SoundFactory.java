package model.sound;

import java.io.File;

public class SoundFactory {
  public File getFile(Sound sound) throws Exception {
    switch (sound) {
      case BEGINNING_SOUND:
        return new File("src/main/res/sounds/pacman_beginning.wav");
      case CHOMP_SOUND:
        return new File("src/main/res/sounds/pacman_chomp.wav");
      case DEATH_SOUND:
        return new File("src/main/res/sounds/pacman_death.wav");
      case EAT_FRUIT_SOUND:
        return new File("src/main/res/sounds/pacman_eatfruit.wav");
      case EAT_GHOST_SOUND:
        return new File("src/main/res/sounds/pacman_eatghost.wav");
      case EXTRA__PAC:
        return new File("src/main/res/sounds/pacman_extrapac.wav");
      case INTERMISSION:
        return new File("src/main/res/sounds/pacman_intermission.wav");
      default:
        throw new Exception("Invalid sound");
    }
  }
}
