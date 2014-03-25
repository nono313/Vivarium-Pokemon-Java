package model.items;

import java.util.ArrayList;
import java.util.List;

import model.Coord;
import model.Environment;

public class Pierre_plante extends Pierre{

	private static List<String> evolvePlant=new ArrayList<String>();
	
	static{
		evolvePlant.add("Boustiflor");
		//evolvePlant.add("Ortide");
		//evolvePlant.add("Noeunoeuf");
	}
	public Pierre_plante(Coord position, Environment env) {
		super(position, env, evolvePlant);
	}
	
}
