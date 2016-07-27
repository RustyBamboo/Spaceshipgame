package resources;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import spaceshipgame.Main;

public class SpriteMaker {
	private static final int BLACK = (255 << (8 * 3)) + 0, GREY = (((((255 << 8) + 149) << 8) + 149) << 8) + 149, LIGHT_GREY = (((((255 << 8) + 157) << 8) + 157) << 8) + 157,
			DARK_GREY = (((((255 << 8) + 138) << 8) + 138) << 8) + 138;
	public static BufferedImage[] letters = null;

	/**
	 * Get the images of letters. [0] = A, [2] = B, ... [25] = Z
	 * 
	 * @return Array of letters
	 */
	private static BufferedImage[] getLetters() {
		if (letters != null) {
			return letters;
		} else {
			BufferedImage bigImg = null;
			try {
				bigImg = ImageIO.read(Main.class.getResource("/resources/letters.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			final int width = 5;
			final int height = 6;
			final int rows = 1;
			final int cols = 38;
			BufferedImage[] sprites = new BufferedImage[rows * cols];

			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					sprites[(i * cols) + j] = bigImg.getSubimage(j * width, i * height, width, height);
				}
			}
			return sprites;
		}
	}

	/**
	 * Generate a window with a title
	 * 
	 * @param width
	 *            Width of window
	 * @param height
	 *            Height of window
	 * @param s
	 *            Window title
	 * @return Image of window
	 */
	public static BufferedImage window(int width, int height, String s) {
		BufferedImage b = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		// Left/Right Top
		for (int i = 0; i < 10; i++) {
			b.setRGB(0, i, LIGHT_GREY);
			b.setRGB(width - 1, i, DARK_GREY);
		}
		// Middle Top
		for (int i = 1; i < width - 1; i++) {
			b.setRGB(i, 0, LIGHT_GREY);
			b.setRGB(i, 9, GREY);
			for (int j = 1; j < 10 - 1; j++) {
				b.setRGB(i, j, GREY);
			}
		}
		// Line Top/Main
		for (int i = 0; i < width; i++) {
			b.setRGB(i, 10, BLACK);
		}
		// Left/Right Main
		for (int i = 11; i < height; i++) {
			b.setRGB(0, i, LIGHT_GREY);
			b.setRGB(width - 1, i, DARK_GREY);
		}
		// Middle Main
		for (int i = 1; i < width - 1; i++) {
			b.setRGB(i, 11, LIGHT_GREY);
			b.setRGB(i, height - 1, DARK_GREY);
			for (int j = 12; j < height - 1; j++) {
				b.setRGB(i, j, GREY);
				;
			}
		}
		addText(b, s, 1, 2);
		return b;
	}

	public static BufferedImage button(int width, int height, String s) {
		BufferedImage b = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		// Left/Right Main
		for (int i = 0; i < height; i++) {
			b.setRGB(0, i, LIGHT_GREY);
			b.setRGB(width - 1, i, DARK_GREY);
		}
		// Middle Main
		for (int i = 1; i < width - 1; i++) {
			b.setRGB(i, 0, LIGHT_GREY);
			b.setRGB(i, height - 1, DARK_GREY);
			for (int j = 1; j < height - 1; j++) {
				b.setRGB(i, j, GREY);
			}
		}
		// Add Text
		addText(b, s, 1, 2);
		return b;
	}

	/**
	 * Draw a string onto and image. Only A-Z and spaces accepted. Will not draw
	 * if string is too wide
	 * 
	 * @param b
	 *            Image to draw to
	 * @param s
	 *            String to draw
	 * @param x
	 *            X coord of top left corner of string to draw
	 * @param y
	 *            Y coord of top left corner of string to draw
	 */
	public static void addText(BufferedImage b, String s, int x, int y) {
		letters = getLetters();
		char[] sc = s.toUpperCase().toCharArray();
		// Can draw?
		if (b.getWidth() - x >= sc.length * 7) {
			// Draw String
			for (int i = 0; i < sc.length; i++) {
				int c = (int) (sc[i] - 'A');
				if (c != -33) {
					if (c == -19) // .
						addImage(b, letters[26], x + (6 * i) + 1, y);
					else if (c == -17) // 0
						addImage(b, letters[27], x + (6 * i) + 1, y);
					else if (c == -16) // 1
						addImage(b, letters[28], x + (6 * i) + 1, y);
					else if (c == -15) // 2
						addImage(b, letters[29], x + (6 * i) + 1, y);
					else if (c == -14) // 3
						addImage(b, letters[30], x + (6 * i) + 1, y);
					else if (c == -13) // 4
						addImage(b, letters[31], x + (6 * i) + 1, y);
					else if (c == -12) // 5
						addImage(b, letters[32], x + (6 * i) + 1, y);
					else if (c == -11) // 6
						addImage(b, letters[33], x + (6 * i) + 1, y);
					else if (c == -10) // 7
						addImage(b, letters[34], x + (6 * i) + 1, y);
					else if (c == -9) // 8
						addImage(b, letters[35], x + (6 * i) + 1, y);
					else if (c == -8) // 9
						addImage(b, letters[36], x + (6 * i) + 1, y);
					else if (c == -7)
						addImage(b, letters[37], x + (6 * i) + 1, y);
					else
						addImage(b, letters[c], x + (6 * i) + 1, y);
				}
			}
		}
	}

	/**
	 * Draw an image onto and image.
	 * 
	 * @param b
	 *            Image to draw to
	 * @param i
	 *            Image to draw
	 * @param x
	 *            X coord of top left corner of image to draw
	 * @param y
	 *            Y coord of top left corner of image to draw
	 */
	public static void addImage(BufferedImage b, BufferedImage i, int x, int y) {
		for (int _x = 0; _x < i.getWidth(); _x++) {
			for (int _y = 0; _y < i.getHeight(); _y++) {
				if (i.getRGB(_x, _y) >> (8 * 3) < 0) {
					b.setRGB(_x + x, _y + y, i.getRGB(_x, _y));
				}
			}
		}
	}

	/**
	 * Draw an image into and image.
	 * 
	 * @param b
	 *            Image to draw to
	 * @param i
	 *            Image to draw
	 */
	public static void copyImage(BufferedImage b, BufferedImage dest) {
		for (int _x = 0; _x < b.getWidth(); _x++) {
			for (int _y = 0; _y < b.getHeight(); _y++) {
				dest.setRGB(_x, _y, b.getRGB(_x, _y));
			}
		}
	}
}
