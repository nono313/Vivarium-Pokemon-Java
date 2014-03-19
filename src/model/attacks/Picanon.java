package model.attacks;

import model.Attack;
import model.Type;

public class Picanon extends Attack {

	public Picanon() {
		super((short) 20, (float) 1., (short) 15, Type.Normal);
	}
}