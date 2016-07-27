package gfx;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import entities.Player;

public class Background {
	private Star[] stars;

	private Player p;
	private double _x, _y;
	private int width, height;
	private static Random r = new Random();

	public Background(Player p, int width, int height) {
		this.p = p;
		this.width = width;
		this.height = height;
		_x = p.x;
		_y = p.y;
		stars = new Star[20];
		for (int i = 0; i < stars.length; i++)
			stars[i] = new Star(r.nextInt(width), r.nextInt(height));
	}

	public void tick() {
		for (int i = 0; i < stars.length; i++) {
			if (_x != p.x || _y != p.y) {
				stars[i].move(_x - p.x, p.y - _y);
				if (stars[i].x < 1)
					stars[i] = new Star(width, r.nextInt(height));
				if (stars[i].x > width)
					stars[i] = new Star(1, r.nextInt(height));
				if (stars[i].y < 1)
					stars[i] = new Star(r.nextInt(width), height);
				if (stars[i].y > height)
					stars[i] = new Star(r.nextInt(width), 1);
			}
			stars[i].tick();
		}
		_x = p.x;
		_y = p.y;
	}

	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		for (int i = 0; i < stars.length; i++) {
			stars[i].render(g);
		}
	}
}
