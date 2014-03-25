package model.pokemons;

import model.BackgroundType;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;
import model.attacks.Boul_Armure;
import model.attacks.Ecras_Face;
import model.attacks.Lilliput;
import model.attacks.Metronome;
import model.attacks.Rugissement;
import model.attacks.Torgnoles;

public class Melofee extends Pokemon {
	public Melofee(Environment env, Coord c, Movement m) {
		super(env,c,m,(short)70, (short) 2,(short) 4,
			(short) 45,(short) 48,(short) 35, (short) 60, 
			(short) 1, 68, 0, CurveType.Average,
			(short) Short.MAX_VALUE, Melodelfe.class);
		attacks.put(new Ecras_Face(),(short) 1);
		attacks.put(new Rugissement(),(short) 1);
		attacks.put(new Torgnoles(),(short) 18);
		attacks.put(new Lilliput(),(short) 24);
		attacks.put(new Metronome(),(short) 31);
		attacks.put(new Boul_Armure(),(short) 39);
		types.add(Type.Normal);
		accessibleBackground.add(BackgroundType.Earth );
		updateAttacksAvailable();
	}	
	public static boolean isOriginalForm(){
		return true;
	}
	public Melofee(Pokemon poke) {
		this(poke.getEnv(), poke.getPosition(), poke.getLookingAt());
		cloneValuesFrom(poke);
	}
}