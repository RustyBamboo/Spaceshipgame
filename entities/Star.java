package entities;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import resources.NameGen;
import resources.SpriteMaker;
import spaceshipgame.GUI;
import spaceshipgame.Main;
import buttonActions.Close;
import buttonActions.Drag;
import buttonActions.Open;
import elements.Light;
import gfx.Button;
import gfx.ImageEffects;

public class Star extends Entity {

	public ArrayList<Entity> orbitals = new ArrayList<Entity>();

	private Random rand = new Random();
	private ArrayList<Integer> orbitsD = new ArrayList<Integer>();
	private ArrayList<Integer> sizeD = new ArrayList<Integer>();

	public Star(int x, int y, int size, int sprite, Main game) {
		this.type = "Star";
		this.name = NameGen.genName();
		this.x = x;
		this.y = y;
		this.size = size;
		this.sprite = sprite;
		this.game = game;
		this.r = rand.nextInt(100) + 156;
		this.g = rand.nextInt(71) + 177;
		this.b = rand.nextInt(144) + 112;

		this.isMineable = true;
		this.resource = new Light("Light", r, g, b);
		this.resource.setAmount(rand.nextDouble() * this.resource.scale * this.size);

		this.mass = size;
		int randPlanets = rand.nextInt(10);
		for (int i = 0; i < randPlanets; i++) {
			generatePlanet();
		}

		this.buttons = new GUI(game.gui);
		buttons.relX = game.WIDTH - game.SCALE;
		buttons.relY = 150;
		Button titleBar = new Button(SpriteMaker.button(90, 10, name.substring(0, 12)), (int) (game.SCALE * 0.1 + 0.5), 0, (int) (game.SCALE * 0.1 + 0.5), 0, (int) (game.SCALE * 0.9 + 0.5),
				(int) (game.SCALE * 0.1 + 0.5), game, new Drag(buttons.relX, buttons.relY), buttons);
		buttons.add(titleBar, "titleBar", false);
		Button info = new Button(SpriteMaker.button(100, 10, "Info"), 0, (int) (game.SCALE * 0.1 + 0.5), 0, (int) (game.SCALE * 0.1 + 0.5), game.SCALE, (int) (game.SCALE * 0.1 + 0.5), game, new Open(
				"nearestInfo"), buttons);
		buttons.add(info, "info", false);
		Button close = new Button(game.sprites[6].getSubimage(0, 0, 10, 10), 0, 0, 0, 0, (int) (game.SCALE * 0.1 + 0.5), (int) (game.SCALE * 0.1 + 0.5), game, new Close("entityButtons"), buttons);
		buttons.add(close, "close", false);
	}

	private void generatePlanet() {
		int ps = rand.nextInt(size / 4) + 200; // Planet size
		int oD = rand.nextInt(10 * size) + size + ps; // 14262.5
		while (!checkOrbits(oD)) {
			oD = rand.nextInt(10 * size) + size + ps;
		}
		orbitsD.add(oD);
		sizeD.add(ps);
		int angle = rand.nextInt(360);
		orbitals.add(new Planet(ps, oD, angle, this, 1, game)); // x,y,orbitald,
	}

	private boolean checkOrbits(int oDCheck) {
		for (int i = 0; i < orbitsD.size(); i++) {
			int oD = orbitsD.get(i);
			if (Math.abs(oDCheck - oD) <= sizeD.get(i))
				return false;
		}
		return true;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void playerHit(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public BufferedImage getSprite() {
		BufferedImage temp = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		temp.getGraphics().drawImage(game.sprites[sprite].getScaledInstance(size, size, Image.SCALE_FAST), 0, 0, null);
		temp = ImageEffects.changeColor(temp, r, g, b);
		//
		// int bg;
		// if (r > g && r > b)
		// bg = 0;
		// else if (g > r && g > b)
		// bg = 1;
		// else
		// bg = 2;
		// for (int y = 0; y < temp.getHeight(); y++) {
		// for (int x = 0; x < temp.getWidth(); x++) {
		// int pix = temp.getRGB(x, y);
		// int blue = pix & 0xFF;
		// int green = (pix >> 8) & 0xFF;
		// int red = (pix >> 16) & 0xFF;
		// int alpha = (pix >> 24) & 0xFF;
		// if (alpha == 0) {
		// // temp.setRGB(x, y, Color.white.getRGB());
		// } else {
		// int change = rand.nextInt(100) - 50;
		// if (bg == 0)
		// red += change;
		// else if (bg == 1)
		// green += change;
		// else
		// blue += change;
		// int color = (((((255 << 8) + red) << 8) + green) << 8) + blue;
		// if (blue < 255 && green < 255 && red < 255)
		// temp.setRGB(x, y, color);
		// }
		//
		// }
		// }
		return temp;
	}

}
