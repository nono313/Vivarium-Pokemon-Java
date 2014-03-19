package model.attacks;

import java.util.Random;

import model.Attack;
import model.Feature;
import model.Pokemon;
import model.Type;


public class Soin extends Attack {

	public Soin() {
		super((short) 1, (float) Float.MAX_VALUE, (short) 20, Type.Normal, Feature.HpMax, true);
	}
	
	public void affect(Pokemon offender, Pokemon defender){
		Random rand = new Random();
		float i = rand.nextFloat();
		offender.setHp((short) (offender.getHp()*(1+i/2)));
	}
}