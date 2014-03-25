package model.items;

import model.Coord;
import model.Environment;
import model.HealingItem;

public class Limonade extends HealingItem{

	public Limonade(Coord position, Environment env) {
		super(position, (short) 16, env, (short) 80);
	}

}
