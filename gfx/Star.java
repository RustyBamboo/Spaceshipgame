package gfx;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Star {
	public double x, y;
	public int frameNum;
	public Color color;
	private long lastFrame;
	private static Random r = new Random();

	/**
	 * Make a new star at (x, y) with a slightly random color
	 * 
	 * @param x
	 *            X screen coord of star
	 * @param y
	 *            Y screen coord of star
	 */
	public Star(int x, int y) {
		this.x = x;
		this.y = y;
		color = new Color(r.nextInt(20) + 235, r.nextInt(20) + 235, r.nextInt(20) + 235);
	}

	/**
	 * Try to animate (glitter)
	 */
	public void tick() {
		if (r.nextInt(1000) == 0) {
			frameNum = 1;
			lastFrame = System.nanoTime();
		}
	}

	/**
	 * move by 1/25 of what the player moved
	 * 
	 * @param horiz
	 *            Horizontal player movement
	 * @param vert
	 *            Vertical player movement
	 */
	public void move(double horiz, double vert) {
		x += horiz / 25;
		y += vert / 25;
	}

	/**
	 * Render star
	 * 
	 * @param g
	 *            Graphics to draw to
	 */
	public void render(Graphics g) {
		g.setColor(color);
		sw: switch (frameNum) {
		case 1:
			g.fillRect((int) x - 1, (int) y, 3, 1);
			g.fillRect((int) x, (int) y - 1, 1, 3);
			if (System.nanoTime() - lastFrame > 125000000 && frameNum >= 1) {
				frameNum = 2;
				lastFrame = System.nanoTime();
			}
			break sw;
		case 2:
			g.fillRect((int) x - 2, (int) y, 5, 1);
			g.fillRect((int) x, (int) y - 2, 1, 5);
			if (System.nanoTime() - lastFrame > 125000000 && frameNum >= 1) {
				frameNum = 0;
				lastFrame = System.nanoTime();
			}
			break sw;
		default:
			g.fillRect((int) x, (int) y, 1, 1);
			break sw;
		}
	}
}
