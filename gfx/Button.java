package gfx;

import input.MouseHandler;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import spaceshipgame.GUI;
import spaceshipgame.Main;
import buttonActions.Action;

public class Button extends Component {
	public int sizeX;
	public int sizeY;

	private BufferedImage img;

	public Main game;

	protected Action a;
	private boolean prevClick = false;

	public Button(BufferedImage img, int x, int y, int relX, int relY, int sizeX, int sizeY, Main game, Action a,
			GUI parent) {
		this.parent = parent;
		this.relX = relX;
		this.relY = relY;
		this.game = game;
		this.x = x;
		this.y = y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.img = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB);
		this.img.getGraphics().drawImage(img.getScaledInstance(sizeX, sizeY, Image.SCALE_FAST), 0, 0, null);
		a.parent = this;
		this.a = a;
	}

	public void click() {
		a.run();
	}

	public int[] get() {
		return new int[] { x, y, sizeX, sizeY };
	}

	public void tick(MouseHandler mouse) {
		if (a.current > 0)
			a.current--;
		if (!parent.hidden) {
			if (mouse.ifClicked(0, game.WIDTH, 0, game.HEIGHT)) {
				if (prevClick) {
					if (a.running) {
						click();
					} else if (mouse.ifClicked(get())) {
						click();
						a.running = true;
					} else {
						prevClick = false;
					}
				}
			} else {
				a.running = false;
				prevClick = true;
			}
		} else {
			a.running = false;
			prevClick = false;
		}
	}

	public void render(Graphics g) {
		g.drawImage(img, x, y, sizeX, sizeY, null);
	}

}
