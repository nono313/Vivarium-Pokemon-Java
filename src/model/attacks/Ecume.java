package model.attacks;

import model.Attack;
import model.Type;

public class Ecume extends Attack {

	public Ecume() {
		super((short) 20, (float) 1., (short) 30, Type.Eau);
	}
}