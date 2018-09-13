package model;

public interface IGameModel {
    void update();

    void loadLevels();

    Level getCurrentLevel();

    int getCurrentGameFrame();

    void pause();

    void unpause();

    boolean isPaused();

    void togglePause();
}
