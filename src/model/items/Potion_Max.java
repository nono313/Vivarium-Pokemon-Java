package model.items;

import model.Coord;
import model.Environment;
import model.HealingItem;

public class Potion_Max extends HealingItem{
	public Potion_Max(Coord position, Environment env) {
		super(position, (short) 40, env, (short) Short.MAX_VALUE);
	}
}
