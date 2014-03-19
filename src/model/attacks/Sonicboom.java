package model.attacks;

import model.Attack;
import model.Pokemon;
import model.Type;

public class Sonicboom extends Attack {

	public Sonicboom() {
		super((short) 1, (float) 0.90, (short) 20, Type.Normal);
	}
	
	public void affect(Pokemon offender, Pokemon defender){
		defender.setHp((short) (defender.getHp()-20));
	}
}