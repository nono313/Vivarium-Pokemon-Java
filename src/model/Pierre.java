package model;

import java.util.List;

import controll.Controller;

public abstract class Pierre extends UsableItem {

	List<String> canUse;
	public Pierre(Coord position, Environment env, List<String> evolvePlant) {
		super(position, (short) 30 , env);
		this.canUse=evolvePlant;
	}
	
	public boolean affect(Pokemon on) {
		if(canUse != null && canUse.size()!=0){
			for(String p : canUse){
				System.out.print(on.getClass().toString());
				System.out.print(p);
				if(on.getClass().toString().equals("class model.pokemons."+p)){
					Controller.getInstance().evolution(on);
					return true;
				}
			}
		}
		return false;
	}
	
	
}
