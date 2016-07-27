package buttonActions;

public class Toggle extends Action {
	String name;
	int x, y;

	public Toggle(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}

	@Override
	public void runAction() {
		if (parent.game.gui.get(name).hidden) {
			parent.game.gui.show(name);
		} else {
			parent.game.gui.hide(name);
		}
		int temp;
		temp = parent.x;
		parent.x = x;
		x = temp;
		temp = parent.y;
		parent.y = y;
		y = temp;
	}
}
