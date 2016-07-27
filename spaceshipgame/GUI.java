package spaceshipgame;

import gfx.Component;
import input.MouseHandler;

import java.awt.Graphics;
import java.util.ArrayList;

public class GUI extends Component {
	private ArrayList<Component> components = new ArrayList<Component>();

	/**
	 * Create new GUI with no values
	 */
	public GUI() {
	}

	/**
	 * Create a Sub-GUI
	 * 
	 * @param parent
	 *            Parent GUI
	 */
	public GUI(GUI parent) {
		this.parent = parent;
	}

	/**
	 * Render all components unless they're hidden
	 */
	public void render(Graphics g) {
		for (int i = 0; i < components.size(); i++) {
			components.get(i).x = components.get(i).relX + x;
			components.get(i).y = components.get(i).relY + y;
			if (!components.get(i).hidden)
				components.get(i).render(g);
		}
	}

	/**
	 * Tick all components unless they're hidden
	 */
	public void tick(MouseHandler mouse) {
		for (int i = 0; i < components.size(); i++) {
			if (!components.get(i).hidden)
				components.get(i).tick(mouse);
		}
	}

	/**
	 * Add a component with a lookup name
	 * 
	 * @param c
	 *            Component to add
	 * @param name
	 *            Name for component
	 */
	public void add(Component c, String name) {
		c.name = name;
		components.add(c);
	}

	/**
	 * Add a component with a lookup name that can be hidden
	 * 
	 * @param c
	 *            Component to add
	 * @param name
	 *            Name for component
	 * @param hidden
	 *            true to hide, false to show
	 */
	public void add(Component c, String name, boolean hidden) {
		c.name = name;
		c.hidden = hidden;
		components.add(c);
	}

	/**
	 * Hide a component
	 * 
	 * @param name
	 *            Lookup name for component
	 */
	public void hide(String name) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).name.equals(name))
				components.get(i).hidden = true;
		}
	}

	/**
	 * Show a component
	 * 
	 * @param name
	 *            Lookup name for component
	 */
	public void show(String name) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).name.equals(name))
				components.get(i).hidden = false;
		}
	}

	/**
	 * Get a component to edit
	 * 
	 * @param name
	 *            Lookup name for component
	 * @return Component if found, else null
	 */
	public Component get(String name) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).name.equals(name))
				return components.get(i);
		}
		return null;
	}

	/**
	 * Change a component with a name to a new component
	 * 
	 * @param c
	 *            Component to set to
	 * @param name
	 *            Name of old component
	 */
	public void set(Component c, String name) {
		c.name = name;
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).name.equals(name)) {
				components.set(i, c);
				return;
			}
		}
		components.add(c);
	}
}