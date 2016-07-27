package entities;

import ships.Ship;
import spaceshipgame.GUI;
import spaceshipgame.Main;

public class SpaceUnit extends Entity {
	private Ship ship;
	
	public SpaceUnit() {
		this.ship = new Ship(Main.class.getResource("/ships/jerindalis.png"),
				0, game, System.currentTimeMillis());
	}
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerHit(Player player) {
		// TODO Auto-generated method stub
		
	}
}
