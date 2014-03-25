package model.attacks;

import model.Attack;
import model.Type;

public class Poing_Eclair extends Attack {

	public Poing_Eclair() {
		super((short) 75, (float) 1., (short) 15, Type.Electricite);
	}
}