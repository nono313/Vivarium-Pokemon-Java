package model.attacks;

import model.Attack;
import model.Feature;
import model.Type;

public class Acidarmure extends Attack {

	public Acidarmure() {
		super((short) 2, (float) Float.MAX_VALUE, (short) 40, Type.Poison,Feature.Defense,true);
	}
}