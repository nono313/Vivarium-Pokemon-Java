package model.attacks;

import model.Attack;
import model.Pokemon;
import model.Type;

public class Tenebres extends Attack {

	public Tenebres() {
		super((short) 1, (float) 1., (short) 15, Type.Spectre);
	}
	public void affect(Pokemon offender, Pokemon defender){
		defender.setHp((short) (defender.getHp()-offender.getLvl()));
	}
}