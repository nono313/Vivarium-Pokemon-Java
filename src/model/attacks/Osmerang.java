package model.attacks;

import model.Attack;
import model.Type;

public class Osmerang extends Attack {

	public Osmerang() {
		super((short) 100, (float) 0.90, (short) 10, Type.Sol);
	}
}