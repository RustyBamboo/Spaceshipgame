package entities;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

import buttonActions.Close;
import buttonActions.Drag;
import elements.Resource;
import gfx.Button;
import gfx.ImageEffects;
import gfx.Picture;
import gfx.ResourceCount;
import input.MouseHandler;
import resources.SpriteMaker;
import spaceshipgame.GUI;
import spaceshipgame.Main;

public abstract class Entity {
	public double ringsG[] = { 1.0 / 2, 1.0 / 4, 1.0 / 16, 1.0 / 64, 1.0 / 256 };
	public Color ringsC[] = { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN };

	public String name = "No Name";
	public String type;
	public Resource resource;
	public boolean isMineable = false;
	public double mass;
	public int size;
	public double vx, vy, x, y;
	public int sprite;
	public int r = 0, g = 0, b = 0;
	public boolean doPhysics;

	public GUI buttons = new GUI();
	
	public abstract void tick();

	public Main game;
	public GUI info = null;

	public abstract void playerHit(Player player);

	public boolean isRightClicked(MouseHandler mouse, Player p) {
		return mouse.ifRightClicked((int) (x - p.x + game.WIDTH / 2 - size / 2), (int) (p.y - y + game.HEIGHT / 2 - size / 2), size, size);
	}

	public BufferedImage getSprite() {
		BufferedImage temp = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		temp.getGraphics().drawImage(game.sprites[sprite].getScaledInstance(size, size, Image.SCALE_FAST), 0, 0, null);
		return ImageEffects.changeColor(temp, r, g, b);
	}

	/**
	 * Get Info GUI about Entity
	 * 
	 * @return gui info of Entity
	 */
	public GUI getInfo() {
		if (info == null) {
			info = new GUI(game.gui);
			info.x = 0;
			info.y = 0;
			info.relX = 0;
			info.relY = game.HEIGHT - (int) (1.5 * game.SCALE);
			BufferedImage window = SpriteMaker.window(100, 150, "");
			if (isMineable) {
				SpriteMaker.addText(window, "Resource", 0, 42);
				SpriteMaker.addImage(window, resource.sprite, 0, 50);
				SpriteMaker.addText(window, resource.name, 10, 52);
				SpriteMaker.addText(window, "Amount", 0, 62);
			}

			info.add(new Picture(info.x, info.y, 0, 0, game.SCALE, (int) (1.5 * game.SCALE), window), "bkdrp");
			if (isMineable) {
				info.add(new ResourceCount(this, 0, (int) (.72 * game.SCALE), (int) (.7 * game.SCALE), (int) (.06 * game.SCALE), info), "amount");
			}
//			info.add(new Button(game.sprites[6].getSubimage(0, 0, 10, 10), info.x + (int) (game.SCALE * 0), info.y + (int)(game.SCALE * 0.5), (int) (game.SCALE * 0.9), 0, (int) (game.SCALE * .1), (int) (game.SCALE * .1),
//					game, new Close("nearestInfo"), info), "close2");
			info.add(new Button(SpriteMaker.button(90, 10, type), info.x, info.y, 0, 0, (int) (game.SCALE * .9), (int) ((1.5 * game.SCALE) / 15.0), game, new Drag(0, 0), info), "");
			info.add(new Button(game.sprites[6].getSubimage(0, 0, 10, 10), info.x + (int) (game.SCALE * 0.9), info.y, (int) (game.SCALE * 0.9), 0, (int) (game.SCALE * .1), (int) (game.SCALE * .1),
					game, new Close("nearestInfo"), info), "close");

		}
		return info;
	}
}
