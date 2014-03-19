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
public class Spectrum extends Pokemon {
	public Spectrum(Environment env, Coord c, Movement m) {
		super(env,c,m,
				(short)45, (short) 5,(short) 7,
				(short) 50,(short) 45,(short) 95, (short) 115,
				(short) 25,
				126, 0, CurveType.Average, 
				(short) 36, Ectoplasma.class);
		attacks.put(new Lechouille(),(short) 1);
		attacks.put(new Tenebres(),(short) 1);
		attacks.put(new Devoreve(),(short) 38);
		types.add(Type.Spectre);
		types.add(Type.Poison);
		accessibleBackground.add(BackgroundType.Earth );
		updateAttacksAvailable();
	}
	public Spectrum(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		getValuesFrom(poke);

	}
}