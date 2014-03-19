package model.attacks;

import model.Attack;
import model.Type;

public class Belier extends Attack {

	public Belier() {
		super((short) 90, (float) 0.85, (short) 20, Type.Normal);
	}
}