package model.attacks;

import model.Attack;
import model.Feature;
import model.Type;

public class Telekinesie extends Attack {

	public Telekinesie() {
		super((short) -1, (float) 0.80, (short) 15, Type.Psy,Feature.Accuracy);
	}
}