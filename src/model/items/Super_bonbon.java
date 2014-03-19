package model.items;

import model.Coord;
import model.Environment;
import model.Pokemon;
import model.UsableItem;

public class Super_bonbon extends UsableItem {

	public Super_bonbon(Coord position, Environment env) {
		super(position, (short) 40, env);
	}

	public boolean affect(Pokemon on) {
		if(on.getLvl()==100){
			return false;
		}
		else{
			on.levelUp();
			return true;
		}
	}
	
}
