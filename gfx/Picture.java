package gfx;

import input.MouseHandler;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Picture extends Component {
	public BufferedImage image;
	
	public Picture(int x, int y, int relX, int relY, int width, int height, BufferedImage image) {
		this.x = x;
		this.y = y;
		this.relX = relX;
		this.relY = relY;
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.image.getGraphics().drawImage(image.getScaledInstance(width, height, Image.SCALE_FAST), 0, 0, null);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(image, x, y, null);
	}

	@Override
	public void tick(MouseHandler mouse) {
	}
}
