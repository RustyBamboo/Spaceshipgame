package inventory;

import gfx.Button;
import gfx.Picture;
import input.MouseHandler;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import resources.SpriteMaker;
import ships.Ship;
import spaceshipgame.GUI;
import buttonActions.Close;
import buttonActions.Drag;

public class ShipInventory extends Inventory {
	public ArrayList<BufferedImage> icons = new ArrayList<BufferedImage>();
	public Ship s;

	public ShipInventory(Ship s) {
		for (int i = 0; i < 25; i++) {
			items.add(null);
			icons.add(null);
		}
		this.s = s;
		invGUI = new GUI(s.game.gui);
		invGUI.relX = s.game.CENTER_X - (4 * s.game.SCALE) / 2;
		invGUI.relY = s.game.CENTER_Y - (3 * s.game.SCALE) / 2;
		BufferedImage bkdrp = new BufferedImage(4 * s.game.SCALE, 3 * s.game.SCALE, BufferedImage.TYPE_INT_ARGB);
		BufferedImage temp = SpriteMaker.window(400, 300, "Inventory");
		BufferedImage tempShip = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
		tempShip.getGraphics().drawImage(s.ship.getScaledInstance(50, 50, Image.SCALE_FAST), 0, 0, null);
		SpriteMaker.addImage(temp, tempShip, 344, 15);
		bkdrp.getGraphics().drawImage(temp.getScaledInstance(4 * s.game.SCALE, 3 * s.game.SCALE, Image.SCALE_FAST), 0, 0, null);
		Picture backdrop = new Picture(invGUI.x, invGUI.y, 0, 0, bkdrp.getWidth(), bkdrp.getHeight(), bkdrp);
		invGUI.add(backdrop, "bkdrp");
		invGUI.add(new Button(SpriteMaker.button(390, 10, "Inventory"), 0, 0, 0, 0, (int) (s.game.SCALE * 3.9), (int) (s.game.SCALE * .1), s.game, new Drag(invGUI.x, invGUI.y), invGUI), "titleBar");
		invGUI.add(new Button(s.game.sprites[6].getSubimage(0, 0, 10, 10), 0, 0, (int) (s.game.SCALE * 3.9), 0, (int) (s.game.SCALE * .1), (int) (s.game.SCALE * .1), s.game, new Close(s.id + ""), invGUI), "close");
		s.game.gui.add(invGUI, s.id + "", true);
	}

	@Override
	public void tick(MouseHandler mouse) {
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			if (item == null)
				icons.set(i, null);
			else {
				if (item.amount == 0) {
					items.set(i, null);
				} else {
					BufferedImage tempIcon = new BufferedImage(64, 10, BufferedImage.TYPE_INT_ARGB);
					SpriteMaker.addImage(tempIcon, item.sprite, 0, 0);
					SpriteMaker.addText(tempIcon, String.format("%7.1f", item.amount), 15, 0);
					BufferedImage itemIcon = new BufferedImage((int) (s.game.SCALE * .64), (int) (s.game.SCALE * .1), BufferedImage.TYPE_INT_ARGB);
					itemIcon.getGraphics().drawImage(tempIcon.getScaledInstance(itemIcon.getWidth(), itemIcon.getHeight(), Image.SCALE_FAST), 0, 0, null);
					icons.set(i, itemIcon);
				}
			}
		}
		int row = 0;
		int col = 0;
		for (int i = 0; i < icons.size(); i++) {
			if (icons.get(i) != null) {
				BufferedImage icon = icons.get(i);
				Picture temp = new Picture(0, 0, (int) (s.game.SCALE * ((col * 69 + 5) / 100.0)), (int) (s.game.SCALE * ((row * 15 + 16) / 100.0)), icon.getWidth(), icon.getHeight(), icon);
				invGUI.set(temp, i + "");
				row++;
				if (row > 17) {
					row = 0;
					col++;
				}
			}
		}
	}
}
