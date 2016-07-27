package buttonActions;

public class Quit extends Action{

	@Override
	public void runAction() {
		parent.game.stop();
	}

}
