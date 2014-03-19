package model.attacks;

import model.Attack;
import model.Type;

public class Damocles extends Attack {

	public Damocles() {
		super((short) 100, (float) 1., (short) 15, Type.Normal);
	}
}