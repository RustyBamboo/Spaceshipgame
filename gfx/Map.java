package gfx;

import input.MouseHandler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import spaceshipgame.Chunk;
import spaceshipgame.Main;
import entities.Entity;

public class Map extends Component {
	private Main game;
	private BufferedImage mapOverlay;
	private int width, height;
	private ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
	private Chunk lastLoaded = null;

	public Map(int width, int height, Main game) {
		this.width = width;
		this.height = height;
		this.game = game;
		this.mapOverlay = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = mapOverlay.getGraphics();
		g.setColor(new Color(0, 100, 0, 50));
		g.fillRect(0, 0, width, height);
		g.setColor(new Color(255, 255, 255, 50));
		for (int x = 1; x < 10; x++) {
			g.drawLine(x * width / 10, 0, x * width / 10, height);
			for (int y = 1; y < 10; y++) {
				g.drawLine(0, y * height / 10, width, y * height / 10);
			}
		}
	}

	@Override
	public void render(Graphics g) {
		if (game.loadedChunk != lastLoaded) {
//			System.out.println("Loaded Map Sprites");
			for (int i = 0; i < sprites.size(); i++) {
				sprites.get(i).flush();
				sprites.set(i, null);
			}
			sprites.clear();
			for (int i = 0; i < game.loadedSprites.size(); i++) {
				BufferedImage b = new BufferedImage((game.loadedSprites.get(i).getWidth()/width) + 1, (game.loadedSprites.get(i).getHeight()/ height) + 1, BufferedImage.TYPE_INT_ARGB);
				b.getGraphics()
						.drawImage(
								game.loadedSprites.get(i).getScaledInstance((game.loadedSprites.get(i).getWidth() / width) + 1, (game.loadedSprites.get(i).getHeight() / height) + 1,
										Image.SCALE_FAST), 0, 0, null);
				sprites.add(b);
			}
			lastLoaded = game.loadedChunk;
		}
		g.drawImage(mapOverlay, 0, 0, null);
		for (int i = 0; i < game.entities.size(); i++) {
			Entity e = game.entities.get(i);
			// Entites on map
			double x = e.x - game.loadedChunk.xcoord;
			double y = e.y - game.loadedChunk.ycoord;
			g.drawImage(sprites.get(i), (int) (x / (game.loadedChunk.SIZE / width)), (-((int) (y / (game.loadedChunk.SIZE / height)) - height / 2)) + height / 2, null);
		}
		double x = game.p.x - game.loadedChunk.xcoord;
		double y = game.p.y - game.loadedChunk.ycoord;
		g.setColor(Color.GREEN);
		g.drawRect((int) (x / (game.loadedChunk.SIZE / width)) - 2, (-((int) (y / (game.loadedChunk.SIZE / height)) - height / 2)) + (height / 2), 5, 1);
		g.drawRect((int) (x / (game.loadedChunk.SIZE / width)), (-((int) (y / (game.loadedChunk.SIZE / height)) - height / 2)) + (height / 2) - 2, 1, 5);
	}

	@Override
	public void tick(MouseHandler mouse) {

	}
}
