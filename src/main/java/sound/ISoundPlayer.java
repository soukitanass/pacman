package sound;

public interface ISoundPlayer {
    boolean isMuted();

    void unmute();

    void mute();

    void toggleSound();
}
