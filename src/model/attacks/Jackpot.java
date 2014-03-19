package model.attacks;

import model.Attack;
import model.Type;

public class Jackpot extends Attack {

	public Jackpot() {
		super((short) 40, (float) 1., (short) 20, Type.Normal);
	}
}