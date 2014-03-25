package model.items;

import model.Attack;
import model.Coord;
import model.Environment;
import model.Pokemon;
import model.UsableItem;

public class Elixir extends UsableItem{
	public Elixir(Coord position, Environment env){
		super(position, (short) 10, env);
	}
	
	public boolean affect(Pokemon on){
		boolean test=false;
		for(Attack a : on.getAttacksAvailable()){
			if(a.getPowerPoint()<a.getPowerPointMax()){
				test=true;
			}
		}
		if(test){
			for(Attack a : on.getAttacksAvailable()){
				a.setPowerPoint((short) (a.getPowerPoint()+10));
			}
			return true;
		}
		else{
			return false;
		}
	}
}
