package ca.usherbrooke.pacman.controller;

import java.awt.event.KeyEvent;
import ca.usherbrooke.pacman.model.Direction;
import ca.usherbrooke.pacman.model.IGameModel;
import ca.usherbrooke.pacman.view.IGameView;
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
		// Do not remove!
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Do not remove!
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case VK_P: {
			model.togglePause();
			view.getCanvas().setPausePanel();
		}
			break;
		case VK_Q:
			model.quit();
			break;
		case KeyEvent.VK_RIGHT:
			model.setPacmanDirection(Direction.RIGHT);
			break;
		case KeyEvent.VK_LEFT:
			model.setPacmanDirection(Direction.LEFT);
			break;
		case KeyEvent.VK_UP:
			model.setPacmanDirection(Direction.UP);
			break;
		case KeyEvent.VK_DOWN:
			model.setPacmanDirection(Direction.DOWN);
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Do not remove!
	}
}
