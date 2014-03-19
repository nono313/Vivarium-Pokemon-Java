package model.pokemons;

import model.BackgroundType;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;
import model.attacks.Choc_Mental;
import model.attacks.Psyko;
import model.attacks.Rafale_Psy;
import model.attacks.Soin;

public class Alakazam extends Pokemon {
	public Alakazam(Environment env, Coord c, Movement m) {
		super(env,c,m,(short)55, (short) 5,(short) 7,
			(short) 50,(short) 45,(short) 120, (short) 135,
			(short) 30,186, 0, CurveType.Average ,
			(short) Short.MAX_VALUE, null);
		//attacks.put(new Teleport(),(short) 1);
		attacks.put(new Choc_Mental(),(short) 1);
		//attacks.put(new Entrave(),(short) 20);
		attacks.put(new Rafale_Psy(),(short) 27);
		attacks.put(new Soin(),(short) 31);
		attacks.put(new Psyko(),(short) 38);
		//attacks.put(new Protection(),(short) 42);
		types.add(Type.Psy);
		accessibleBackground.add(BackgroundType.Earth );
		updateAttacksAvailable();
	}
	public Alakazam(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		getValuesFrom(poke);
	}
}