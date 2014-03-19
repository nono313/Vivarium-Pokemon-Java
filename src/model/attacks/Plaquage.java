package model.attacks;

import model.Attack;
import model.Type;

public class Plaquage extends Attack {

	public Plaquage() {
		super((short) 85, (float) 1., (short) 15, Type.Normal);
	}
}