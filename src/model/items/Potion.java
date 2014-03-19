package model.items;

import model.Coord;
import model.Environment;
import model.HealingItem;

public class Potion extends HealingItem{
	public Potion(Coord position,Environment env){
		super(position, (short) 8, env,(short) 20);
	}
}
