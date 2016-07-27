package gfx;

import java.awt.Color;
import java.awt.Graphics;

public class Particle {
	private int x,y,vx,vy,ax,ay;
	private int r,g,b;
	private double lifespan;
	
	public Particle(int x, int y, int vx, int vy, int ax, int ay, int r, int g, int b) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.ax = ax;
		this.ay = ay;
		this.r = r;
		this.g = g;
		this.b = b;
		this.lifespan = (float) 0.9;
	}
	public void tick() {
		this.vx += ax;
		this.vy += ay;
		this.x += vx;
		this.y += vy;
	}
	public void render(Graphics g) {
		g.setColor(new Color(this.r,this.g,this.b));
		g.drawRect(x, y, 100, 100);
	}
}
