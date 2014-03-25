package model.pokemons;

import model.BackgroundType;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;
import model.attacks.*;

public class Papilusion extends Pokemon {
	public Papilusion(Environment env, Coord c, Movement m) {
		super(env,c,m,(short)60, (short) 4,(short) 6,
			(short) 45,(short) 50,(short) 70, (short) 80,
			(short) 15,160, 0, CurveType.Average ,
			(short) Short.MAX_VALUE, null);
		attacks.put(new Choc_Mental(),(short) 10);
		/*attacks.put(new Poudre_Toxik(),(short) 13);
		attacks.put(new Para_Spore(),(short) 14);
		attacks.put(new Poudre_Dodo(),(short) 15);
		attacks.put(new Ultrason(),(short) 18);
		attacks.put(new Cyclone(),(short) 23);*/
		attacks.put(new Tornade(),(short) 28);
		attacks.put(new Rafale_Psy(),(short) 34);
		types.add(Type.Insecte);
		types.add(Type.Vol);
		accessibleBackground.add(BackgroundType.Earth );
		accessibleBackground.add(BackgroundType.Water );
		updateAttacksAvailable();
	}	
	public Papilusion(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		cloneValuesFrom(poke);
	}

}