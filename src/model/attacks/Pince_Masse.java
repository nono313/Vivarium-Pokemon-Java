package model.attacks;

import model.Attack;
import model.Type;

public class Pince_Masse extends Attack {

	public Pince_Masse() {
		super((short) 90, (float) 0.85, (short) 10, Type.Eau);
	}
}