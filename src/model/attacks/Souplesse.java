package model.attacks;

import model.Attack;
import model.Type;

public class Souplesse extends Attack {

	public Souplesse() {
		super((short) 80, (float) 0.75, (short) 20, Type.Normal);
	}
}