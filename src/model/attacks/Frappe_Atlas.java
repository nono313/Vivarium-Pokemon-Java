package model.attacks;

import model.Attack;
import model.Pokemon;
import model.Type;

public class Frappe_Atlas extends Attack {

	public Frappe_Atlas() {
		super((short) 1, (float) 1., (short) 20, Type.Combat);
	}
	public void affect(Pokemon offender, Pokemon defender){
		if(defender.getTypes().contains(Type.Spectre)==false){
			defender.setHp((short) (defender.getHp()-offender.getLvl()));
		}
	}
}