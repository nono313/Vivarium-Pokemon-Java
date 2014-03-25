package model.attacks;

import model.Attack;
import model.Pokemon;
import model.Type;

public class Vague_Psy extends Attack {

	public Vague_Psy() {
		super((short) 1, (float) 0.80, (short) 15, Type.Psy);
	}
	public void affect(Pokemon offender, Pokemon defender){
		defender.setHp((short) (defender.getHp()-offender.getLvl()));
	}
}