package model.attacks;

import model.Attack;
import model.Type;

public class Coup_d_Boule extends Attack {

	public Coup_d_Boule() {
		super((short) 70, (float) 1., (short) 15, Type.Normal);
	}
}