package model.attacks;

import model.Attack;
import model.Type;

public class Charge extends Attack {

	public Charge() {
		super((short) 35, (float) 0.95, (short) 30, Type.Normal);
	}
}