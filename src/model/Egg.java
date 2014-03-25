package model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import View.Window;

/**
 * An egg can be placed as an object on the map.<br>
 * It progressively cracks and after a certain time, the egg hatch into a pre-defined or a random pokemon.
 */
public class Egg extends Living{
	
	/**
	 * Pokemon that will be created when the egg hatches
	 */
	private Class<? extends Pokemon> pokemonInside = null;
	
	/**
	 * When cycle reaches the value of maxCycle, the state of the egg is incremented until it reaches 7 and hatches.
	 */
	private static final int maxCycle = 200;
	
	/**
	 * An egg has 6 states from just placed to almost hatched.<br>
	 * Each state corresponds to an image of an egg more and more cracked.
	 */
	private int state = 0;
	
	
	public Egg(Coord position, Class<? extends Pokemon> poke, Environment env) {
		super(position, env);
		this.pokemonInside=poke;
	}
	
	public Egg(Coord position, Environment env) {
		super(position, env);		
	}
	
	/**
	 * After the sixth state, the egg hatches into a pokemon either predefined or randomly selected within a list of original pokemon (not evolved).
	 */
	public void hatch() {
		/* If the pokemon has not been defined, a random selection is made */
		if (pokemonInside == null) {
			List<Class<? extends Pokemon>> possibilities = new ArrayList<>();
			possibilities.addAll(Window.getInstance().getMainPane().getOriginalFormList());
			Collections.shuffle(possibilities);
			pokemonInside = possibilities.get(0);
		}
		
		Window.getInstance().getNews().addInfo("L'oeuf éclot en " + pokemonInside.getSimpleName());
		
		Class<? extends Pokemon> cl = pokemonInside;
		Constructor<? extends Pokemon> con = null;
		try {
			con = cl.getConstructor(Environment.class, Coord.class, Movement.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		Pokemon pokemonHatched = null;
		try {
			pokemonHatched = con.newInstance(env, this.position, Movement.Down);
		} catch (InstantiationException
				| IllegalAccessException
				| IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		/* 
		 * The egg is removed from the list of elements that need to be updated
		 * It is also replaced on the map by the new pokemon.
		 * Therefore, the garbage collector should destroy the egg.
		 */
		env.getListLiving().remove(this);
	}

	public boolean update(int timeElapsed) {
		cycle += timeElapsed;
		if(cycle >= maxCycle) {
			cycle = 0;
			state++;
			if(state >= 6)
				return true;
			else
				return false;
		}
		else
			return false;
	}
	
	// Getter
	public int getState() {
		return state;
	}
}
