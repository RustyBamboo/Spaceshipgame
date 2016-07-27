package buttonActions;

import gfx.Button;

public abstract class Action {
	public boolean running;
	public Button parent;
	public int cooldown, current;
	public abstract void runAction();
	public void run(){
		if(current<=0){
			runAction();
		}
	}
}
