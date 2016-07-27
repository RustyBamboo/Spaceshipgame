package gfx;

import input.MouseHandler;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import resources.SpriteMaker;
import spaceshipgame.GUI;
import entities.Entity;

public class ResourceCount extends Component {
	public Entity e;
	public int width, height;
	public BufferedImage img;

	public ResourceCount(Entity e, int relX, int relY, int width, int height, GUI parent) {
		this.e = e;
		this.parent = parent;
		this.relX = relX;
		this.relY = relY;
		this.width = width;
		this.height = height;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(img, x, y, null);
	}

	@Override
	public void tick(MouseHandler mouse) {
		BufferedImage temp = new BufferedImage(70, 6, BufferedImage.TYPE_INT_ARGB);
		SpriteMaker.addText(temp, String.format("%-7.1f", e.resource.amount), 0, 0);
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		img.getGraphics().drawImage(temp.getScaledInstance(width, height, Image.SCALE_FAST), 0, 0, null);
	}

}
