package model.pokemons;

import model.BackgroundType;
import model.attacks.*;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;

public class Herbizarre extends Pokemon {
	public Herbizarre(Environment env, Coord c, Movement m) {
		super(env, c, m, 
				(short)60, (short)3, (short)5,	// HP, hauteur, vision 
				(short)62, (short)63, (short)65, (short)80,		//stats 
				(short) 16, 	//level
				141, 0, CurveType.Parabolic,	//experience 
				(short)32, Florizarre.class);	//evolution
		attacks.put(new Charge(),(short) 1);
		attacks.put(new Rugissement(), (short) 1);
		attacks.put(new Vampigraine(), (short) 1);
		attacks.put(new Fouet_Lianes(), (short) 13);
		//attacks.put(new Poudre_Toxik(),(short) 22);
		attacks.put(new Tranch_Herbe(),(short) 30);
		attacks.put(new Croissance(),(short) 38);
		//attacks.put(new Poudre_Dodo(),(short) 46);
		attacks.put(new Lance_Soleil(),(short) 54);
		
		types.add(Type.Plante);
		types.add(Type.Poison);
		updateAttacksAvailable();
		accessibleBackground.add(BackgroundType.Earth);
	}
	public Herbizarre(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		cloneValuesFrom(poke);

	}
}
