package entities;

import gfx.ImageEffects;
import input.InputHandler;
import input.MouseHandler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Random;

import resources.SpriteMaker;
import ships.Ship;
import spaceshipgame.GUI;
import spaceshipgame.Main;
import tools.MiningLaser;
import tools.WeaponLaser;

public class Player extends Entity {

	public double x, y;
	private InputHandler keyboard;
	AffineTransform at = new AffineTransform();

	public boolean thrusting;
	public double angle; // In degrees
	public double rotSpeed = 2.5;
	private Ship ship;
	private String[] ships = { "falcon.png", "bayonet.png" };
	public double gx, gy, gravity;
	private Random rand = new Random();
	private double closestMassAccel;
	public Entity closestEntity;
	public double closestDistance;

	private String debugMessage;

	public double health = 100;

	public GUI entityButtons;

	private BufferedImage arrow;

	// private ArrayList<Boolean> rendered;

	public Player(int x, int y, int size, InputHandler keyboard, Main game) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.game = game;
		this.keyboard = keyboard;
		this.ship = new Ship(Main.class.getResource("/ships/" + ships[rand.nextInt(ships.length)]), (rand.nextDouble() + 1) / 25, game, System.currentTimeMillis());
		this.ship.setColor(0, (((((255 << 8) + 0) << 8) + 255) << 8) + 0);
		this.ship.setColor(1, (((((255 << 8) + 255) << 8) + 0) << 8) + 0);
		this.ship.setTool(0, new MiningLaser(ship, 1000, 1.0, 10));
		this.ship.setTool(1, new WeaponLaser(ship, 1000, 1.0, 10));
		this.angle = 0;
		this.vx = 0;
		this.vy = 0;
		this.mass = (size + size) / 2;
		this.gravity = 0;
		this.entityButtons = new GUI(game.gui);
		game.gui.add(entityButtons, "entityButtons", true);
		arrow = game.sprites[7];
		arrow = ImageEffects.changeColor(arrow, 200, 50, 0);
	}

	public void move() {
		// Move x/y by velocities
		x += vx / 2;
		y += vy / 2;
	}

	public void render(Graphics g) {

		// Rotation information
		BufferedImage tempSprite = new BufferedImage(ship.ship.getWidth(), ship.ship.getHeight(), BufferedImage.TYPE_INT_ARGB);
		SpriteMaker.copyImage(ship.ship, tempSprite);
		if (thrusting) {
			SpriteMaker.addImage(tempSprite, ship.thrust, 0, 0);
		}
		BufferedImage temp = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		temp.getGraphics().drawImage(tempSprite.getScaledInstance(size, size, Image.SCALE_FAST), 0, 0, null);
		tempSprite = temp;

		double rotationRequired = Math.toRadians(angle);
		double locationX = tempSprite.getWidth() / 2;
		double locationY = tempSprite.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

		// Draw Entities
		for (int i = 0; i < game.entities.size(); i++) {
			Entity entity = game.entities.get(i);

			// Draw Gravity Rings
			if (game.debug) {
				for (int j = 0; j < entity.ringsG.length; j++) {
					double d = Math.sqrt(game.gconst * (entity.mass / entity.ringsG[j]));
					g.setColor(entity.ringsC[j]);
					g.drawOval((int) (entity.x - x + (game.WIDTH) / 2) - ((int) d), (int) (y - entity.y + (game.HEIGHT) / 2) - ((int) d), (int) d * 2, (int) d * 2);

				}
				// Draw Orbits
				if (entity.getClass() == Planet.class) {
					Planet p = (Planet) entity;
					g.setColor(Color.WHITE);
					g.drawOval((int) (p.getStarX() - x + (game.WIDTH / 2) - p.oD), (int) (y - p.getStarY() + (game.HEIGHT / 2) - p.oD), (int) p.oD * 2, (int) p.oD * 2);

				}
			}
			if (x - entity.x < ((game.WIDTH) / 2) + entity.size && y - entity.y < ((game.HEIGHT) / 2) + entity.size) {
				// Draw Entity
				g.drawImage(game.loadedSprites.get(i), (int) (entity.x - x + (game.WIDTH) / 2) - entity.size / 2, (int) (y - entity.y + (game.HEIGHT) / 2) - entity.size / 2, null);
			}

			// Draw pointer arrows
			double ex = (entity.x - x);
			double ey = (y - entity.y);
			if ((Math.abs(ex) - entity.size / 2) > game.WIDTH / 2 || (Math.abs(ey) - entity.size / 2) > game.HEIGHT / 2) {
				double ed = Math.sqrt((ex * ex) + (ey * ey));
				ex = ex / ed;
				ey = ey / ed;
				int wx = (int) (ex * (game.WIDTH - 50) / 2) + game.WIDTH / 2;
				int wy = (int) (ey * (game.HEIGHT - 100) / 2) + game.HEIGHT / 2;
				g.setColor(Color.WHITE);
				if (wx + 200 > game.WIDTH) {
					g.drawString(entity.name, wx - 100, wy);
				} else
					g.drawString(entity.name, wx, wy);
				double anglearrow = Math.atan2(ey, ex);
				AffineTransform arrowtx = AffineTransform.getRotateInstance(anglearrow, 10, 10);
				AffineTransformOp arrowop = new AffineTransformOp(arrowtx, AffineTransformOp.TYPE_BILINEAR);
				g.drawImage(arrowop.filter(arrow, null), wx, wy, 20, 20, null);
			}
		}

		// Chunk (star) name
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		g.drawString(String.format("%10s", game.loadedChunk.name), (game.WIDTH / 2) - game.loadedChunk.name.length() * 12 / 2, 50);

		if (closestEntity != null) {
			Entity p = closestEntity;
			g.setColor(Color.WHITE);
			g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
			g.drawString(String.format("%10s", p.name), (game.WIDTH / 2) - (p.name.length() * 11 / 2), 80); // Dunno
		}

		// Draw Player
		g.drawImage(op.filter(tempSprite, null), ((game.WIDTH) / 2) - (size / 2), ((game.HEIGHT) / 2) - (size / 2), null);

		// Draw Debug Text
		if (game.debug) {
			g.setColor(Color.WHITE);
			g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
			g.drawString(String.format("%10s%+5.3f, %+5.3f", "VX, VY: ", vx, vy), (game.WIDTH / 8) + (game.WIDTH / 155) + 10, 10);
			g.drawString(String.format("%10s%+5.3f", "Angle: ", angle), (game.WIDTH / 8) + (game.WIDTH / 155) + 10, 25);
			g.drawString(String.format("%10s%+5.3f, %+5.3f", "X, Y: ", x, y), (game.WIDTH / 8) + (game.WIDTH / 155) + 10, 40);
			g.drawString(String.format("%10s%-+6d, %-+6d", "ChX, ChY: ", game.loadedChunk.x, game.loadedChunk.y), (game.WIDTH / 8) + (game.WIDTH / 155) + 10, 55);
			g.drawString(String.format("%10s%+5.3f", "Close G: ", this.closestMassAccel), (game.WIDTH / 8) + (game.WIDTH / 155) + 10, 70);
			g.drawString(String.format("%10s", closestEntity), (game.WIDTH / 8) + (game.WIDTH / 155) + 10, 85);
			g.drawString(String.format("%10s", debugMessage), (game.WIDTH / 8) + (game.WIDTH / 155) + 10, 100);
		}
		ship.tools[0].render(this, g);
		ship.tools[1].render(this, g);

	}

	public void tick(MouseHandler mouse) {
		ship.tick(mouse);
		// Check Gravity
		if (closestEntity != null) {
			closestEntity.buttons.hidden = game.gui.get("entityButtons").hidden;
			if (mouse.ifRightClicked((int) (closestEntity.x - x + (game.WIDTH) / 2) - closestEntity.size / 2, (int) (y - closestEntity.y + (game.HEIGHT) / 2) - closestEntity.size / 2,
					closestEntity.size, closestEntity.size)) {
				game.gui.show("entityButtons");
			}
			closestEntity.buttons.relX = game.gui.get("entityButtons").relX;
			closestEntity.buttons.relY = game.gui.get("entityButtons").relY;
			closestEntity.buttons.hidden = game.gui.get("entityButtons").hidden;
			game.gui.set(closestEntity.buttons, "entityButtons");
		}
		double maxG = 0;
		double minD = 8000;
		for (int i = 0; i < game.entities.size(); i++) {
			Entity entity = game.entities.get(i);
			if (entity.mass > 0) {
				double dx, dy;
				dx = (x - entity.x);
				dy = (y - entity.y);
				double d = Math.sqrt((Math.pow(dx, 2)) + Math.pow(dy, 2));
				if (d < minD) {
					minD = d;
					boolean isInfoHidden = true;
					int relX = 0;
					int relY = game.HEIGHT - (int) (1.5 * game.WIDTH / 10.0);
					if (game.gui.get("nearestInfo") != null) {
						relX = game.gui.get("nearestInfo").relX;
						relY = game.gui.get("nearestInfo").relY;
						isInfoHidden = game.gui.get("nearestInfo").hidden;
					}
					entity.getInfo().relX = relX;
					entity.getInfo().relY = relY;
					entity.getInfo().hidden = isInfoHidden;
					game.gui.set(entity.getInfo(), "nearestInfo");
					this.closestEntity = entity;
					this.closestDistance = d;
				}

				if (d < entity.size / 4)
					entity.playerHit(this);
				if (d < 8000) { // Check if entity is nearby
					gravity = game.gconst * (entity.mass) / (d * d);
					if (gravity > maxG)
						maxG = gravity;
					if (gravity > (1.0 / 256)) {
						gx = -gravity * dx / d;
						gy = -gravity * dy / d;
						if (gravity >= entity.ringsG[0]) {
							// Auto orbit :)
							// double vt = Math.sqrt(game.gconst * entity.mass /
							// d);
							// vx = entity.vx + vt * dy/d;
							// vy = entity.vy -vt * dx/d;
						} else {
							vx += gx;
							vy += gy;
						}
					}
				}
			}
		}
		closestMassAccel = maxG;
		if (keyboard.down.isPressed()) {
			reverse();
			thrusting = false;
		} else if (keyboard.shift.isPressed()) {
			thrusting = false;
			if (Math.abs(vx) > 0 || Math.abs(vy) > 0) {
				if (reverse()) {
					thrusting = true;
					double signx = (int) Math.signum(vx);
					double signy = (int) Math.signum(vy);
					if (Math.abs(vx) > 0) {
						vx += ship.accel * Math.cos(Math.toRadians(angle));
						if (Math.signum(vx) != signx) {
							vx = 0;
						}
					}
					if (Math.abs(vy) > 0) {
						vy -= ship.accel * Math.sin(Math.toRadians(angle));
						if (Math.signum(vy) != signy) {
							vy = 0;
						}
					}
				}
			}
		} else {
			if (keyboard.up.isPressed()) {
				vx += ship.accel * Math.cos(Math.toRadians(angle));
				vy -= ship.accel * Math.sin(Math.toRadians(angle));
				thrusting = true;
			} else {
				thrusting = false;
			}
			if (keyboard.left.isPressed()) {
				angle -= rotSpeed;
				if (angle < -180) {
					angle += 360;
				}
			}
			if (keyboard.right.isPressed()) {
				angle += rotSpeed;
				if (angle > 180) {
					angle -= 360;
				}
			}
		}
		if (keyboard.q.isPressed()) {
			ship.tools[0].fire(this);
		}
		if (keyboard.e.isPressed()) {

			ship.tools[1].fire(this);
		}
		if (keyboard.i.isPressed()) {
			game.gui.show(ship.id + "");
		}
		move();
		ship.tools[0].tick(this);
		ship.tools[1].tick(this);
	}

	private boolean reverse() {
		double angleTravel = Math.atan2(vy, -vx);
		double angleR = Math.toRadians(angle);
		double distC;
		double distCC;
		if (angleTravel > angleR) {
			distC = angleTravel - angleR;
			distCC = (Math.PI * 2) - distC;
		} else {
			distCC = angleR - angleTravel;
			distC = (Math.PI * 2) - distCC;
		}
		if (distC > Math.toRadians(rotSpeed) && distCC > Math.toRadians(rotSpeed)) {
			if (distC < distCC) {
				angle += rotSpeed;
				if (angle > 180) {
					angle -= 360;
				}
			} else {
				angle -= rotSpeed;
				if (angle < -180) {
					angle += 360;
				}
			}
		} else {
			angle = Math.toDegrees(angleTravel);
			return true;
		}
		return false;
	}

	public void getDamage(double amount) {
		this.health -= amount;
	}

	@Override
	public void playerHit(Player player) {

	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}
}
