package model.attacks;

import model.Attack;
import model.Feature;
import model.Type;

public class Brouillard extends Attack {

	public Brouillard() {
		super((short) -1, (float) 1., (short) 20, Type.Normal,Feature.Accuracy);
	}
}