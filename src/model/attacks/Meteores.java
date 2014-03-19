package model.attacks;

import model.Attack;
import model.Type;

public class Meteores extends Attack {

	public Meteores() {
		super((short) 60, (float) Float.MAX_VALUE, (short) 20, Type.Normal);
	}
}