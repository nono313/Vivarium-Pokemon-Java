package model.pokemons;

import model.BackgroundType;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;
import model.attacks.*;


public class Carabaffe extends Pokemon {
	public Carabaffe(Environment env, Coord c, Movement m) {
		super(env, c, m, 
				(short)59, (short)3, (short)5,	// HP, hauteur, vision 
				(short)63, (short)80, (short)58, (short)65,		//stats 
				(short) 16, 	//level
				143, 0, CurveType.Parabolic,	//experience 
				(short)36, Tortank.class);	//evolution
		attacks.put(new Charge(),(short) 1);
		attacks.put(new Mimi_Queue(), (short) 1);
		attacks.put(new Ecume(), (short) 1);
		attacks.put(new Pistolet_a_O(), (short) 1);
		attacks.put(new Morsure(),(short) 24);
		attacks.put(new Repli(),(short) 31);
		attacks.put(new Coud_Krane(),(short) 39);
		attacks.put(new Hydrocanon(),(short) 47);
		
		types.add(Type.Eau);
		updateAttacksAvailable();
		accessibleBackground.add(BackgroundType.Earth);
		accessibleBackground.add(BackgroundType.Water);
	}
	public Carabaffe(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		cloneValuesFrom(poke);

	}
}
