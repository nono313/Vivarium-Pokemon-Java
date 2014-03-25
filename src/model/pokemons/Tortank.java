package model.pokemons;

import model.BackgroundType;
import model.attacks.*;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;

public class Tortank extends Pokemon {
	public Tortank(Environment env, Coord c, Movement m) {
		super(env, c, m, 
				(short)59, (short)5, (short)7,	// HP, hauteur, vision 
				(short)83, (short)100, (short)78, (short)85,		//stats 
				(short) 36, 	//level
				210, 0, CurveType.Parabolic,	//experience 
				(short)Short.MAX_VALUE, null);	//evolution
		attacks.put(new Charge(),(short) 1);
		attacks.put(new Mimi_Queue(), (short) 1);
		attacks.put(new Ecume(), (short) 1);
		attacks.put(new Pistolet_a_O(), (short) 1);
		attacks.put(new Morsure(),(short) 1);
		attacks.put(new Repli(),(short) 1);
		attacks.put(new Coud_Krane(),(short) 42);
		attacks.put(new Hydrocanon(),(short) 52);
		
		types.add(Type.Eau);
		updateAttacksAvailable();
		accessibleBackground.add(BackgroundType.Earth);
		accessibleBackground.add(BackgroundType.Water);
	}
	public Tortank(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		cloneValuesFrom(poke);

	}
}
