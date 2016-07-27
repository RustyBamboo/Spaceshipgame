package spaceshipgame;

import entities.Entity;
import entities.Player;
import gfx.Background;
import gfx.Button;
import gfx.Map;
import gfx.Picture;
import gfx.SpriteSheet;
import input.InputHandler;
import input.MouseHandler;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import resources.SpriteMaker;
import buttonActions.Quit;

public class Main extends Canvas implements Runnable {
	private static final long serialVersionUID = 3572605473303097116L;

	public JFrame frame;


	public final int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public final int HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - ((System.getProperty("os.name").contains("Mac")? 1 : 0)) * 24;
	public final int SCALE = (int) (WIDTH / 10.0);
	public final int CENTER_X = WIDTH / 2;
	public final int CENTER_Y = HEIGHT / 2;

	public final double gconst = 50;

	public BufferedImage[] sprites;

	private boolean running;
	public MouseHandler mouse;
	private InputHandler keyboard;
	public Background bg;
	public Player p;
	public GUI gui;
	private int debugCooldown = 0;
	public boolean debug = false;
	public HashMap<String, Chunk> chunks = new HashMap<String, Chunk>();
	private Random r = new Random();
	private int[] dirx = { 0, 0, 1, -1 };
	private int[] diry = { 1, -1, 0, 0 };
	public Chunk loadedChunk;
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public ArrayList<BufferedImage> loadedSprites = new ArrayList<BufferedImage>();
	

	Main() {
		String os = System.getProperty("os.name");
		System.out.println(os);
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		frame = new JFrame("SpaceShip Game");
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();

		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

		frame.setVisible(true);

	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000 / 60;
		int frames = 0;
		int ticks = 0;
		long lastTimer = System.currentTimeMillis();
		double delta = 0;

		init();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			// boolean shouldRender = true;
			while (delta >= 1) {
				ticks++;
				tick();
				frames++;
				render();
				delta -= 1;
				// shouldRender = false;
			}
			try {
				Thread.sleep(2); // Slow it down
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// if (shouldRender) {
			// frames++;
			// render();
			// }
			if (System.currentTimeMillis() - lastTimer > 1000) {
				lastTimer += 1000;
				// frame.setTitle(ticks + " " + frames);
				frame.setTitle(mouse.getX() + ", " + mouse.getY() + " | " + frames + " " + ticks);

				frames = 0;
				ticks = 0;
			}
		}
	}

	private synchronized void start() {
		running = true;
		new Thread(this).start();
	}

	public synchronized void stop() {
		System.exit(0);
	}

	public void init() {
		// Initialize Things/Input
		sprites = SpriteSheet.get(Main.class.getResource("/resources/sheet.png"));
		mouse = new MouseHandler(this);
		keyboard = new InputHandler(this);
		gui = new GUI();
		initGUI();
		p = new Player((int) Chunk.CENTER, (int) Chunk.CENTER, 50, keyboard, this);
		bg = new Background(p, WIDTH, HEIGHT);
	}

	public void initGUI() {
		gui.add(new Button(SpriteMaker.button(50, 10, "QUIT"), 0, 0, WIDTH - (int) ((WIDTH / 10.0) * .5), HEIGHT - (int) ((WIDTH / 10.0) * .1), (int) ((WIDTH / 10.0) * .5),
				(int) ((WIDTH / 10.0) * .1), this, new Quit(), gui), "quit");
		try {
			gui.add(new Picture(0, 0, 0, 0, (WIDTH / 8) + (WIDTH / 155), (WIDTH / 8) + (WIDTH / 155), ImageIO.read(Main.class.getResource("/resources/mapborder.png"))), "mpbdr");
		} catch (IOException e) {
			e.printStackTrace();
		}
		gui.add(new Map(WIDTH / 8, WIDTH / 8, this), "mp");
	}

	public Chunk getChunk(int x, int y) {
		String id = x + "" + y;
		if (chunks.containsKey(id)) {
			return chunks.get(id);
		}
		return null;
	}

	public void tick() {
		// Generate Chunks
		int chunkx = (int) Math.floor(p.x / Chunk.SIZE);
		int chunky = (int) Math.floor(p.y / Chunk.SIZE);
		String id = chunkx + "" + chunky;
		if (chunks.containsKey(id)) {
			loadedChunk = chunks.get(id);
		} else {
			chunks.put(id, new Chunk(chunkx, chunky, this));
			loadedChunk = chunks.get(id);
		}
		for (int i = 0; i < 4; i++) {
			id = (chunkx + dirx[i]) + "" + (chunky + diry[i]);
			if (!chunks.containsKey(id)) {
				chunks.put(id, new Chunk(chunkx + dirx[i], chunky + diry[i], this));
			}
		}
		// Load Entities
		if (entities != loadedChunk.entities) {
			entities = loadedChunk.entities;
			for (int i = 0; i < loadedSprites.size(); i++) {
				loadedSprites.get(i).flush();
				loadedSprites.set(i, null);
			}
			loadedSprites.clear();
			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				loadedSprites.add(e.getSprite());
			}
		}
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).tick();
		}
		if (debugCooldown < 0) {
			if (keyboard.debug.isPressed()) {
				debug = !debug;
				debugCooldown = 5;
			}
		} else {
			debugCooldown--;
		}
		// Tick Parts
		bg.tick();
		p.tick(mouse);
		gui.tick(mouse);
	}

	public void render() {
		// Get Graphics
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		// Render Parts
		bg.render(g);
		p.render(g);
		gui.render(g);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		new Main().start();
	}
}
