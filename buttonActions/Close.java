package buttonActions;

public class Close extends Action{
	String name;
	public Close(String name){
		this.name = name;
	}
	@Override
	public void runAction() {
		parent.game.gui.hide(name);
	}
}
