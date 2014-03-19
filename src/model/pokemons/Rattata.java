package model.pokemons;

import model.BackgroundType;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;
import model.attacks.*;
public class Rattata extends Pokemon {
	public Rattata(Environment env, Coord c, Movement m) {
		super(env,c,m,(short)30, (short) 1,(short) 3,
				(short) 56,(short) 35,(short) 72, (short) 25,
				(short) 5, 57, 
				0, CurveType.Fast , (short)20, Rattatac.class);
		attacks.put(new Charge(),(short) 1);
		attacks.put(new Mimi_Queue(),(short) 1);
		attacks.put(new Vive_Attaque(),(short) 7);
		attacks.put(new Croc_de_Mort(),(short) 14);
		//attacks.put(new Puissance(),(short) 23);
		attacks.put(new Croc_Fatal(),(short) 34);
		types.add(Type.Normal);
		accessibleBackground.add(BackgroundType.Earth );
		updateAttacksAvailable();
	}	
	public static boolean isOriginalForm(){
		return true;
	}
	public Rattata(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		getValuesFrom(poke);

	}
}