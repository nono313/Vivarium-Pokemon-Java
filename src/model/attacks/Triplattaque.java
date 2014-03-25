package model.attacks;

import model.Attack;
import model.Type;

public class Triplattaque extends Attack {

	public Triplattaque() {
		super((short) 80, (float) 1., (short) 10, Type.Normal);
	}
}