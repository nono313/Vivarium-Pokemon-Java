package model.attacks;

import model.Attack;
import model.Type;

public class Laser_Glace extends Attack {

	public Laser_Glace() {
		super((short) 95, (float) 1., (short) 10, Type.Glace);
	}
}