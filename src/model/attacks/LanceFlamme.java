package model.attacks;

import model.Attack;
import model.Type;

public class LanceFlamme extends Attack{
	public LanceFlamme(){
		super((short) 90, (float) 1, (short) 15, Type.Feu );
	}
}
