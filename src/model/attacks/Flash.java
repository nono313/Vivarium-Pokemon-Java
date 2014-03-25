package model.attacks;

import model.Attack;
import model.Feature;
import model.Type;

public class Flash extends Attack {

	public Flash() {
		super((short) -1, (float) 0.7, (short) 20, Type.Normal,Feature.Accuracy);
	}
}