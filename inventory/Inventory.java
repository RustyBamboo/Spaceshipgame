package inventory;

import input.MouseHandler;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import spaceshipgame.GUI;

public abstract class Inventory {
	public ArrayList<Item> items = new ArrayList<Item>();
	public GUI invGUI;

	public abstract void tick(MouseHandler mouse);

	public void addItems(double amount, int id, BufferedImage icon) {
		if (items.get(id) != null) {
			items.get(id).amount += amount;
		} else {
			Item temp = new Item();
			temp.id = id;
			temp.amount = amount;
			temp.sprite = icon;
			items.set(id, temp);
		}
	}

	public double getItems(int id, double amount) {
		if (items.get(id) != null) {
			if (items.get(id).amount < amount) {
				amount = items.get(id).amount;
				items.set(id, null);
				return amount;
			}
			items.get(id).amount -= amount;
			return amount;
		} else {
			return 0;
		}
	}
}
