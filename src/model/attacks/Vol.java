package model.attacks;

import model.Attack;
import model.Type;

public class Vol extends Attack {

	public Vol() {
		super((short) 90, (float) 0.70, (short) 15, Type.Vol);
	}
}