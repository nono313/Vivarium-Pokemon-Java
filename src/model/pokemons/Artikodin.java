package model.pokemons;

import model.BackgroundType;
import model.Coord;
import model.CurveType;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;
import model.attacks.Blizzard;
import model.attacks.Hate;
import model.attacks.Laser_Glace;
import model.attacks.Picpic;

public class Artikodin extends Pokemon {
	public Artikodin(Environment env, Coord c, Movement m) {
		super(env,c,m,
				(short)90, (short) 6,(short) 8,
				(short) 85,(short) 100,(short) 85, (short) 125,
				(short) 50,215, 0, CurveType.Slow , 
				(short) Short.MAX_VALUE, null);
		attacks.put(new Picpic(),(short) 1);
		attacks.put(new Laser_Glace(),(short) 1);
		attacks.put(new Blizzard(),(short) 51);
		attacks.put(new Hate(),(short) 55);
		types.add(Type.Glace);
		types.add(Type.Vol);
		accessibleBackground.add(BackgroundType.Earth );
		updateAttacksAvailable();
	}	
	public static boolean isOriginalForm(){
		return true;
	}
}