package model.attacks;

import model.Attack;
import model.Type;

public class Mawashi_Geri extends Attack {

	public Mawashi_Geri() {
		super((short) 60, (float) 0.85, (short) 15, Type.Combat);
	}
}