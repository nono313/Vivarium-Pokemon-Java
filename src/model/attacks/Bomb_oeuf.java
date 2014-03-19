package model.attacks;

import model.Attack;
import model.Type;

public class Bomb_oeuf extends Attack {

	public Bomb_oeuf() {
		super((short) 100, (float) 0.75, (short) 10, Type.Normal);
	}
}