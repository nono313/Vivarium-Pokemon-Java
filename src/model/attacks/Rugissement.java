package model.attacks;

import model.Attack;
import model.Feature;
import model.Type;

public class Rugissement extends Attack{
	public Rugissement(){
		super((short)-1, (float)1., (short)40, Type.Normal, Feature.Attack,false);
	}
}
