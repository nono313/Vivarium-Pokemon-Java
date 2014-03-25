package model.attacks;

import model.Attack;
import model.Type;

public class Deflagration extends Attack {

	public Deflagration() {
		super((short) 120, (float) 0.85, (short) 5, Type.Feu);
	}
}