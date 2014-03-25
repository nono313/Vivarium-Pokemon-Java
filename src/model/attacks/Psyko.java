package model.attacks;

import model.Attack;
import model.Type;

public class Psyko extends Attack {

	public Psyko() {
		super((short) 90, (float) 1., (short) 10, Type.Psy);
	}
}