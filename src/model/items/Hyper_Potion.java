package model.items;

import model.Coord;
import model.Environment;
import model.HealingItem;

public class Hyper_Potion extends HealingItem{

	public Hyper_Potion(Coord position, Environment env){
		super(position, (short) 25, env, (short) 200);
	}
}
