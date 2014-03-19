package model.attacks;

import model.Attack;
import model.Type;

public class Lutte extends Attack {
	private static Lutte instance;
	
	private Lutte(){
		super((short) 50, (float) 1, (short) Short.MAX_VALUE, Type.Normal );
	}
	public static Lutte getInstance() {
		if(instance == null) {
			instance = new Lutte();
		}
		return instance;
	}
}
