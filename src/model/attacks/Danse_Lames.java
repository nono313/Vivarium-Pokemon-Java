package model.attacks;

import model.Attack;
import model.Feature;
import model.Type;

public class Danse_Lames extends Attack {

	public Danse_Lames() {
		super((short) 2, (float) Float.MAX_VALUE, (short) 30, Type.Normal,Feature.Attack,true);
	}
}