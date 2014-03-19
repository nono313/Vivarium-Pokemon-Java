package model.attacks;

import model.Attack;
import model.Type;

public class Blizzard extends Attack {

	public Blizzard() {
		super((short) 120, (float) 0.895, (short) 5, Type.Glace);
	}
}