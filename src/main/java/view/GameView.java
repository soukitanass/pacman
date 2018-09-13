package view;

import model.IGameModel;

public class GameView implements IGameView {
    private final IGameModel model;

    public GameView(IGameModel model) {
        this.model = model;
    }

    public void update() {

    }
}
