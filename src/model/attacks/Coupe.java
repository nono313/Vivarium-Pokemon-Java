package model.attacks;

import model.Attack;
import model.Type;

public class Coupe extends Attack {

	public Coupe() {
		super((short) 50, (float) 0.95, (short) 30, Type.Normal);
	}
}