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

public class Dracaufeu extends Pokemon {
	public Dracaufeu(Environment env, Coord c, Movement m) {
		super(env, c, m, 
				(short)78, (short)6, (short)8,	// HP, hauteur, vision 
				(short)84, (short)78, (short)100, (short)85,		//stats 
				(short) 36, 	//level
				209, 0, CurveType.Parabolic,	//experience 
				(short)Short.MAX_VALUE, null);	//evolution

		attacks.put(new Griffe(),(short) 1);
		attacks.put(new Rugissement(), (short) 1);
		attacks.put(new Flammeche(),(short) 1);
		attacks.put(new Groz_Yeux(), (short) 1);
		attacks.put(new Frenesie(),(short) 1);
		attacks.put(new Tranche(),(short) 36);
		attacks.put(new LanceFlamme(),(short) 46);
		attacks.put(new Danseflamme(),(short) 55);
		types.add(Type.Feu);
		types.add(Type.Vol);
		updateAttacksAvailable();
		accessibleBackground.add(BackgroundType.Earth);
	}
	public Dracaufeu(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		cloneValuesFrom(poke);

	}
}
