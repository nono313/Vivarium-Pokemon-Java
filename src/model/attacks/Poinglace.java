package model.attacks;

import model.Attack;
import model.Type;

public class Poinglace extends Attack {

	public Poinglace() {
		super((short) 75, (float) 1., (short) 15, Type.Glace);
	}
}