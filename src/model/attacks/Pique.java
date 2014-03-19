package model.attacks;

import model.Attack;
import model.Type;

public class Pique extends Attack {

	public Pique() {
		super((short) 140, (float) 0.90, (short) 5, Type.Vol);
	}
}