package entities;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

import buttonActions.Close;
import buttonActions.Drag;
import buttonActions.Open;
import elements.Iron;
import elements.Resource;
import elements.Stone;
import gfx.Button;
import gfx.ImageEffects;
import resources.NameGen;
import resources.SpriteMaker;
import spaceshipgame.GUI;
import spaceshipgame.Main;

public class Planet extends Entity {
	private Resource[] allowedResources = { new Stone("Stone"), new Iron("Iron") };
	private Random rand = new Random();
	AffineTransform at = new AffineTransform();

	public int oD;
	// public double vx = r.nextInt(5) - 10, vy = r.nextInt(5) - 10;
	// public double vx =1, vy =2;
	// private double gravity, gy, gx;
	// private int gconst = 50;

	private double dxS, dyS;

	private Star star;

	// public GUI planetButtons;

	public Planet(int size, int oD, int initAngle, Star star, int sprite, Main game) {
		this.type = "Planet";
		this.star = star;
		this.name = NameGen.genName();
		this.dxS = oD * Math.cos(Math.toRadians(initAngle));
		this.dyS = oD * Math.sin(Math.toRadians(initAngle));
		this.sprite = sprite;
		this.x = dxS + star.x;
		this.y = dyS + star.y;
		this.r = rand.nextInt(256);
		this.g = rand.nextInt(256);
		this.b = rand.nextInt(256);
		this.size = size;
		this.oD = oD;
		this.game = game;
		this.mass = size;
		this.isMineable = true;
		this.resource = allowedResources[rand.nextInt(allowedResources.length)];
		this.resource.setAmount(rand.nextDouble() * this.resource.scale * this.size);

		this.buttons = new GUI(game.gui);
		buttons.relX = game.WIDTH - game.SCALE;
		buttons.relY = 150;
		Button titleBar = new Button(SpriteMaker.button(90, 10, name.substring(0, 12)), (int) (game.SCALE * 0.1 + 0.5), 0, (int) (game.SCALE * 0.1 + 0.5), 0, (int) (game.SCALE * 0.9 + 0.5), (int) (game.SCALE * 0.1 + 0.5), game, new Drag(buttons.relX, buttons.relY), buttons);
		buttons.add(titleBar, "titleBar", false);
		Button info = new Button(SpriteMaker.button(100, 10, "Info"), 0, (int) (game.SCALE * 0.1 + 0.5), 0, (int) (game.SCALE * 0.1 + 0.5), game.SCALE, (int) (game.SCALE * 0.1 + 0.5), game, new Open("nearestInfo"), buttons);
		buttons.add(info, "info", false);
		Button close = new Button(game.sprites[6].getSubimage(0, 0, 10, 10), 0, 0, 0, 0, (int) (game.SCALE * 0.1 + 0.5), (int) (game.SCALE * 0.1 + 0.5), game, new Close("entityButtons"), buttons);
		buttons.add(close, "close", false);

		// this.planetButtons = new GUI();
		// System.out.println(this.resource.getAmount());
	}

	private void move() {
		double vv = Math.sqrt(game.gconst * star.mass / oD);
		double dx = (x - star.x);
		double dy = (y - star.y);

		vx = vv * dy / oD; // vx =0
		vy = vv * dx / oD; // vy = - vv
		// System.out.println(vv + " " + vx + " " + vy);
		x = x - vx;
		y = y + vy;
		// System.out.println(dx + " " + dy);
	}

	public double getStarX() {
		return star.x;
	}

	public double getStarY() {
		return star.y;
	}

	public double getStarSize() {
		return star.size;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		move();
	}

	@Override
	public void playerHit(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public BufferedImage getSprite() {
		BufferedImage temp = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		temp.getGraphics().drawImage(game.sprites[sprite].getScaledInstance(size, size, Image.SCALE_FAST), 0, 0, null);
		return ImageEffects.changeColor(temp, r, g, b);
	}
}
