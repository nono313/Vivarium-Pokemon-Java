package model.attacks;

import model.Attack;
import model.Type;

public class Explosion extends Attack {

	public Explosion() {
		super((short) 250, (float) 1., (short) 5, Type.Normal);
	}
}