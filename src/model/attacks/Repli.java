package model.attacks;

import model.Attack;
import model.Feature;
import model.Type;

public class Repli extends Attack {

	public Repli() {
		super((short) 1, (float) Float.MAX_VALUE, (short) 40, Type.Eau,Feature.Defense,true);
	}
}