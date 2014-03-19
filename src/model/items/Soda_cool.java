package model.items;

import model.Coord;
import model.Environment;
import model.HealingItem;

public class Soda_cool extends HealingItem{

	public Soda_cool(Coord position, Environment env) {
		super(position, (short) 15, env, (short) 60);
	}
	
}
