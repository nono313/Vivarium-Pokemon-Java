package model.attacks;

import model.Attack;
import model.Type;

public class Hydrocanon extends Attack{
	public Hydrocanon(){
		super((short)110, (float)0.8,(short)5, Type.Eau);
	}
}