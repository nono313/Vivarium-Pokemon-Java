package model.attacks;

import model.Attack;
import model.Pokemon;
import model.Type;

public class Draco_Rage extends Attack {

	public Draco_Rage() {
		super((short) 40, (float) 1., (short) 10, Type.Dragon);
	}
	
	public void affect(Pokemon offender, Pokemon defender){
		defender.setHp((short) (defender.getHp()-40));
	}
}