package tools;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import ships.Ship;
import entities.Entity;
import entities.Player;

public class MiningLaser extends Tool {
	private int power;
	private double miningSpeed;
	public int cooldown;
	public int lastFire;
	private ArrayList<double[]> lasers = new ArrayList<double[]>();

	/**
	 * Constructs a laser with provided player, power, miningSpeeds
	 * 
	 * @param p
	 *            Parent player
	 * @param power
	 *            Length of laser
	 * @param miningSpeed
	 *            Resource harvesting speed
	 * @param color
	 *            Color of laser
	 */
	public MiningLaser(Ship s, int power, double miningSpeed, int cooldown) {
		this.s = s;
		this.power = power;
		this.miningSpeed = miningSpeed;
		this.cooldown = cooldown;
	}

	@Override
	public void tick(Player p) {
		if (lastFire > 0)
			lastFire--;
		for (int i = 0; i < lasers.size(); i++) {
			double[] l = lasers.get(i);
			for (Entity e : p.game.entities) {
				if (Math.abs(l[0] - e.x) < e.size / 2 && Math.abs(l[1] - e.y) < e.size / 2) {
					if (Math.sqrt((l[0] - e.x) * (l[0] - e.x) + (l[1] - e.y) * (l[1] - e.y)) < e.size / 2) {
						s.shipinv.addItems(e.resource.extract(miningSpeed), e.resource.id, e.resource.sprite);
						lasers.remove(i);
					}
				}
			}
			l[0] += 50 * Math.cos(l[2]);
			l[1] -= 50 * Math.sin(l[2]);
			if (l[3] >= power / 50) {
				lasers.remove(i);
			}
			l[3]++;
		}
	}

	@Override
	public void fire(Player p) {
		if (lastFire == 0) {
			double[] l = new double[4];
			l[0] = p.x;
			l[1] = p.y;
			l[2] = Math.toRadians(p.angle);
			l[3] = 0;
			lasers.add(l);
			lastFire = cooldown;
		}
	}

	@Override
	public void render(Player p, Graphics g) {
		BufferedImage templaser = new BufferedImage(20, 19, BufferedImage.TYPE_INT_ARGB);
		for (int i = 5; i < 15; i++)
			templaser.setRGB(i, 9, color);
		BufferedImage laser = new BufferedImage(40, 39, BufferedImage.TYPE_INT_ARGB);
		laser.getGraphics().drawImage(templaser, 0, 0, 30, 39, null);
		for (double[] l : lasers) {
			int x = (int) (l[0] - p.x) + p.game.WIDTH / 2;
			int y = (int) (p.y - l[1]) + p.game.HEIGHT / 2;
			double rotationRequired = l[2];
			double locationX = laser.getWidth() / 2;
			double locationY = laser.getHeight() / 2;
			AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(op.filter(laser, null), x - laser.getWidth() / 2, y - laser.getHeight() / 2, null);
		}
	}

}
