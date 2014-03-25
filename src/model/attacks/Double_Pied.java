package model.attacks;

import model.Attack;
import model.Type;

public class Double_Pied extends Attack {

	public Double_Pied() {
		super((short) 60, (float) 1., (short) 30, Type.Combat);
	}
}