package gfx;

import input.MouseHandler;

import java.awt.Graphics;

import spaceshipgame.GUI;

public abstract class Component {
	public boolean hidden = false;
	public String name;
	public GUI parent;
	public int x=0, y=0;
	public int relX=0, relY=0;
	public abstract void render(Graphics g);
	public abstract void tick(MouseHandler mouse);
}
