package model.attacks;

import model.Attack;
import model.Type;

public class Frenesie extends Attack {

	public Frenesie() {
		super((short) 20, (float) 1., (short) 20, Type.Normal);
	}
}