package model.attacks;

import model.Attack;
import model.Type;

public class Surf extends Attack {

	public Surf() {
		super((short) 95, (float) 1., (short) 15, Type.Eau);
	}
}