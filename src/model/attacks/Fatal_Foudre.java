package model.attacks;

import model.Attack;
import model.Type;

public class Fatal_Foudre extends Attack {

	public Fatal_Foudre() {
		super((short) 120, (float) 0.70, (short) 10, Type.Electricite);
	}
}