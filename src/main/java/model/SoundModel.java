package model;

public class SoundModel implements ISoundModel {
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
        if (isMuted()) {
            unmute();
        } else {
            mute();
        }
    }
}
