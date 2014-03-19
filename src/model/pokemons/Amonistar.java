package model.pokemons;

import model.BackgroundType;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;
import model.attacks.Groz_Yeux;
import model.attacks.Hydrocanon;
import model.attacks.Koud_Korne;
import model.attacks.Picanon;
import model.attacks.Pistolet_a_O;
import model.attacks.Repli;

public class Amonistar extends Pokemon {
	public Amonistar(Environment env, Coord c, Movement m) {
		super(env,c,m,
				(short)70, (short) 3,(short) 5,
				(short) 60,(short) 125,(short) 55, (short) 115,
				(short) 40, 199, 0, CurveType.Average , 
				(short) Short.MAX_VALUE, null);
		attacks.put(new Pistolet_a_O(),(short) 1);
		attacks.put(new Repli(),(short) 1);
		attacks.put(new Koud_Korne(),(short) 1);
		attacks.put(new Groz_Yeux(),(short) 1);
		attacks.put(new Picanon(),(short) 44);
		attacks.put(new Hydrocanon(),(short) 49);
		types.add(Type.Roche);
		types.add(Type.Eau);
		accessibleBackground.add(BackgroundType.Earth );
		accessibleBackground.add(BackgroundType.Water );
		updateAttacksAvailable();
	}
	public Amonistar(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		getValuesFrom(poke);

	}
	
}