package model.items;

import model.Attack;
import model.Coord;
import model.Environment;
import model.Pokemon;
import model.UsableItem;

public class Huile extends UsableItem{
	public Huile(Coord position, Environment env){
		super(position,(short) 7,env);
	}
	public boolean affect(Pokemon on) {
		Attack att=null;
		int i=Integer.MAX_VALUE;
		for(Attack a : on.getAttacksAvailable()){
			if(a.getPowerPoint()<a.getPowerPointMax()){
				if(a.getPowerPointMax()-a.getPowerPoint()<i){
					i=a.getPowerPointMax()-a.getPowerPoint();
					att = a;
				}
			}
		}
		if(att!=null){
			att.setPowerPoint((short) (att.getPowerPoint()+10));
			return true;
		}
		else{
			return false;
		}
	}
}
