package model.attacks;

import model.Attack;
import model.Type;

public class Tonnerre extends Attack {

	public Tonnerre() {
		super((short) 95, (float) 1., (short) 15, Type.Electricite);
	}
}