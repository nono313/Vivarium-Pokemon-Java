package model.pokemons;

import model.BackgroundType;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;
import model.attacks.Lilliput;
import model.attacks.Metronome;
import model.attacks.Torgnoles;

public class Melodelfe extends Pokemon{
	public Melodelfe(Environment env, Coord c, Movement m) {
		super(env,c,m,
				(short)95, (short) 4,(short) 5,
			(short) 70,(short) 73,(short) 60, (short) 85, 
			(short) 16, 129, 0, CurveType.Average ,
			(short) Short.MAX_VALUE, null);
		attacks.put(new Torgnoles(),(short) 1);
		attacks.put(new Lilliput(),(short) 1);
		attacks.put(new Metronome(),(short) 1);
		types.add(Type.Normal);
		accessibleBackground.add(BackgroundType.Earth );
		updateAttacksAvailable();
	}
	public Melodelfe(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		getValuesFrom(poke);
	}
}