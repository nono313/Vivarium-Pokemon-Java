package model.attacks;

import model.Attack;
import model.Pokemon;
import model.Type;

public class Croc_Fatal extends Attack {

	public Croc_Fatal() {
		super((short) 1, (float) 0.90, (short) 10, Type.Normal);
	}
	public void affect(Pokemon offender, Pokemon defender){
		if(defender.getHp()==1){
			defender.setHp((short) 0);
		}
		else{
			defender.setHp((short) (defender.getHp()/2));
		}
	}
}