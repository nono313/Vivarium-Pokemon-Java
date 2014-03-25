package model.pokemons;

import model.BackgroundType;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;
import model.attacks.Acide;
import model.attacks.Croissance;
import model.attacks.Fouet_Lianes;
import model.attacks.Ligotage;
import model.attacks.Souplesse;
import model.attacks.Tranch_Herbe;

public class Chetiflor extends Pokemon {
	public Chetiflor(Environment env, Coord c, Movement m) {
		super(env,c,m,(short)50, (short) 2,(short) 4,
			(short) 75,(short) 35,(short) 40, (short) 70,
			(short) 12,84, 0, CurveType.Average ,
			(short) 21, Boustiflor.class);
		attacks.put(new Fouet_Lianes(),(short) 1);
		attacks.put(new Croissance(),(short) 1);
		attacks.put(new Ligotage(),(short) 13);
		attacks.put(new Acide(),(short) 26);
		attacks.put(new Tranch_Herbe(),(short) 33);
		attacks.put(new Souplesse(),(short) 42);
		types.add(Type.Plante);
		types.add(Type.Poison);
		accessibleBackground.add(BackgroundType.Earth );
		updateAttacksAvailable();
	}	
	public static boolean isOriginalForm(){
		return true;
	}
	public Chetiflor(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		cloneValuesFrom(poke);
	}
}