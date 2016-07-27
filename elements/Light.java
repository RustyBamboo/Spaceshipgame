package elements;

import gfx.ImageEffects;
import resources.SpriteMaker;

public class Light extends Resource {
	public int color;

	public Light(String name, int r, int g, int b) {
		this.color = (((((255 << 8) + r) << 8) + g) << 8) + b;
		this.sprite = ICONS[20];
		SpriteMaker.addImage(sprite, ImageEffects.changeColor(ICONS[21], r, g, b), 0, 0);
		this.speed = 0.1;
		this.name = name;
		this.scale = 100;
		id = 4;
	}
}
