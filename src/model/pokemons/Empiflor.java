package model.pokemons;

import model.BackgroundType;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;
import model.attacks.*;

public class Empiflor extends Pokemon {
	public Empiflor(Environment env, Coord c, Movement m) {
		super(env,c,m,(short)80, (short) 6,(short) 8,
			(short) 105,(short) 65,(short) 70, (short) 100,
			(short) 40,191, 0, CurveType.Average ,
			(short) Short.MAX_VALUE, null);
		attacks.put(new Ligotage(),(short) 1);
		//attacks.put(new Poudre_Toxik(),(short) 1);
		//attacks.put(new Poudre_Dodo(),(short) 1);
		types.add(Type.Plante);
		types.add(Type.Poison);
		accessibleBackground.add(BackgroundType.Earth );
		updateAttacksAvailable();
	}
	public Empiflor(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		getValuesFrom(poke);
	}
}