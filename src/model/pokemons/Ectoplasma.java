package model.pokemons;

import model.BackgroundType;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;
import model.attacks.Devoreve;
import model.attacks.Lechouille;
import model.attacks.Tenebres;
public class Ectoplasma extends Pokemon {
	public Ectoplasma(Environment env, Coord c, Movement m) {
		super(env,c,m,
				(short)60, (short) 5,(short) 7,
				(short) 65,(short) 60,(short) 110, (short) 130,
				(short) 36,
				190, 0, CurveType.Average , 
				Short.MAX_VALUE, null);
		attacks.put(new Lechouille(),(short) 1);
		attacks.put(new Tenebres(),(short) 1);
		attacks.put(new Devoreve(),(short) 38);
		types.add(Type.Spectre);
		types.add(Type.Poison);
		accessibleBackground.add(BackgroundType.Earth );
		updateAttacksAvailable();
	}
	public Ectoplasma(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		getValuesFrom(poke);

	}
}