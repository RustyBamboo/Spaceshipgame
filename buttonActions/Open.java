package buttonActions;

public class Open extends Action{
	String name;
	public Open(String name){
		this.name = name;
	}
	@Override
	public void runAction() {
		parent.game.gui.show(name);
	}
}
