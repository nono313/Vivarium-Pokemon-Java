package model.attacks;

import model.Attack;
import model.Type;

public class Dard_Venin extends Attack {

	public Dard_Venin() {
		super((short) 15, (float) 1., (short) 35, Type.Poison);
	}
}