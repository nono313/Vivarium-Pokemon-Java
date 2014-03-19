package model.attacks;

import model.Attack;
import model.Feature;
import model.Type;

public class Mimi_Queue extends Attack {

	public Mimi_Queue() {
		super((short) -1, (float) 1., (short) 30, Type.Normal,Feature.Defense);
	}
}