package model.attacks;

import model.Attack;
import model.Feature;
import model.Type;

public class Grincement extends Attack {

	public Grincement() {
		super((short) -2, (float) 0.85, (short) 40, Type.Normal,Feature.Defense);
	}
}