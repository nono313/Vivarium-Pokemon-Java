package model.pokemons;

import model.BackgroundType;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;
import model.attacks.*;

public class Florizarre extends Pokemon {
	public Florizarre(Environment env, Coord c, Movement m) {
		super(env, c, m, 
				(short)80, (short)7, (short)9,	// HP, hauteur, vision 
				(short)82, (short)82, (short)80, (short)100,		//stats 
				(short) 36, 	//level
				208, 0, CurveType.Parabolic,	//experience 
				(short)Short.MAX_VALUE, null);	//evolution
		attacks.put(new Charge(),(short) 1);
		attacks.put(new Rugissement(), (short) 1);
		attacks.put(new Vampigraine(), (short) 1);
		attacks.put(new Fouet_Lianes(), (short) 1);
		//attacks.put(new Poudre_Toxik(),(short) 1);
		attacks.put(new Tranch_Herbe(),(short) 1);
		attacks.put(new Croissance(),(short) 43);
		//attacks.put(new Poudre_Dodo(),(short) 55);
		attacks.put(new Lance_Soleil(),(short) 65);
		
		types.add(Type.Plante);
		types.add(Type.Poison);
		updateAttacksAvailable();
		accessibleBackground.add(BackgroundType.Earth);
	}
	public Florizarre(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		cloneValuesFrom(poke);

	}
}
