package model.attacks;

import model.Attack;
import model.Type;

public class Lance_Flamme extends Attack {

	public Lance_Flamme() {
		super((short) 95, (float) 1., (short) 15, Type.Feu);
	}
}