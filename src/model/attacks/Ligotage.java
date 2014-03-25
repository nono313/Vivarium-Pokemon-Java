package model.attacks;

import model.Attack;
import model.Type;

public class Ligotage extends Attack {

	public Ligotage() {
		super((short) 15, (float) 0.85, (short) 20, Type.Normal);
	}
}