package model.attacks;

import model.Attack;
import model.Feature;
import model.Type;

public class Bouclier extends Attack {

	public Bouclier() {
		super((short) 2, (float)1., (short) 30, Type.Psy,Feature.Defense,true);
	}
}