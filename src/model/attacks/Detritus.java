package model.attacks;

import model.Attack;
import model.Type;

public class Detritus extends Attack {

	public Detritus() {
		super((short) 65, (float) 1., (short) 20, Type.Poison);
	}
}