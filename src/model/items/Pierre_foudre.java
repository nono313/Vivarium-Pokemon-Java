package model.items;

import java.util.List;

import model.Coord;
import model.Environment;

public class Pierre_foudre extends Pierre{

	private static List<String> evolveThunder;
	static{
		//evolveThunder.add("Pikachu");
		//evolveThunder.add("Evoli");
	}
	public Pierre_foudre(Coord position, Environment env) {
		super(position, env, evolveThunder);
	}
	
}
