package model;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

import View.Window;

/**
 * Main class of the model, represents the map of the world and every elements of it.
 */
public class Environment {
	
	/**
	 * Map of key-value with coordinate as key and element as value.<br>
	 * Every element on the world are in this map.
	 */
	private final Map<Coord, Element> mapContains;
	/**
	 * Every elements that needs to be updated (living creatures) are added in this list.
	 */
	private List<Living> listLiving;
	
	/* Dimensions of the map (in number of squares) */
	private int width;
	private int height;
	
	/**
	 * Default background of the map, used on every square where no background is specified.
	 */
	private BackgroundType bgByDefault;
	/**
	 * Every square that has a different background from the default one are linked to that background in this map.
	 */
	private Map<Coord,BackgroundType> mapBackground;
	
	public Environment(Map<Coord,Element> mapContains, int width, int height, Map<Coord, BackgroundType> mapBackground) {
		super();
		this.mapContains = mapContains;
		this.width = width;
		this.height = height;
		this.mapBackground = mapBackground;
	}
	
	public Environment(int width, int height, BackgroundType backgroundByDefault) {
		super();
		this.mapContains = new TreeMap<Coord,Element>();
		this.width = width;
		this.height = height;
		this.mapBackground = new TreeMap<Coord, BackgroundType>();
		this.listLiving = new CopyOnWriteArrayList<Living>();
		this.bgByDefault = backgroundByDefault;
	}
	
	/**
	 * Place an element on the map and adds it on the listLiving if it is an egg or a pokemon
	 * @param e : Element
	 * @param c : Coord
	 */
	public void createAt(Element e, Coord c) {
		if(c.getX() >= 0 && c.getX() < width && c.getY() >= 0 && c.getY() < height ){
			mapContains.put(c, null);
			mapContains.put(c, e);
			if(e instanceof Living)
				listLiving.add((Living) e);
		}
	}

	/**
	 * Générateur de pokemons aléatoires sur la carte
	 */
	public void generatePokemons(int n) {
		Random rnd = new Random();
		Class<? extends Pokemon> p = null;
		Pokemon poke = null;
		Coord c = null;
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		List<Class<?extends Pokemon>> list = Window.getInstance().getMainPane().getPokemonList();
		
		for(int i = 0; i < n; i++) {
			int tryToGenerate = 0;
			do {
				if(tryToGenerate > 0)
					removeAt(c);
				p = list.get(rnd.nextInt(list.size()));
				do {
					c = new Coord(rnd.nextInt(width), rnd.nextInt(height));
				}while(getAt(c) != null || !nothingAround(c));
				poke = createPokemonFromClass(p, c);
				tryToGenerate++;
			}while(!poke.accessibleBackground.contains(mapBackground.get(c)));
			
		}
		String s = "Ajout de " + n + " pokemons sur la carte.";		
		Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
	}
	
	/**
	 * Checks if the close neighborhood is empty (squares on the 4 directions one square next to a specified coordinate).
	 * @param c : Coord
	 * @return boolean value
	 */
	private boolean nothingAround(Coord c) {
		boolean bool = true;
		for(Movement m : Movement.values()) {
			bool = bool && getAt(c.plus(m)) == null;
		}
		return bool;
	}
	
	/**
	 * Generate a pokemon from its class name and a coord on the map.
	 * @param cl : class derived from Pokemon
	 * @param position : Coord on the map where the pokemon will be
	 * @return the newly created Pokemon object
	 */
	private Pokemon createPokemonFromClass(Class<?extends Pokemon> cl, Coord position) {
		Constructor<? extends Pokemon> con = null;
		try {
			con = cl.getConstructor(Environment.class, Coord.class, Movement.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		Pokemon evolution = null;
		try {
			evolution = con.newInstance(this, position, Movement.Down);
		} catch (InstantiationException
				| IllegalAccessException
				| IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return evolution;
	}
	
	/* Getters and setters */
	public Map<Coord, Element> getContains() {
		return mapContains;
	}
	public BackgroundType getBgByDefault() {
		return bgByDefault;
	}
	public void putAt(Element e, Coord c) {
		mapContains.put(c, e);
	}
	public List<Living> getListLiving() {
		return listLiving;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public Map<Coord,BackgroundType> getBackground() {
		return mapBackground;
	}
	public void setBackground(HashMap<Coord,BackgroundType> mapBackground) {
		this.mapBackground = mapBackground ;
	}
	public Element getAt(Coord c){
		return mapContains.get(c);
	}
	
	/**
	 * Remove an element from the map
	 * @param e : an Element 
	 */
	public void remove(Element e) {
		listLiving.remove(e);
		mapContains.put(e.getPosition(), null);
	}
	
	/**
	 * Remove the element at a specific location on the map
	 * @param c : coord of the element
	 */
	private void removeAt(Coord c) {
		Element e = getAt(c);
		if(e != null) {
			listLiving.remove(e);
		}
		mapContains.put(c, null);
	}
	
}
