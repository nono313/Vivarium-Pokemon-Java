package model.pokemons;

import model.BackgroundType;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;
import model.attacks.*;

public class Bulbizarre extends Pokemon {
	public Bulbizarre(Environment env, Coord c, Movement m) {
		super(env, c, m, 
				(short)45, (short)2, (short)4,	// HP, hauteur, vision 
				(short)49, (short)49, (short)45, (short)65,		//stats 
				(short) 5, 	//level
				64, 0, CurveType.Parabolic,	//experience 
				(short)16, Herbizarre.class);	//evolution et form originale
		attacks.put(new Charge(),(short) 1);
		attacks.put(new Rugissement(), (short) 1);
		attacks.put(new Vampigraine(), (short)1);
		attacks.put(new Fouet_Lianes(), (short)13);
		attacks.put(new Tranch_Herbe(),(short) 27);
		attacks.put(new Croissance(),(short) 34);
		attacks.put(new Lance_Soleil(),(short) 48);
		
		types.add(Type.Plante);
		types.add(Type.Poison);
		updateAttacksAvailable();
		accessibleBackground.add(BackgroundType.Earth);
	}
	public static boolean isOriginalForm() {
		return true;
	}
	public Bulbizarre(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		cloneValuesFrom(poke);

	}
}
