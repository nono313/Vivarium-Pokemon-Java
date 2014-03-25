package model.attacks;

import model.Attack;
import model.Type;

public class Trempette extends Attack {

	public Trempette() {
		super((short) 0, (float) Float.MAX_VALUE, (short) 40, Type.Normal);
	}
}