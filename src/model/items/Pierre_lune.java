package model.items;

import java.util.ArrayList;
import java.util.List;

import model.Coord;
import model.Environment;
import model.Pierre;

public class Pierre_lune extends Pierre{

	private static List<String> evolveMoon=new ArrayList<String>(); 
	static{
		evolveMoon.add("Melofee");
		//evolveMoon.add("Nidorina");
		//evolveMoon.add("Nidorino");
		//evolveMoon.add("Rondoudou");
	}
	public Pierre_lune(Coord position, Environment env) {
		super(position, env, evolveMoon);
	}
	
}
