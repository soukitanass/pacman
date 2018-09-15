package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JFrame;

import model.IGameModel;
import model.Level;

public class LevelView {
	private final String LEVEL_SPRITES = "level_sprite";
	private final int LEVEL_TILE_SIZE = 8;
	private IGameModel model;

	public LevelView(IGameModel model) {
		this.model = model;
	}

	public void paint(Graphics graphic, JFrame window) {
		final Level level = this.model.getCurrentLevel();
		final int width = level.getWidth();
		final int height = level.getHeight();
		final Sprite sprite = new Sprite(LEVEL_SPRITES, LEVEL_TILE_SIZE);
		List<List<Integer>> map = level.getMap();

		for (List<Integer> row : map) {
			for (Integer spriteId : row) {
				BufferedImage image = sprite.getSprite(spriteId, 1);
				drawLevel(image, graphic, 0, 0, 1, 1, 10, 10);
			}
		}

	}

	private void drawLevel(Image source, Graphics graphic, int x, int y, int columns, int frame, int width, int height) {
		int frameX = (frame % columns) * width;
		int frameY = (frame / columns) * height;
		graphic.drawImage(source, x, y, x + width, y + height, frameX, frameY, frameX + width, frameY + height, null);
	}
}
