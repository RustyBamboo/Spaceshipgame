package entities;

import java.util.Random;

import resources.SpriteMaker;
import spaceshipgame.Chunk;
import spaceshipgame.GUI;
import spaceshipgame.Main;
import buttonActions.Close;
import buttonActions.Drag;
import buttonActions.Open;
import elements.AntiMatter;
import gfx.Background;
import gfx.Button;

public class WormHole extends Entity {

	private int connectedChunkX, connectedChunkY; // Where to send player to
	private Random rand = new Random();

	// private double connectedX, connectedY;
	private boolean set;
	private WormHole connectedWormHole;

	public WormHole(int x, int y, int connectedChunkX, int connectedChunkY, int size, int sprite, Main game, Chunk c) {
		this.type = "Wormhole";
		this.x = x;
		this.y = y;
		this.name = c.name;
		this.connectedChunkX = connectedChunkX;
		this.connectedChunkY = connectedChunkY;
		this.size = size;
		this.sprite = sprite;
		this.game = game;

		this.isMineable = true;
		this.resource = new AntiMatter("Anti Matter");
		this.resource.setAmount(rand.nextDouble() * this.resource.scale * this.size);

		this.mass = size;
		set = false;

		this.buttons = new GUI(game.gui);
		buttons.relX = game.WIDTH - game.SCALE;
		buttons.relY = 150;
		Button titleBar = new Button(SpriteMaker.button(90, 10, name.substring(0, 12)), (int) (game.SCALE * 0.1 + 0.5), 0, (int) (game.SCALE * 0.1 + 0.5), 0, (int) (game.SCALE * 0.9 + 0.5),
				(int) (game.SCALE * 0.1 + 0.5), game, new Drag(buttons.relX, buttons.relY), buttons);
		buttons.add(titleBar, "titleBar", false);
		Button info = new Button(SpriteMaker.button(100, 10, "Info"), 0, (int) (game.SCALE * 0.1 + 0.5), 0, (int) (game.SCALE * 0.1 + 0.5), game.SCALE, (int) (game.SCALE * 0.1 + 0.5), game, new Open(
				"nearestInfo"), buttons);
		buttons.add(info, "info", false);
		Button close = new Button(game.sprites[6].getSubimage(0, 0, 10, 10), 0, 0, 0, 0, (int) (game.SCALE * 0.1 + 0.5), (int) (game.SCALE * 0.1 + 0.5), game, new Close("entityButtons"), buttons);
		buttons.add(close, "close", false);
	}

	@Override
	public void tick() {
	}

	@Override
	public void playerHit(Player player) {
		if (!set) {
			Chunk c = game.getChunk(connectedChunkX, connectedChunkY);
			System.out.println(c);
			for (int i = 0; i < c.entities.size(); i++) {
				if (c.entities.get(i).getClass() == WormHole.class) {
					connectedWormHole = (WormHole) c.entities.get(i);
					break;
				}
			}
			set = true;
		}
		int dx = 0;
		int dy = 1000;
		double d = Math.sqrt((Math.pow(dx, 2)) + Math.pow(dy, 2));
		double vt = Math.sqrt(2 * game.gconst * connectedWormHole.mass / d);
		// System.out.println(vt + " " + connectedWormHole.mass + " " + d);
		// double newvx = vt * dy / d;
		// double newvy = - vt * dx / d;
		double newvx = vt;
		double newvy = 0;
		player.x = this.connectedWormHole.x + dx;
		player.y = this.connectedWormHole.y + dy;
		player.vx = newvx;
		player.vy = newvy;
		game.bg = new Background(player, game.WIDTH, game.HEIGHT);
	}
}
