package model.attacks;

import model.Attack;
import model.Type;

public class Morsure extends Attack{
	public Morsure(){
		super((short)60, (float)1.0,(short)25, Type.Normal);
	}
}