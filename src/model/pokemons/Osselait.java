package model.pokemons;

import model.BackgroundType;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;

public class Osselait extends Pokemon {
	public Osselait(Environment env, Coord c, Movement m) {
		super(env,c,m,(short)50, (short) 1,(short) 3,
			(short) 50,(short) 95,(short) 35, (short) 40, 
			(short) 1, 87,0, CurveType.Slow ,
			(short) 28, Ossatueur.class);
		types.add(Type.Sol);
		accessibleBackground.add(BackgroundType.Earth );
		updateAttacksAvailable();
	}	
	public static boolean isOriginalForm(){
		return true;
	}
	public Osselait(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		cloneValuesFrom(poke);
	}
}