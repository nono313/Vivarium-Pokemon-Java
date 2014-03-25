package model.attacks;

import model.Attack;
import model.Feature;
import model.Type;

public class Reflet extends Attack {

	public Reflet() {
		super((short) -1, (float) Float.MAX_VALUE, (short) 15, Type.Normal,Feature.Accuracy);
	}
}