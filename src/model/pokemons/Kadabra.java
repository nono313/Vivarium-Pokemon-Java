package model.pokemons;

import model.BackgroundType;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;
import model.attacks.*;

public class Kadabra extends Pokemon {
	public Kadabra(Environment env, Coord c, Movement m) {
		super(env,c,m,
				(short)40, (short) 4,(short) 6,
				(short) 35,(short) 30,(short) 105, (short) 120,
				(short) 20, 145, 0, CurveType.Average , 
				(short) Short.MAX_VALUE, Alakazam.class);
		//attacks.put(new Teleport(),(short) 1);
		attacks.put(new Choc_Mental(),(short) 1);
		attacks.put(new Telekinesie(),(short) 1);
		//attacks.put(new Entrave(),(short) 20);
		attacks.put(new Rafale_Psy(),(short) 27);
		attacks.put(new Soin(),(short) 31);
		attacks.put(new Psyko(),(short) 38);
		//attacks.put(new Protection(),(short) 42);
		types.add(Type.Psy);
		accessibleBackground.add(BackgroundType.Earth );
		updateAttacksAvailable();
	}	
	public Kadabra(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		cloneValuesFrom(poke);
	}

}