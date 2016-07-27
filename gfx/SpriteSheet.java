package gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class SpriteSheet {
	/**
	 * Convert a SpriteSheet to a Image array
	 * 
	 * @param url
	 *            URL of sprites
	 * @return Array of sprites
	 */
	public static BufferedImage[] get(URL url) {
		BufferedImage bigImg = null;
		try {
			bigImg = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		final int width = 20;
		final int height = 20;
		final int rows = 10;
		final int cols = 10;
		BufferedImage[] sprites = new BufferedImage[rows * cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				sprites[(i * cols) + j] = bigImg.getSubimage(j * width, i * height, width, height);
			}
		}
		return sprites;
	}
}