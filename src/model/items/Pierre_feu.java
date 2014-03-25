package model.items;

import java.util.ArrayList;
import java.util.List;

import model.Coord;
import model.Environment;

public class Pierre_feu extends Pierre{

	private static List<String> evolveFire=new ArrayList<String>(); 
	static{
		//evolveFire.add("Goupix");
		//evolveFire.add("Caninos");
		//evolveFire.add("Evoli");
	}
	public Pierre_feu(Coord position, Environment env) {
		super(position, env, evolveFire);
	}
	
}

