package controller;

import model.ISoundModel;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_M;

public class SoundController implements ISoundController {

  private ISoundModel soundPlayer;

  public SoundController(ISoundModel soundPlayer) {
    this.soundPlayer = soundPlayer;
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (VK_M == e.getKeyCode()) {
      soundPlayer.toggleSound();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {

  }
}
