package model.attacks;

import model.Attack;
import model.Type;

public class Ecrasement extends Attack {

	public Ecrasement() {
		super((short) 65, (float) 1., (short) 20, Type.Normal);
	}
}