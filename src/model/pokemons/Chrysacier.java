package model.pokemons;

import model.BackgroundType;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;
import model.attacks.Armure;

public class Chrysacier extends Pokemon {
	public Chrysacier(Environment env, Coord c, Movement m) {
		super(env,c,m,(short)50, (short) 2,(short) 4,
			(short) 20,(short) 55,(short) 25, (short) 30,
			(short) 7, 72, 0, CurveType.Fast ,
			(short) 10, Papilusion.class);
		attacks.put(new Armure(),(short) 7);
		types.add(Type.Insecte);
		accessibleBackground.add(BackgroundType.Earth );
		updateAttacksAvailable();
	}	
	public Chrysacier(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		getValuesFrom(poke);
	}
}