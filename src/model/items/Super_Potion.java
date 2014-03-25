package model.items;

import model.Coord;
import model.Environment;
import model.HealingItem;

public class Super_Potion extends HealingItem{

	public Super_Potion(Coord position, Environment env) {
		super(position, (short) 18, env, (short) 50);
	}
}
