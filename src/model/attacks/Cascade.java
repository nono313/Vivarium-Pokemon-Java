package model.attacks;

import model.Attack;
import model.Type;

public class Cascade extends Attack {

	public Cascade() {
		super((short) 80, (float) 1., (short) 15, Type.Eau);
	}
}