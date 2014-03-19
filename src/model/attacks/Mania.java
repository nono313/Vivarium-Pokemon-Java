package model.attacks;

import model.Attack;
import model.Type;

public class Mania extends Attack {

	public Mania() {
		super((short) 120, (float) 0.9, (short) 20, Type.Normal);
	}
}