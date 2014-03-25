package model.attacks;

import model.Attack;
import model.Feature;
import model.Type;

public class Secretion extends Attack {

	public Secretion() {
		super((short) -1, (float) 0.95, (short) 40, Type.Insecte,Feature.Speed);
	}
}