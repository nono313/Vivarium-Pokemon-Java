package model.attacks;

import model.Attack;
import model.Type;

public class Pied_Saute extends Attack {

	public Pied_Saute() {
		super((short) 70, (float) 0.95, (short) 25, Type.Combat);
	}
}