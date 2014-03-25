package model.attacks;

import model.Attack;
import model.Type;

public class Furie extends Attack {

	public Furie() {
		super((short) 15, (float) 0.85, (short) 20, Type.Normal);
	}
}