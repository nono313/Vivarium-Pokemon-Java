package model.items;

import model.Attack;
import model.Coord;
import model.Environment;
import model.Pokemon;
import model.UsableItem;

public class Max_Elixir extends UsableItem{
	public Max_Elixir(Coord position, Environment env){
		super(position, (short) 30, env);
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
				a.setPowerPoint((short) (a.getPowerPointMax()));
			}
			return true;
		}
		else{
			return false;
		}
	}
}