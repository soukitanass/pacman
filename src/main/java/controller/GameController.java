package controller;

import model.IGameModel;
import view.IGameView;

public class GameController implements IGameController {
    private final IGameModel model;
    private final IGameView view;

    public GameController(IGameModel model, IGameView view) {
        this.model = model;
        this.view = view;
    }

    public void update() {

    }
}
