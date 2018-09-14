package view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;

import model.IGameModel;
import model.Level;

public class GameView implements IGameView {
	private final String SPRITE_SOURCE = "";

	private final IGameModel model;

	public GameView(IGameModel model) {
		this.model = model;
	}

	public void update() {

	}

	public void displayLevel() {
		final Level level = this.model.getCurrentLevel();
		final int width = level.getWidth();
		final int height = level.getHeight();
		final Sprite sprite = new Sprite(SPRITE_SOURCE);
		List<List<Integer>> map = level.getMap();

		for (List<Integer> row : map) {
			for (Integer column : row) {
				//sprite.getSprite(xGrid, yGrid)
			}
		}

	}

	private void drawSprite(Image source, Graphics2D g2d, int x, int y, int columns, int frame, int width, int height) {
		int frameX = (frame % columns) * width;
		int frameY = (frame / columns) * height;
	//	g2d.drawImage(source, x, y, x + width, y + height, frameX, frameY, frameX + width, frameY + height, this);
	}
}
