package model.attacks;

import model.Attack;
import model.Type;

public class Seisme extends Attack {

	public Seisme() {
		super((short) 100, (float) 1., (short) 10, Type.Sol);
	}
}