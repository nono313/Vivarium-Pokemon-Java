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

public class Amonita extends Pokemon {
	public Amonita(Environment env, Coord c, Movement m) {
		super(env,c,m,
				(short)35, (short) 1,(short) 3,
				(short) 40,(short) 100,(short) 35, (short) 90,
				(short) 30, 99, 0, CurveType.Average , 
				(short) 40, Amonistar.class);
		attacks.put(new Pistolet_a_O(),(short) 1);
		attacks.put(new Repli(),(short) 1);
		attacks.put(new Koud_Korne(),(short) 34);
		attacks.put(new Groz_Yeux(),(short) 39);
		attacks.put(new Picanon(),(short) 46);
		attacks.put(new Hydrocanon(),(short) 53);
		types.add(Type.Roche);
		types.add(Type.Eau);
		accessibleBackground.add(BackgroundType.Earth );
		accessibleBackground.add(BackgroundType.Water );
		updateAttacksAvailable();
	}	
	public static boolean isOriginalForm(){
		return true;
	}
}