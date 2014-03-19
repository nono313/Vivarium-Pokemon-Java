package model.attacks;

import model.Attack;
import model.Feature;
import model.Type;

public class Croissance extends Attack {

	public Croissance() {
		super((short) 1, (float) Float.MAX_VALUE, (short) 40, Type.Normal,Feature.Special,true);
	}
}