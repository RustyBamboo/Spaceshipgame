package elements;

public class Iron extends Resource {
	public Iron(String name) {
		sprite = ICONS[1];
		this.speed = 0.01;
		this.name = name;
		this.scale = 5;
		id = 1;
	}

}
