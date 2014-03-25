package model.attacks;

import model.Attack;
import model.Type;

public class Puredpois extends Attack {

	public Puredpois() {
		super((short) 20, (float) 0.70, (short) 20, Type.Poison);
	}
}