package model.attacks;

import model.Attack;
import model.Feature;
import model.Type;

public class Jet_de_Sable extends Attack {

	public Jet_de_Sable() {
		super((short) -1, (float) 1., (short) 15, Type.Normal,Feature.Accuracy);
	}
}