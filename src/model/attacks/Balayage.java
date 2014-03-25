package model.attacks;

import model.Attack;
import model.Type;

public class Balayage extends Attack {

	public Balayage() {
		super((short) 50, (float) 1., (short) 20, Type.Combat);
	}
}