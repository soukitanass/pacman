package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

	private static BufferedImage spriteSheet;

	private static final int TILE_SIZE = 32;

	public Sprite(String file) {
		this.spriteSheet = this.loadSprite(file);
	}

	public BufferedImage getSprite(int xGrid, int yGrid) {
		return this.spriteSheet.getSubimage(xGrid * TILE_SIZE, yGrid * TILE_SIZE, TILE_SIZE, TILE_SIZE);
	}

	private BufferedImage loadSprite(String file) {

		BufferedImage sprite = null;

		try {
			sprite = ImageIO.read(new File("res/" + file + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sprite;
	}

}
