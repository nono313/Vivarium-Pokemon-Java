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
public class Fantominus extends Pokemon {
	public Fantominus (Environment env, Coord c, Movement m) {
		super(env,c,m,
				(short)30, (short) 4,(short) 6,
				(short) 35,(short) 30,(short) 80, (short) 100,
				(short) 1, 
				95, 0, CurveType.Average ,
				(short) 25, Spectrum.class);
		attacks.put(new Lechouille(),(short) 1);
		attacks.put(new Tenebres(),(short) 1);
		attacks.put(new Devoreve(),(short) 35);
		types.add(Type.Spectre);
		types.add(Type.Poison);
		accessibleBackground.add(BackgroundType.Earth );
		updateAttacksAvailable();
	}	
	public static boolean isOriginalForm(){
		return true;
	}
	public Fantominus(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		cloneValuesFrom(poke);

	}
}