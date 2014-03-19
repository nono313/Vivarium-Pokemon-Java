package model.attacks;

import model.Attack;
import model.Type;

public class Tunnel extends Attack {

	public Tunnel() {
		super((short) 100, (float) 1., (short) 10, Type.Sol);
	}
}