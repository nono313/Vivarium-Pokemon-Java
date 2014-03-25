package model.attacks;

import model.Attack;
import model.Type;

public class Tranche extends Attack {

	public Tranche() {
		super((short) 70, (float) 1., (short) 20, Type.Normal);
	}
}