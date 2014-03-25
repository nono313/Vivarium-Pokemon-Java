package model.pokemons;

import model.BackgroundType;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;
import model.attacks.Coup_d_Boule;
import model.attacks.Frenesie;
import model.attacks.Groz_Yeux;
import model.attacks.Mania;
import model.attacks.Massd_Os;
import model.attacks.Osmerang;
import model.attacks.Rugissement;

public class Ossatueur extends Pokemon {
	public Ossatueur(Environment env, Coord c, Movement m) {
		super(env,c,m,
				(short)60, (short) 3,(short) 5,
			(short) 80,(short) 110,(short) 45, (short) 50, 
			(short) 28, 124, 0, CurveType.Slow ,
			(short) Short.MAX_VALUE, null);
		types.add(Type.Sol);
		attacks.put(new Massd_Os(),(short) 1);
		attacks.put(new Rugissement(),(short) 1);
		attacks.put(new Coup_d_Boule(),(short) 1);
		attacks.put(new Groz_Yeux(),(short) 1);
		attacks.put(new Mania(),(short) 41);
		attacks.put(new Osmerang(),(short) 48);
		attacks.put(new Frenesie(),(short) 55);
		types.add(Type.Sol);
		accessibleBackground.add(BackgroundType.Earth );
		updateAttacksAvailable();
	}	
	public Ossatueur(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		cloneValuesFrom(poke);
	}
}