package model.pokemons;

import model.BackgroundType;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;
import model.attacks.Bec_Vrille;
import model.attacks.Eclair;
import model.attacks.Fatal_Foudre;
import model.attacks.Hate;

public class Electhor extends Pokemon {
	public Electhor(Environment env, Coord c, Movement m) {
		super(env,c,m,(short)90, (short) 5,(short) 7,
				(short) 90,(short) 85,(short) 100, (short) 125,
				(short) 50, 216, 0, 
				CurveType.Slow , (short) Short.MAX_VALUE, null);
		attacks.put(new Eclair(),(short) 1);
		attacks.put(new Bec_Vrille(),(short) 1);
		attacks.put(new Fatal_Foudre(),(short) 51);
		attacks.put(new Hate(),(short) 55);
		types.add(Type.Electricite);
		types.add(Type.Vol);
		accessibleBackground.add(BackgroundType.Earth );
		updateAttacksAvailable();
	}	
	public static boolean isOriginalForm(){
		return true;
	}
	public Electhor(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		cloneValuesFrom(poke);

	}
}