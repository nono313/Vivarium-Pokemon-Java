package model.attacks;

import model.Attack;
import model.Type;

public class Lechouille extends Attack {

	public Lechouille() {
		super((short) 20, (float) 1., (short) 30, Type.Spectre);
	}
}