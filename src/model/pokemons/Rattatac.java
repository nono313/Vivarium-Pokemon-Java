package model.pokemons;

import model.BackgroundType;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;
import model.attacks.*;
public class Rattatac extends Pokemon {
	public Rattatac(Environment env, Coord c, Movement m) {
		super(env,c,m,(short)55, (short) 2,(short) 4,
				(short) 81,(short) 60,(short) 97, (short) 50,
				(short) 20,116, 0, CurveType.Average , (short) Short.MAX_VALUE, null);
		attacks.put(new Charge(),(short) 1);
		attacks.put(new Mimi_Queue(),(short) 1);
		attacks.put(new Vive_Attaque(),(short) 1);
		attacks.put(new Croc_de_Mort(),(short) 14);
		//attacks.put(new Puissance(),(short) 27);
		attacks.put(new Croc_Fatal(),(short) 41);
		types.add(Type.Normal);
		accessibleBackground.add(BackgroundType.Earth );
		updateAttacksAvailable();
	}
	public Rattatac(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		cloneValuesFrom(poke);

	}
}