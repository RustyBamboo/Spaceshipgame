package entities;

import java.util.Random;

import spaceshipgame.GUI;
import spaceshipgame.Main;

public class Gate extends Entity {
	private boolean accel;
	private double accelX, accelY;
	private Random rand = new Random();
	private long lastActivation;

	public Gate(boolean accel, int x, int y, int size, int sprite, Main game) {
		this.accel = accel;
		this.type = "Gate";
		this.name = "Gate";
		this.x = x;
		this.y = y;
		this.size = size;
		this.sprite = sprite;
		this.game = game;
		this.mass = 1;
		this.isMineable = false;

		this.r = 200;
		this.b = 200;
		this.g = 200;

		this.accelX = rand.nextDouble() * 10 + 5;
		this.accelY = rand.nextDouble() * 10 + 5;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void playerHit(Player player) {
		if (System.currentTimeMillis() - lastActivation > 5000) {
			lastActivation = System.currentTimeMillis();
			// TODO Auto-generated method stub
			double moveAx = Math.sin(Math.atan2(player.vy, player.vx));
			double moveAy = Math.cos(Math.atan2(player.vy, player.vx));
//			double moveAx = Math.cos(Math.toRadians(player.angle));
//			double moveAy = Math.sin(Math.toRadians(player.angle));

			if (accel) {
				player.vx += accelX * moveAx;
				player.vy += accelY * moveAy;
			} else {
				player.vx += accelX * moveAx;
				player.vy += accelY * moveAy;
			}
			this.r = 200;
			this.b = 0;
			this.g = 0;
		}
	}
}
