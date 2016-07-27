package gfx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ButtonSheet {
	public static BufferedImage[] get(URL url) {
		BufferedImage bigImg = null;
		try {
			bigImg = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// The above line throws an checked IOException which must be caught.

		final int width = 100;
		final int height = 20;
		final int rows = 10;
		final int cols = 1;
		BufferedImage[] sprites = new BufferedImage[rows * cols];
		for (int j = 0; j < rows; j++) {
			sprites[j] = bigImg.getSubimage(0, (height * j) - (j * 2), width, height);
		}
		return sprites;
	}
}