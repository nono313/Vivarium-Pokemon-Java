package model.attacks;

import model.Attack;
import model.Feature;
import model.Type;

public class Groz_Yeux extends Attack {

	public Groz_Yeux() {
		super((short) -1, (float) 1., (short) 30, Type.Normal,Feature.Defense);
	}
}