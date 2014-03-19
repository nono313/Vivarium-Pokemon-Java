package model.attacks;

import model.Attack;
import model.Pokemon;
import model.Type;

public class Vampigraine extends Attack{
	public Vampigraine(){
		super((short)1, (float)1., (short)10, Type.Normal);

	}
	
	public void affect(Pokemon offender, Pokemon defender){
		short x;
		if(defender.getHp()<=16){
			x=1;
		}
		else{
			x=(short) (defender.getHp()/16);
		}
		defender.setHp((short) (defender.getHp()-x));
		offender.setHp((short) (offender.getHp()+x));
	}
}
