package model.attacks;

import model.Attack;
import model.Type;

public class Croc_de_Mort extends Attack {

	public Croc_de_Mort() {
		super((short) 80, (float) 0.90, (short) 15, Type.Normal);
	}
}