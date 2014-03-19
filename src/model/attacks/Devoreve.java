package model.attacks;

import model.Attack;
import model.Type;

public class Devoreve extends Attack {

	public Devoreve() {
		super((short) 100, (float) 1., (short) 15, Type.Psy);
	}
}