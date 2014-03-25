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


public class Salameche extends Pokemon{

	public Salameche(Environment env, Coord c, Movement m) {
		super(env, c, m, 
				(short)39, (short)2, (short)4,	// HP, hauteur, vision 
				(short)52, (short)43, (short)65, (short)55,		//stats 
				(short) 5, 	//level
				65, 0, CurveType.Parabolic,	//experience 
				(short)16, Reptincel.class);	//evolution
		
		attacks.put(new Griffe(),(short) 1);
		attacks.put(new Rugissement(), (short) 1);
		attacks.put(new Flammeche(),(short) 9);
		attacks.put(new Groz_Yeux(), (short) 15);
		attacks.put(new Frenesie(),(short) 22);
		attacks.put(new Tranche(),(short) 30);
		attacks.put(new LanceFlamme(),(short) 38);
		attacks.put(new Danseflamme(),(short) 46);
		types.add(Type.Feu);
		accessibleBackground.add(BackgroundType.Earth);
		updateAttacksAvailable();		

	}
	public static boolean isOriginalForm() {
		return true;
	}
	public Salameche(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		cloneValuesFrom(poke);

	}
}
