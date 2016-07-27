package elements;

public class Stone extends Resource {
	public Stone(String name) {
		sprite = ICONS[0];
		this.speed = 0.1;
		this.name = name;
		this.scale = 10;
		id = 0;
	}
}
