package model.pokemons;

import model.BackgroundType;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;

public class Abra extends Pokemon {
	public Abra(Environment env, Coord c, Movement m) {
		super(env,c,m,(short)25, (short) 3,(short) 5,
				(short) 20,(short) 15,(short) 90, (short) 105,
				(short) 10,75, 
				0, CurveType.Average , 
				(short) 16, Kadabra.class);
		types.add(Type.Psy);
		accessibleBackground.add(BackgroundType.Earth );
		updateAttacksAvailable();
	}	
	public static boolean isOriginalForm(){
		return true;
	}
	public Abra(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		cloneValuesFrom(poke);

	}
}