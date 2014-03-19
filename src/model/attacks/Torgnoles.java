package model.attacks;

import model.Attack;
import model.Type;

public class Torgnoles extends Attack {

	public Torgnoles() {
		super((short) 15, (float) 0.85, (short) 10, Type.Normal);
	}
}