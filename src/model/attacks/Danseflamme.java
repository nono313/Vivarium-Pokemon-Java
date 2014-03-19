package model.attacks;

import model.Attack;
import model.Type;

public class Danseflamme extends Attack {

	public Danseflamme() {
		super((short) 15, (float) 0.70, (short) 15, Type.Feu);
	}
}