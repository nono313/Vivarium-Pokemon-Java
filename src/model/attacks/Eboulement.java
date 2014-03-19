package model.attacks;

import model.Attack;
import model.Type;

public class Eboulement extends Attack {

	public Eboulement() {
		super((short) 75, (float) 0.90, (short) 10, Type.Roche);
	}
}