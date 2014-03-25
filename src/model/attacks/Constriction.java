package model.attacks;

import model.Attack;
import model.Type;

public class Constriction extends Attack {

	public Constriction() {
		super((short) 10, (float) 1., (short) 35, Type.Normal);
	}
}