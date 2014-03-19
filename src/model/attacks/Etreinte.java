package model.attacks;

import model.Attack;
import model.Type;

public class Etreinte extends Attack {

	public Etreinte() {
		super((short) 15, (float) 0.75, (short) 20, Type.Normal);
	}
}