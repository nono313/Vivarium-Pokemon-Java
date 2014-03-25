package model.pokemons;

import model.BackgroundType;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;
import model.attacks.Charge;
import model.attacks.Secretion;

public class Chenipan extends Pokemon {
	public Chenipan(Environment env, Coord c, Movement m) {
		super(env,c,m,(short)45, (short) 1,(short) 3,
			(short) 30,(short) 35,(short) 45, (short) 20,
			(short) 5,
			(short) 53, 0, CurveType.Fast ,
			(short) 7, Chrysacier.class);
		attacks.put(new Charge(),(short) 1);
		attacks.put(new Secretion(),(short) 1);
		types.add(Type.Insecte);
		accessibleBackground.add(BackgroundType.Earth );
		updateAttacksAvailable();
	}	
	public static boolean isOriginalForm(){
		return true;
	}
	public Chenipan(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		cloneValuesFrom(poke);
	}
}