package model.attacks;

import model.Attack;
import model.Type;

public class Flammeche extends Attack {

	public Flammeche() {
		super((short) 40, (float) 1., (short) 25, Type.Feu);
	}
}