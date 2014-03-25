package model.attacks;

import model.Attack;
import model.Type;

public class Poing_de_Feu extends Attack {

	public Poing_de_Feu() {
		super((short) 75, (float) 1., (short) 15, Type.Feu);
	}
}