package model.items;

import java.util.ArrayList;
import java.util.List;

import model.Coord;
import model.Environment;
import model.Pierre;

public class Pierre_eau extends Pierre{

	private static List<String> evolveWater=new ArrayList<String>(); 
	static{
		//evolveWater.add("Tetarte");
		//evolveWater.add("Kokiyas");
		//evolveWater.add("Evoli");
		//evolveWater.add("Stari");
	}
	public Pierre_eau(Coord position, Environment env) {
		super(position, env, evolveWater);
	}
	
}
