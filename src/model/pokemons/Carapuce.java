package model.pokemons;

import model.BackgroundType;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;
import model.attacks.*;


public class Carapuce extends Pokemon {
	public Carapuce(Environment env, Coord c, Movement m) {
		super(env, c, m, 
				(short)44, (short)2, (short)4,	// HP, hauteur, vision 
				(short)48, (short)65, (short)43, (short)50,		//stats 
				(short) 5, 	//level
				66, 0, CurveType.Parabolic,	//experience 
				(short)16, Carabaffe.class);	//evolution
		attacks.put(new Charge(),(short) 1);
		attacks.put(new Mimi_Queue(), (short) 4);
		attacks.put(new Ecume(), (short) 8);
		attacks.put(new Pistolet_a_O(), (short) 15);
		attacks.put(new Morsure(),(short) 22);
		attacks.put(new Repli(),(short) 28);
		attacks.put(new Coud_Krane(),(short) 35);
		attacks.put(new Hydrocanon(),(short) 35);
		
		types.add(Type.Eau);
		updateAttacksAvailable();
		accessibleBackground.add(BackgroundType.Earth);
		accessibleBackground.add(BackgroundType.Water);
	}
	public Carapuce(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		getValuesFrom(poke);

	}
	public static boolean isOriginalForm() {
		return true;
	}
}
