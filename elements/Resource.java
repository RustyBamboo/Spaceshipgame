package elements;

import inventory.Item;

public abstract class Resource extends Item {
	public int ID;
	public double scale;
	public String name;
	public double speed; // 0.5

	public double extract(double s) {
		if (speed * s > amount) {
			return amount = 0;
		}
		amount -= speed * s;
		return speed * s;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getAmount() {
		return this.amount;
	}
}
