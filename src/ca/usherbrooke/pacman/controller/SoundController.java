/*******************************************************************************
 * Team agilea18b, Pacman
 * 
 * beam2039 - Marc-Antoine Beaudoin
 * dupm2216 - Maxime Dupuis
 * nass2801 - Soukaina Nassib
 * royb2006 - Benjamin Roy
 ******************************************************************************/
package ca.usherbrooke.pacman.controller;

import java.awt.event.KeyEvent;
import ca.usherbrooke.pacman.model.sound.ISoundModel;
import static java.awt.event.KeyEvent.VK_M;

public class SoundController implements ISoundController {

  private ISoundModel soundPlayer;

  public SoundController(ISoundModel soundPlayer) {
    this.soundPlayer = soundPlayer;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // Do not remove!
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (VK_M == e.getKeyCode()) {
      soundPlayer.toggleSound();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // Do not remove!
  }
}
