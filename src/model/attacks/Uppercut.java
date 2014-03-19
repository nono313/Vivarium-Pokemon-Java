package model.attacks;

import model.Attack;
import model.Type;

public class Uppercut extends Attack {

	public Uppercut() {
		super((short) 70, (float) 1., (short) 10, Type.Normal);
	}
}