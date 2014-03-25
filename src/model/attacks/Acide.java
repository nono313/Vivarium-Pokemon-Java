package model.attacks;

import model.Attack;
import model.Type;

public class Acide extends Attack {

	public Acide() {
		super((short) 40, (float) 1., (short) 30, Type.Poison);
	}
}