package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Each square on the map has a background type corresponding to the type of soil.
 */
public enum BackgroundType {
	Earth,
	Grass,
	Sand,
	Sea,
	Water;
	
	/**
	 * Some BackgroundType are equivalent in term of pokemon's movement.<br>
	 * Sea and Water for example are equivalent in the model point of view, but will look different on the UI.
	 */
	private static Map<BackgroundType, List<BackgroundType>> equivalent;
	
	/**
	 * Initialization of the static map equivalent.
	 */
	static {
		equivalent = new TreeMap<BackgroundType, List<BackgroundType>>();
		for(BackgroundType t : BackgroundType.values()) {
			List<BackgroundType> l = new ArrayList<BackgroundType>();
			equivalent.put(t, l);
		}
		addEquivalence(Earth, Sand);
		addEquivalence(Earth, Grass);
		addEquivalence(Sea, Water);
	}
	
	/**
	 * Add an entry in the equivalent map.<br>
	 * When A is said to be an equivalent of B, it is also added to the list of equivalents of B's equivalents and vice versa.
	 *   
	 * @param a : a BackgroundType
	 * @param b : a BackgroundType
	 */
	private static void addEquivalence(BackgroundType a, BackgroundType b) {
		List<BackgroundType> l = equivalent.get(a); 
		
		/* Equivalents of A are added to the equivalent list of B */
		equivalent.get(b).addAll(l);
		equivalent.get(b).add(a);
		/* B is considered as an equivalent of every equivalents of A */
		for(BackgroundType tmp : l) {
			equivalent.get(tmp).add(b);
		}
		/* Finally, B is an equivalent of A */
		l.add(b);
		equivalent.put(a, l);
		
	}
	
	/**
	 * Get the list of equivalents of a BackgroundType
	 * @return a list of BackgroundType equivalent to this.
	 */
	public List<BackgroundType> getEquivalent() {
		List l = equivalent.get(this);
		if(l == null) {
			l =  new ArrayList<BackgroundType>();
		}
		return l;
	}
}