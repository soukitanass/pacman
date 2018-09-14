package controller;

import model.Direction;
import model.IGameModel;
import model.Level;
import model.PacMan;
import view.IGameView;

import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_P;

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
            case KeyEvent.VK_RIGHT:
            	setPacmanDirection(Direction.RIGHT);
                break;
            case KeyEvent.VK_LEFT:
            	setPacmanDirection(Direction.LEFT);
                break;
            case KeyEvent.VK_UP:
            	setPacmanDirection(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
            	setPacmanDirection(Direction.DOWN);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void setPacmanDirection(Direction direction) {
        System.out.println(direction);
    	PacMan pacman = model.getPacman();
        pacman.setDirection(direction);
    }
}
