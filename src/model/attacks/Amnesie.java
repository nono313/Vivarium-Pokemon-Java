package model.attacks;

import model.Attack;
import model.Feature;
import model.Type;

public class Amnesie extends Attack {

	public Amnesie() {
		super((short) 2, (float) Float.MAX_VALUE, (short) 20, Type.Psy,Feature.Special,true);
	}
}