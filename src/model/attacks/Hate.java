package model.attacks;

import model.Attack;
import model.Feature;
import model.Type;

public class Hate extends Attack {

	public Hate() {
		super((short) 2, (float) Float.MAX_VALUE, (short) 30, Type.Psy,Feature.Speed,true);
	}
}