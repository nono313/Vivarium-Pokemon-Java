package model.attacks;

import java.util.Random;

import model.Attack;
import model.Pokemon;
import model.Type;

public class E_Coque extends Attack {

	public E_Coque() {
		super((short) 1, (float) 1., (short) 10, Type.Normal);
	}
	public void affect(Pokemon offender,Pokemon defender){
		Random rand = new Random();
		float r = rand.nextFloat();
		offender.setHp((short) (offender.getHp()*(1+r/2)));
	}
}