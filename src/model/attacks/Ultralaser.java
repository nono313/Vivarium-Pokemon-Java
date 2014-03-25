package model.attacks;

import model.Attack;
import model.Type;

public class Ultralaser extends Attack {

	public Ultralaser() {
		super((short) 150, (float) 0.90, (short) 5, Type.Normal);
	}
}