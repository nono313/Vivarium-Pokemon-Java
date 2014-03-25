package model.attacks;

import model.Attack;
import model.Type;

public class Ultimawashi extends Attack {

	public Ultimawashi() {
		super((short) 120, (float) 0.75, (short) 5, Type.Normal);
	}
}