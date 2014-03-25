package model.pokemons;

import model.BackgroundType;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;
import model.attacks.Acide;
import model.attacks.Croissance;
import model.attacks.Fouet_Lianes;
import model.attacks.Ligotage;
import model.attacks.Souplesse;
import model.attacks.Tranch_Herbe;

public class Boustiflor extends Pokemon {
	public Boustiflor(Environment env, Coord c, Movement m) {
		super(env,c,m,(short)65, (short) 3,(short) 5,
			(short) 90,(short) 50,(short) 55, (short) 85,
			(short) 30 ,151, 0, CurveType.Average ,
			(short) Short.MAX_VALUE, Empiflor.class);
		attacks.put(new Fouet_Lianes(),(short) 1);
		attacks.put(new Croissance(),(short) 1);
		attacks.put(new Ligotage(),(short) 1);
		attacks.put(new Acide(),(short) 29);
		attacks.put(new Tranch_Herbe(),(short) 38);
		attacks.put(new Souplesse(),(short) 49);
		types.add(Type.Plante);
		types.add(Type.Poison);
		accessibleBackground.add(BackgroundType.Earth );
		updateAttacksAvailable();
	}
	public Boustiflor(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		cloneValuesFrom(poke);
	}
}