package tools;

import java.awt.Graphics;

import ships.Ship;
import entities.Player;

public abstract class Tool {
	public String name;
	public int color;
	protected Ship s;
	protected boolean use;

	public abstract void tick(Player p);
	public abstract void fire(Player p);
	public abstract void render(Player p, Graphics g);
}
