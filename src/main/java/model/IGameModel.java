package model;

public interface IGameModel {
    void update();
    void loadLevels();
    Level getCurrentLevel();
}
