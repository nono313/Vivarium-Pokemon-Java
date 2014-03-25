package model.attacks;

import model.Attack;
import model.Type;

public class Eclair extends Attack {

	public Eclair() {
		super((short) 40, (float) 1., (short) 30, Type.Electricite);
	}
}