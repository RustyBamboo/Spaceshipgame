package ships;

import input.MouseHandler;
import inventory.ShipInventory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import spaceshipgame.Main;
import tools.Tool;

public class Ship {
	public BufferedImage ship;
	public BufferedImage thrust;
	public BufferedImage weapon1;
	public BufferedImage weapon2;
	public long id;
	public Main game;
	public double accel;
	public int colors[] = new int[2];
	public Tool[] tools = new Tool[2]; // Current primary tools that can be
										// drawn on top of ship
	public ShipInventory shipinv;

	/**
	 * Creates a new Spaceship with the image and acceleration provided
	 * 
	 * @param url
	 *            URL of Spaceship Image
	 */
	public Ship(URL url, double acc, Main game, long id) {
		BufferedImage[] temp = getShip(url);
		this.id = id;
		this.game = game;
		ship = temp[0];
		thrust = temp[1];
		weapon1 = temp[2];
		weapon2 = temp[3];
		accel = acc;
		shipinv = new ShipInventory(this);
	}

	/**
	 * Set the num-th color of the ship to the given ARGB color
	 * 
	 * @param num
	 *            Color to set
	 * @param color
	 *            ARGB color
	 */
	public void setColor(int num, int color) {
		colors[num] = color;
	}

	/**
	 * Set the num-th color of the ship to a solid ARGB color using r, g, and b
	 * values
	 * 
	 * @param num
	 *            Color to set
	 * @param r
	 *            Red Component
	 * @param g
	 *            Green Component
	 * @param b
	 *            Blue Component
	 */
	public void setColor(int num, int r, int g, int b) {
		setColor(num, (((((255 << 8) + r) << 8) + g) << 8) + b);
	}

	private BufferedImage[] getShip(URL url) {
		BufferedImage bigImg = null;
		try {
			bigImg = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		final int width = 20;
		final int height = 20;
		final int rows = 1;
		final int cols = 5;
		BufferedImage[] sprites = new BufferedImage[rows * cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				sprites[(i * cols) + j] = bigImg.getSubimage(j * width, i * height, width, height);
			}
		}
		return sprites;
	}

	public void setTool(int num, Tool t) {
		t.color = colors[num];
		tools[num] = t;
	}
	public void tick(MouseHandler mouse){
		shipinv.tick(mouse);
	}
}
