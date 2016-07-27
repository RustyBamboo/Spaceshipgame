package buttonActions;

public class Drag extends Action {
	public int prevX, prevY, distX, distY;

	public Drag(int parX, int parY) {
		prevX = parX; // parent.parent.x;
		prevY = parY; // parent.parent.y;
		cooldown = 0;
	}

	@Override
	public void runAction() {
		int moveX = 0, moveY = 0;
		if (running) {
			moveX = parent.game.mouse.getX() - prevX;
			moveY = parent.game.mouse.getY() - prevY;
		}
		prevX = parent.game.mouse.getX();
		prevY = parent.game.mouse.getY();
		parent.parent.relX += moveX;
		parent.parent.relY += moveY;
	}
}
