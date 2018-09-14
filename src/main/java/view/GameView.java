package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Direction;
import model.IGameModel;
import model.Level;

public class GameView implements IGameView {
	private final String LEVEL_SPRITES = "level_sprite";
	private final int LEVEL_TILE_SIZE = 8;
	private final IGameModel model;

	public GameView(IGameModel model) {
		this.model = model;
		this.display();
	}

	private void display() {
		JFrame jFrame = new JFrame("PacMan");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.pack();
		jFrame.add(new GamePanel());
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);
	}
/*
	public void displayLevel() {
		final Level level = this.model.getCurrentLevel();
		final int width = level.getWidth();
		final int height = level.getHeight();
		final Sprite sprite = new Sprite(LEVEL_SPRITES, LEVEL_TILE_SIZE);
		List<List<Integer>> map = level.getMap();

		for (List<Integer> row : map) {
			for (Integer spriteId : row) {
				sprite.getSprite(spriteId, 1);
			}
		}

	}

	private void drawSprite(Image source, Graphics2D g2d, int x, int y, int columns, int frame, int width, int height) {
		int frameX = (frame % columns) * width;
		int frameY = (frame / columns) * height;
		// g2d.drawImage(source, x, y, x + width, y + height, frameX, frameY, frameX +
		// width, frameY + height, this);
	}
*/
	public void update() {

	}

	@SuppressWarnings("serial")
	private static class GamePanel extends JPanel implements KeyListener {
		public GamePanel() {
			this.addKeyListener(this);
			this.setFocusable(true);
			this.requestFocusInWindow();
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				System.out.println(Direction.RIGHT);
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				System.out.println(Direction.LEFT);
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				System.out.println(Direction.UP);
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				System.out.println(Direction.DOWN);
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	}
}
