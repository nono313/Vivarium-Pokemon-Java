package model.attacks;

import model.Attack;
import model.Type;

public class Coupe_Vent extends Attack {

	public Coupe_Vent() {
		super((short) 80, (float) 1., (short) 10, Type.Normal);
	}
}