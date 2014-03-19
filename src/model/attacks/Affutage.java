package model.attacks;

import model.Attack;
import model.Feature;
import model.Type;

public class Affutage extends Attack {

	public Affutage() {
		super((short) 1, (float) Float.MAX_VALUE, (short) 30, Type.Normal,Feature.Attack,true);
	}
}