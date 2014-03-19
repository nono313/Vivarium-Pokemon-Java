package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public enum BackgroundType {
	Earth,
	Grass,
	Sand,
	Sea,
	Water;
	
	private static Map<BackgroundType, List<BackgroundType>> equivalent;
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
	
	public static void addEquivalence(BackgroundType a, BackgroundType b) {
		List<BackgroundType> l = equivalent.get(a); 
		
		/* Les équivalents de a sont ajouté à ceux de b */
		equivalent.get(b).addAll(l);
		equivalent.get(b).add(a);
		/* b est rajouté comme étant un équivalent de chaque équivalent de a */
		for(BackgroundType tmp : l) {
			equivalent.get(tmp).add(b);
		}
		/* b est équivalent de a */
		l.add(b);
		equivalent.put(a, l);
		
	}
	
	public List<BackgroundType> getEquivalent() {
		// TODO Auto-generated method stub
		List l = equivalent.get(this);
		if(l == null) {
			l =  new ArrayList<BackgroundType>();
		}
		return l;
	}
}