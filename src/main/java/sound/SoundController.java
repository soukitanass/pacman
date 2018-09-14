package sound;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.VK_M;

public class SoundController implements KeyListener {

    private ISoundPlayer soundPlayer;

    public SoundController(ISoundPlayer soundPlayer) {
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
