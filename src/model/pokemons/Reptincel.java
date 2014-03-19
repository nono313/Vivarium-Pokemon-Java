package model.pokemons;

import model.BackgroundType;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;
import model.attacks.Danseflamme;
import model.attacks.Flammeche;
import model.attacks.Frenesie;
import model.attacks.Griffe;
import model.attacks.Groz_Yeux;
import model.attacks.LanceFlamme;
import model.attacks.Rugissement;
import model.attacks.Tranche;

public class Reptincel extends Pokemon {
	public Reptincel(Environment env, Coord c, Movement m) {
		super(env, c, m, 
				(short)58, (short)4, (short)6,	// HP, hauteur, vision 
				(short)64, (short)58, (short)80, (short)65,		//stats 
				(short) 16, 	//level
				142, 0, CurveType.Parabolic,	//experience 
				(short)36, Dracaufeu.class);	//evolution

		attacks.put(new Griffe(),(short) 1);
		attacks.put(new Rugissement(), (short) 1);
		attacks.put(new Flammeche(),(short) 1);
		attacks.put(new Groz_Yeux(), (short) 1);
		attacks.put(new Frenesie(),(short) 22);
		attacks.put(new Tranche(),(short) 30);
		attacks.put(new LanceFlamme(),(short) 38);
		attacks.put(new Danseflamme(),(short) 46);
		types.add(Type.Feu);
		updateAttacksAvailable();
		accessibleBackground.add(BackgroundType.Earth);
	}
	public Reptincel(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		getValuesFrom(poke);

	}

}
