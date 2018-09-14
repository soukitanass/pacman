package controller;

import model.Direction;
import model.IGameModel;
import view.IGameView;

import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_P;
import static java.awt.event.KeyEvent.VK_Q;

public class GameController implements IGameController {
    private final IGameModel model;
    private final IGameView view;

    public GameController(IGameModel model, IGameView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void update() {

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case VK_P:
                model.togglePause();
                break;
            case VK_Q:
                model.quit();
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println(Direction.RIGHT);
                break;
            case KeyEvent.VK_LEFT:
                System.out.println(Direction.LEFT);
                break;
            case KeyEvent.VK_UP:
                System.out.println(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                System.out.println(Direction.DOWN);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
