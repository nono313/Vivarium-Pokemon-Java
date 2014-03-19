package model.attacks;

import model.Attack;
import model.Type;

public class Force extends Attack {

	public Force() {
		super((short) 80, (float) 1., (short) 15, Type.Normal);
	}
}