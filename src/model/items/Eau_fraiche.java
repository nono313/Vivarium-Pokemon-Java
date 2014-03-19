package model.items;

import model.Coord;
import model.Environment;
import model.HealingItem;

public class Eau_fraiche extends HealingItem {
	public Eau_fraiche(Coord position, Environment env){
		super(position, (short) 8, env, (short) 20);
	}
}
