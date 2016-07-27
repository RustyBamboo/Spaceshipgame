package inventory;

import java.awt.image.BufferedImage;

import spaceshipgame.Main;
import elements.ResourceSheet;

public class Item {
	public double amount;
	public int id;
	public BufferedImage sprite;
	public static final BufferedImage[] ICONS = ResourceSheet.get(Main.class.getResource("/resources/resourceIcons.png"));
}
