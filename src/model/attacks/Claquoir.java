package model.attacks;

import model.Attack;
import model.Type;

public class Claquoir extends Attack {

	public Claquoir() {
		super((short) 35, (float) 0.75, (short) 10, Type.Eau);
	}
}