package model.attacks;

import model.Attack;
import model.Type;

public class Abime extends Attack {

	public Abime() {
		super((short) Short.MAX_VALUE, (float) 0.30, (short) 5, Type.Sol);
	}
}