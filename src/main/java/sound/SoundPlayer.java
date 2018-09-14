package sound;

public class SoundPlayer implements ISoundPlayer {
    private boolean isMuted;

    @Override
    public boolean isMuted() {
        return isMuted;
    }

    @Override
    public void unmute() {
        isMuted = false;
    }

    @Override
    public void mute() {
        isMuted = true;
    }

    @Override
    public void toggleSound() {
        if(isMuted()) {
            unmute();
        }
        else {
            mute();
        }
    }
}
