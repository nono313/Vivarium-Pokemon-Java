package model.attacks;

import model.Attack;
import model.Type;

public class Guillotine extends Attack {

	public Guillotine() {
		super((short) Short.MAX_VALUE, (float) 0.3, (short) 5, Type.Normal);
	}
}