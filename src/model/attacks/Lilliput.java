package model.attacks;

import model.Attack;
import model.Feature;
import model.Type;

public class Lilliput extends Attack {

	public Lilliput() {
		super((short) -1, (float) Float.MAX_VALUE, (short) 20, Type.Normal,Feature.Accuracy);
	}
}