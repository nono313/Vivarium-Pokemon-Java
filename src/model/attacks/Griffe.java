package model.attacks;

import model.Attack;
import model.Type;

public class Griffe extends Attack {

	public Griffe() {
		super((short) 40, (float) 1., (short) 35, Type.Normal);
	}
}