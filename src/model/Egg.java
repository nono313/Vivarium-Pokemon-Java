package model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import View.Window;

public class Egg extends Living{
	private Class<? extends Pokemon> pokemonInside = null;
	private int state = 0;
	
	public Egg(Coord position, Class<? extends Pokemon> poke, Environment env) {
		super(position, env);
		this.pokemonInside=poke;
		maxCycle = 200;
	}
	public Egg(Coord position, Environment env) {
		super(position, env);
		maxCycle = 200;
		
	}
	
	public void hatch() {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pokemon evolution = null;
		try {
			evolution = con.newInstance(env, this.position, Movement.Down);
		} catch (InstantiationException
				| IllegalAccessException
				| IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		env.getListLiving().remove(this);
	}
	public int getState() {
		return state;
	}
	public boolean update() {
		cycle++;
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
}
