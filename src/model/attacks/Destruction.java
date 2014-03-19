package model.attacks;

import model.Attack;
import model.Type;

public class Destruction extends Attack {

	public Destruction() {
		super((short) 200, (float) 1., (short) 5, Type.Normal);
	}
}