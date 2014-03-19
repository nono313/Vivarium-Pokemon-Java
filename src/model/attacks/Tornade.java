package model.attacks;

import model.Attack;
import model.Type;

public class Tornade extends Attack {

	public Tornade() {
		super((short) 40, (float) 1., (short) 35, Type.Normal);
	}
}