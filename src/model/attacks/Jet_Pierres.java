package model.attacks;

import model.Attack;
import model.Type;

public class Jet_Pierres extends Attack {

	public Jet_Pierres() {
		super((short) 50, (float) 0.65, (short) 15, Type.Roche);
	}
}