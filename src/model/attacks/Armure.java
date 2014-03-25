package model.attacks;

import model.Attack;
import model.Feature;
import model.Type;

public class Armure extends Attack {

	public Armure() {
		super((short) 1, (float) Float.MAX_VALUE, (short) 40, Type.Poison,Feature.Defense,true);
	}
}
