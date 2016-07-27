package spaceshipgame;

import java.util.ArrayList;
import java.util.Random;

import entities.Entity;
import entities.Star;
import entities.WormHole;
import resources.NameGen;

public class Chunk {
	public final static double SIZE = 14262.5 * 3;
	public final static double ROOM = 14262.5 / 2;
	public final static double CENTER = SIZE / 2;

	public int x, y;
	public double xcoord, ycoord;

	private Random r = new Random();
	public String name;

	public ArrayList<Entity> entities = new ArrayList<Entity>();

	/**
	 * Generates a chunk that has a wormhole in connected chunk
	 * 
	 * @param x
	 *            Chunk x coord
	 * @param y
	 *            Chunk y coord
	 * @param x2
	 *            Connected chunk x
	 * @param y2
	 *            Connected chunk y
	 * @param game
	 *            Main game chunk is part of
	 */
	public Chunk(int x, int y, int x2, int y2, Main game) {
		this.x = x;
		this.y = y;
		this.xcoord = x * SIZE;
		this.ycoord = y * SIZE;
		this.name = NameGen.genName();
		WormHole s;
		s = new WormHole((int) (r.nextDouble() * ROOM + CENTER + xcoord), (int) (r.nextDouble() * ROOM + CENTER + ycoord), x2, y2, r.nextInt(500) + 750, 5, game, this);
		this.entities.add(s);
	}

	/**
	 * Generate a chunk with a 90% chance to be a star, 10% to be a wormhole
	 * 
	 * @param x
	 *            Chunk x coord
	 * @param y
	 *            Chunk y coord
	 * @param game
	 *            Main game that chunk is in
	 */
	public Chunk(int x, int y, Main game) {
		this.x = x;
		this.y = y;
		xcoord = x * SIZE;
		ycoord = y * SIZE;
		this.name = NameGen.genName();
		int prob = r.nextInt(10);
		if (prob < 9) {
			Star s;
			s = new Star((int) (r.nextDouble() * ROOM - ROOM / 2 + CENTER + xcoord), (int) (r.nextDouble() * ROOM - ROOM / 2 + CENTER + ycoord), r.nextInt(500) + 750, 4, game);
			this.entities.add(s);
			for (int i = 0; i < s.orbitals.size(); i++) {
				this.entities.add(s.orbitals.get(i));
			}
		} else {
			int newX = 0, newY = 0;
			searchPlanets: for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (!(i == 0 && j == 0)) {
						int cnX = this.x + i, cnY = this.y + j;
						if (game.getChunk(cnX, cnY) == null) {
							newX = cnX;
							newY = cnY;
							game.chunks.put(cnX + "" + cnY, new Chunk(cnX, cnY, this.x, this.y, game));
							break searchPlanets;
						}
					}
				}
			}
			WormHole s;
			s = new WormHole((int) (r.nextDouble() * ROOM + CENTER + xcoord), (int) (r.nextDouble() * ROOM + CENTER + ycoord), newX, newY, r.nextInt(500) + 750, 5, game, this);
			this.entities.add(s);
		}
	}
}
